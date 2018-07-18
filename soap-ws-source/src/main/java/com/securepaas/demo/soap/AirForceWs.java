package com.securepaas.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import com.securepaas.demo.soap.KmlWsInterface;

@WebService(targetNamespace = KmlWsInterface.TARGET_NAMESPACE, serviceName="KmlAirForceWs")
public class AirForceWs extends HttpServlet implements KmlWsInterface {
	private static final long serialVersionUID = 6649002988235881518L;

	@WebMethod
     public String operationOne()
     {
    	 String style="<styleUrl>#airforceStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>McGuire Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-74.59051801658491,40.02738027550376,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Wright-Patterson Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-84.0487207566671,39.82985448133285,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 kml.append("<Placemark>");
    	 kml.append("<name>Scott Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-89.84399000667456,38.54564904798151,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Bolling Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-77.02230494989914,38.82287499538168,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 kml.append("<Placemark>");
    	 kml.append("<name>Andrews Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-76.86854119581319,38.81063820756057,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }
     
     @WebMethod
     public String operationTwo()
     {
    	 String style="<styleUrl>#airforceStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Langley Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-76.35556232507273,37.08238901992618,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Dover Air Force Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-75.47575489275636,39.12848864810985,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }    
}