package com.securepaas.demo.parking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.securepaas.demo.parking.object.Location;
import com.securepaas.demo.parking.object.Ticket;
import com.securepaas.demo.parking.object.Violation;

@Path("/atickets")
@Produces(MediaType.APPLICATION_JSON)
public class ATicketWs{
    private static final int INIT_TICKETS_COUNT = 17;
	private static final List<Ticket> result=generateTickets('A');
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets() {
    	return result;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response commitTickets(Ticket ticket){
    	
    	result.add(ticket);
    	return Response.status(Status.ACCEPTED).build(); 
    }
    
    public static List<Ticket> generateTickets(char zone) {

        final List<Location> locations = new ArrayList<Location>();

        if(zone=='A')
        {
	        //NY
	        locations.add(createLocation("Avalon Plaza", 40.070790, -74.277157));
	        locations.add(createLocation("North Point Mall", 40.047144,-74.295127));
	        locations.add(createLocation("Shadow-Soft Headquarters", 39.986658,-74.342613));
	        locations.add(createLocation("Atlantic Station", 39.792719, -74.397864));
	        locations.add(createLocation("Cumberland Mall", 39.880233, -74.468629));
	        locations.add(createLocation("Northside Hospital-Atlanta", 39.909188, -74.354051));
	        locations.add(createLocation("Piedmont Park", 39.785475, -74.371933));
	        locations.add(createLocation("Georgia Aquarium", 39.748995, -74.387982));
	        locations.add(createLocation("Lenox Square", 39.827435, -74.354680));
	        locations.add(createLocation("Atlanta Motor Speedway", 39.748995, -74.387982));
	        locations.add(createLocation("Stone Mountain", 39.806020, -74.146014));
	        locations.add(createLocation("Six Flags White Water", 39.806020, -74.146014));
        }
        else if(zone=='B')
        {
	        //CA
	        locations.add(createLocation("Avalon Plaza", 39.070790, -121.277157));
	        locations.add(createLocation("North Point Mall", 39.047144,-121.295127));
	        locations.add(createLocation("Shadow-Soft Headquarters", 38.986658,-121.342613));
	        locations.add(createLocation("Atlantic Station", 38.792719, -121.397864));
	        locations.add(createLocation("Cumberland Mall", 38.880233, -121.468629));
	        locations.add(createLocation("Northside Hospital-Atlanta", 38.909188, -121.354051));
	        locations.add(createLocation("Piedmont Park", 38.785475, -121.371933));
	        locations.add(createLocation("Georgia Aquarium", 38.748995, -121.387982));
	        locations.add(createLocation("Lenox Square", 38.827435, -121.354680));
	        locations.add(createLocation("Atlanta Motor Speedway", 38.748995, -121.387982));
	        locations.add(createLocation("Stone Mountain", 38.806020, -121.146014));
	        locations.add(createLocation("Six Flags White Water", 38.806020, -121.146014));
        }
        else
        {
	        //GA
	        locations.add(createLocation("Avalon Plaza", 34.070790, -84.277157));
	        locations.add(createLocation("North Point Mall", 34.047144,-84.295127));
	        locations.add(createLocation("Shadow-Soft Headquarters", 33.986658,-84.342613));
	        locations.add(createLocation("Atlantic Station", 33.792719, -84.397864));
	        locations.add(createLocation("Cumberland Mall", 33.880233, -84.468629));
	        locations.add(createLocation("Northside Hospital-Atlanta", 33.909188, -84.354051));
	        locations.add(createLocation("Piedmont Park", 33.785475, -84.371933));
	        locations.add(createLocation("Georgia Aquarium", 33.748995, -84.387982));
	        locations.add(createLocation("Lenox Square", 33.827435, -84.354680));
	        locations.add(createLocation("Atlanta Motor Speedway", 33.748995, -84.387982));
	        locations.add(createLocation("Stone Mountain", 33.806020, -84.146014));
	        locations.add(createLocation("Six Flags White Water", 33.806020, -84.146014));
        }
        
        List<Ticket> result = new ArrayList<Ticket>();
        for (Location location : locations) {
            result.add(createRandomTicket(location,zone));
        }

        for (int i = 0; i < INIT_TICKETS_COUNT; i++) {
            result.add(createRandomTicket(null,zone));
        }

        return result;
    }
    
    private static Ticket createRandomTicket(final Location location, char zone) {
        final Random random = new Random();
        final Ticket ticket = new Ticket();

        Calendar cal = Calendar.getInstance();

        if (location == null) {
            cal.add(Calendar.HOUR, -(30 + random.nextInt(70)));
            ticket.setNotes("Dummy notes");
            ticket.setLocation(createDummyLocation());
        } else {
            cal.add(Calendar.HOUR, -random.nextInt(24));
            ticket.setLocation(location);
            ticket.setNotes("Notes for " + location.getAddress());
        }
        cal.set(Calendar.MINUTE, 0);
        ticket.setTimeStamp(cal.getTime());

        ticket.setImageUrl("VAADIN/themes/parking/tickets/" + 1 + ".jpg");
        ticket.setThumbnailUrl("VAADIN/themes/parking/tickets/" + 1 + "thumbnail.jpg");
        ticket.setImageIncluded(true);
        ticket.setRegisterPlateNumber("ABC-" + (random.nextInt(800) + 100));

        ticket.setViolation(Violation.values()[random.nextInt(Violation.values().length)]);

        ticket.setMyTicket(random.nextDouble() < 0.1);
        ticket.setArea(zone + String.valueOf(random.nextInt(4) + 1));
        return ticket;
    }
    
    private static Location createDummyLocation() {
        final Random random = new Random();

        double lat = Location.getDefaultLatitude();
        double lon = Location.getDefaultLongitude();

        Location location = new Location();
        location.setAddress("Test");

        double latitude = lat + (random.nextDouble() - 0.5) * 0.1;
        double longitude = lon + (random.nextDouble() - 0.5) * 0.1;
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    private static Location createLocation(final String address,final double latitude, final double longitude) {
        Location location = new Location();
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}