package com.securepaas.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import com.securepaas.demo.soap.KmlWsInterface;

@WebService(targetNamespace = KmlWsInterface.TARGET_NAMESPACE, serviceName="KmlMarineCorpsWs")
public class MarineCorpsWs extends HttpServlet implements KmlWsInterface {
	private static final long serialVersionUID = -501646124183963009L;

	@WebMethod
     public String operationOne()
     {
    	 String style="<styleUrl>#marineStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();
    	 kml.append("<Placemark>");
    	 kml.append("<name>Marine Corps Air Station Iwakuni</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>132.2342016581189,34.14480222651773,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Royal Marines Base Chivenor</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-4.147226877372887,51.09195472847591,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 kml.append("<Placemark>");
    	 kml.append("<name>Hawaii Marine Corps Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-157.7649127409037,21.44616505490176,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Marine Corps Air Base Miramar</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-117.1401027930728,32.87289089616353,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 kml.append("<Placemark>");
    	 kml.append("<name>ElToroMarineAirStation(Closed)</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-117.7285866459733,33.67118033622596,246.6200632690128</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");    	 
    	 return kml.toString();
     }
     
     @WebMethod
     public String operationTwo()
     {
    	 String style="<styleUrl>#marineStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();

    	 kml.append("<Placemark>");
    	 kml.append("<name>Marine Barracks</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-76.99418920031241,38.87913475449083,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Quantico Marine Corps Base</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-77.30046369028032,38.51598884959407,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 return kml.toString();
     }
    
}