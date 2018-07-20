package com.securepaas.demo.parking.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.securepaas.demo.parking.ParkingUI;
import com.securepaas.demo.parking.Shift;
import com.securepaas.demo.parking.SoapServiceInterface;
import com.securepaas.demo.parking.object.Location;
import com.securepaas.demo.parking.object.Ticket;
import com.vaadin.data.util.BeanItemContainer;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

public class DataUtil {
	private static Logger logger=Logger.getLogger(DataUtil.class.getCanonicalName());
	private static SoapServiceInterface shiftWs;
	private static String baseURL;
	
	public static BeanItemContainer<Ticket> initData()
	{
		return new BeanItemContainer<Ticket>(Ticket.class,getTickets());
	}
	
	public static void refreshTickets()
	{
		ParkingUI.getTicketContainer().removeAllItems();
		ParkingUI.getTicketContainer().addAll(getTickets());
	}
	
	private synchronized static String getBaseURL()
	{
		if(baseURL==null)
		{
			String host=System.getenv("WEB_SERVICE_HOST");
			if(host==null || host.isEmpty())
				host="localhost";
			String port=System.getenv("WEB_SERVICE_PORT");
			if(port==null || port.isEmpty())
				port="8080";
			
			baseURL="http://"+host;
			
			if(!port.equals("80"))
				baseURL=baseURL+":"+port;
		}
		return baseURL;
	}
	
	private synchronized static SoapServiceInterface getShiftWs()
	{
		if(shiftWs==null)
		{
	    	try {
				String wsBaseURL=getBaseURL()+"/SoapWebServices/";
				
				Service service = Service.create(new URL(wsBaseURL+"ShiftWs?wsdl"), new QName(SoapServiceInterface.TARGET_NAMESPACE, "ShiftWs"));
				shiftWs=service.getPort(SoapServiceInterface.class);
			} catch (Exception e) {
				logger.log(Level.SEVERE,"Failed to get ShiftWs Connection - "+e.getMessage(),e);
				throw new RuntimeException("Failed to get ShiftWs Connection - "+e.getMessage(),e);
			}
		}
		return shiftWs;
	}
	
	private static Builder getTicketClient(char type)
	{
		Client client = ClientBuilder.newClient();
		Builder builder=client.target(getBaseURL()+"/RestWebServices/"+type+"tickets").request().accept(MediaType.APPLICATION_JSON);
		return builder;
	}	
	
    /**
     * Generate a collection of random shifts.
     * 
     * @return
     */
    private static Collection<Ticket> getTickets() {   
    	List<Ticket> result=new ArrayList<Ticket>();
        try {
        	Response res = getTicketClient('a')
        			.get();
        	List<Ticket> shifts=res.readEntity(new GenericType<List<Ticket>>() {});
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of A shift objects:\n "+e.getMessage(),e);
		}
        
        try {
        	Response res = getTicketClient('b')
        			.get();
        	List<Ticket> shifts=res.readEntity(new GenericType<List<Ticket>>() {});
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of B shift objects:\n "+e.getMessage(),e);
		}
        
        try {
        	Response res = getTicketClient('c')
        			.get();
        	List<Ticket> shifts=res.readEntity(new GenericType<List<Ticket>>() {});
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of C shift objects:\n "+e.getMessage(),e);
		}
        
        return result;
    }
    
    /**
     * Generate a collection of random shifts.
     * 
     * @return
     */
    public static Collection<Shift> getShifts() {
        Collection<Shift> result = Lists.newArrayList();
        
        try {
        	List<Shift> shifts=getShiftWs().getAShifts();
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of A shift objects:\n "+e.getMessage(),e);
		}
        
        try {
        	List<Shift> shifts=getShiftWs().getBShifts();
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of B shift objects:\n "+e.getMessage(),e);
		}
        
        try {
        	List<Shift> shifts=getShiftWs().getCShifts();
			result.addAll(shifts);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to get list of C shift objects:\n "+e.getMessage(),e);
		}
        
        return result;
    }

    public static boolean persistTicket(final Ticket ticket) {
        ticket.setMyTicket(true);
        Location location = ticket.getLocation();
        if (location.getLatitude() == 0.0 || location.getLongitude() == 0.0) {
            determineTicketLocation(ticket);
        }
        Builder builder;
        if(ticket.getArea().startsWith("A"))
        	 builder = getTicketClient('a');
        else if(ticket.getArea().startsWith("B"))
        	builder = getTicketClient('b');  	
        else
        	builder = getTicketClient('c');
        
        try {
         	Response res = builder
         			.post(Entity.entity(ticket, MediaType.APPLICATION_JSON));
         	if(res.getStatus()==Status.ACCEPTED.getStatusCode()) 
         		return true;
         	else
         	{
         		logger.log(Level.SEVERE, "Failed to commit ticket, server returned with status code "+res.getStatus());
         		return false;
         	}
 		} catch (Exception e) {
 			logger.log(Level.SEVERE, "Failed to commit ticket:\n "+e.getMessage(),e);
 			return false;
 		} finally {
 			refreshTickets();
 		}
    }

    private static void determineTicketLocation(final Ticket ticket) {
        double latitude = ParkingUI.getApp().getCurrentLatitude();
        double longitude = ParkingUI.getApp().getCurrentLongitude();

        try {
            // Try to determine the coordinates using google maps api
            String address = ticket.getLocation().getAddress();
            if (address != null) {
                StringBuilder str = new StringBuilder(
                        "http://maps.google.com/maps/api/geocode/json?address=");
                str.append(address.replaceAll(" ", "+"));
                str.append("&sensor=false");

                URL url = new URL(str.toString());
                URLConnection urlc = url.openConnection();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(
                        urlc.getInputStream()));

                String line;
                final StringBuilder builder = new StringBuilder(2048);
                builder.append("[");
                while ((line = bfr.readLine()) != null) {
                    builder.append(line);
                }
                builder.append("]");
                final JsonArray jsa = (JsonArray) Json
                        .parse(builder.toString());
                final JsonObject jo = (JsonObject) jsa.get(0);
                JsonArray results = jo.getArray("results");
                JsonObject geometry = results.getObject(0)
                        .getObject("geometry");
                JsonObject loc = geometry.getObject("location");
                latitude = loc.getNumber("lat");
                longitude = loc.getNumber("lng");
            }
        } catch (Exception e) {
            // Ignore
        }

        ticket.getLocation().setLatitude(latitude);
        ticket.getLocation().setLongitude(longitude);

    }

}
