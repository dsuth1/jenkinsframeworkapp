package com.securepaas.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import com.securepaas.demo.soap.KmlWsInterface;

@WebService(targetNamespace = KmlWsInterface.TARGET_NAMESPACE, serviceName="KmlArmyWs")
public class ArmyWs extends HttpServlet  implements KmlWsInterface {
	private static final long serialVersionUID = 3201408665043201506L;

	@WebMethod
     public String operationOne()
     {
    	 String style="<styleUrl>#armyStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();

    	 kml.append("<Placemark>");
    	 kml.append("<name>Red River Army Depot</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-94.30126075393129,33.43495805498533,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Tobyhanna Army Depot</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-75.43232595029818,41.19393517812243,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Blue Grass Army Depot</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-84.24822889954427,37.67999337800261,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }
     
     @WebMethod
     public String operationTwo()
     {
    	 String style="<styleUrl>#armyStyle</styleUrl>";
    	 StringBuilder kml=new StringBuilder();
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Letterkenny Army Depot</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-77.63493027754581,39.99474300746362,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 kml.append("<Placemark>");
    	 kml.append("<name>Biggs Army Air Field</name>");
    	 kml.append(style);
    	 kml.append("<Point>");
    	 kml.append("<coordinates>-106.3839383501602,31.83897572362344,0</coordinates>");
    	 kml.append("</Point>");
    	 kml.append("</Placemark>");
    	 
    	 return kml.toString();
     }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}  
}