package com.securepaas.demo.soap;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import com.securepaas.demo.soap.KmlWsInterface;

@WebService(targetNamespace = KmlWsInterface.TARGET_NAMESPACE, serviceName="KmlNavyWs")
public class NavyWs extends HttpServlet implements KmlWsInterface  {
	private static final long serialVersionUID = 3431376070149778174L;

	@WebMethod
     public String operationOne()
     {
    	 String style="<styleUrl>#navyStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();

    	 kml.append("<Placemark>");
    	 kml.append("<name>United States Navy Support Facility Dieg Garcia</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>72.44026663211334,-7.337244838000708,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Whidbey Island Navy Air Station</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-122.6614848892228,48.34610719267651,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Moffett Navy Air Station(Closed)</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-122.0491458652284,37.4162127859741,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>US Navy Haines Fuel Terminal</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-135.4444811715057,59.27324992715371,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }
     
     @WebMethod
     public String operationTwo()
     {
    	 String style="<styleUrl>#navyStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();

    	 kml.append("<Placemark>");
    	 kml.append("<name>Navy Ships Parts Control Center</name>");
    	 kml.append("<styleUrl>#navyStyle</styleUrl>");
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-76.98261303420095,40.22853566593889,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Great Lakes Naval Station</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-87.83630061392726,42.31080452828196,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}    
}