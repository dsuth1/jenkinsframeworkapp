package com.securepaas.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.securepaas.demo.soap.KmlWsInterface;

public class ServiceProvider extends HttpServlet {
	private static final long serialVersionUID = -3338842517495164448L;
	private final Logger systemLogger=Logger.getLogger(ServiceProvider.class.getCanonicalName());
	private final String navyIcon="navy.png";
	private final String armyIcon="army.png";
	private final String airForceIcon="airforce.png";
	private final String marineIcon="marine.png";
	private PrintWriter responseWriter;
	private final KmlWsInterface navyWs;
	private final KmlWsInterface airForceWs;
	private final KmlWsInterface armyWs;
	private final KmlWsInterface marineWs;
	
	public ServiceProvider()
	{		
    	String host="localhost";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
    	String wsBaseURL="http://"+host+":8080/SoapWebServices/";
		
		try {				
			Service service = Service.create(new URL(wsBaseURL+"Navy?wsdl"), new QName(KmlWsInterface.TARGET_NAMESPACE, "KmlNavyWs"));
			navyWs=service.getPort(KmlWsInterface.class);
					
			service = Service.create(new URL(wsBaseURL+"AirForce?wsdl"), new QName(KmlWsInterface.TARGET_NAMESPACE, "KmlAirForceWs"));
			airForceWs=service.getPort(KmlWsInterface.class);
							
			service = Service.create(new URL(wsBaseURL+"Army?wsdl"), new QName(KmlWsInterface.TARGET_NAMESPACE, "KmlArmyWs"));
			armyWs=service.getPort(KmlWsInterface.class);
			
			service = Service.create(new URL(wsBaseURL+"MarineCorps?wsdl"), new QName(KmlWsInterface.TARGET_NAMESPACE, "KmlMarineCorpsWs"));
			marineWs=service.getPort(KmlWsInterface.class);
		} catch (Exception e) {
			systemLogger.log(Level.SEVERE,e.getMessage(),e);
			throw new IllegalArgumentException("Failed to create servlet: "+ e.getMessage(),e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = "Anonymous User";
		if(request.getUserPrincipal() != null)
		   username = request.getUserPrincipal().getName();
		
		responseWriter=response.getWriter();		
		responseWriter.write(generateBeginningSection(username));
		responseWriter.write(generateJScriptSection(request));
		responseWriter.write("</script></body></html>");
	}

	private String getKmlData(HttpServletRequest request)
	{	
		StringBuilder kml=new StringBuilder();				
		String baseUrl=getServletBaseURL(request);
		if(!baseUrl.endsWith("/"))
			baseUrl=baseUrl+"/";
		baseUrl=baseUrl+"images/";
		
		kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		kml.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		kml.append("<Document>");
		kml.append("<Style id=\"armyStyle\">");
		kml.append("<IconStyle id=\"iconStyle1\">");
		kml.append("<Icon>");
		kml.append("<href>"+baseUrl+armyIcon+"</href>");
		kml.append("<w>32</w>");
		kml.append("<h>32</h>");
		kml.append("</Icon>");
		kml.append("</IconStyle>");
		kml.append("</Style>");
		kml.append("<Style id=\"airforceStyle\">");
		kml.append("<IconStyle id=\"iconStyle1\">");
		kml.append("<Icon>");
		kml.append("<href>"+baseUrl+airForceIcon+"</href>");
		kml.append("<w>32</w>");
		kml.append("<h>32</h>");
		kml.append("</Icon>");
		kml.append("</IconStyle>");
		kml.append("</Style>");
		kml.append("<Style id=\"marineStyle\">");
		kml.append("<IconStyle id=\"iconStyle1\">");
		kml.append("<Icon>");
		kml.append("<href>"+baseUrl+marineIcon+"</href>");
		kml.append("<w>32</w>");
		kml.append("<h>32</h>");
		kml.append("</Icon>");
		kml.append("</IconStyle>");
		kml.append("</Style>");
		kml.append("<Style id=\"navyStyle\">");
		kml.append("<IconStyle id=\"iconStyle1\">");
		kml.append("<Icon>");
		kml.append("<href>"+baseUrl+navyIcon+"</href>");
		kml.append("<w>32</w>");
		kml.append("<h>32</h>");
		kml.append("</Icon>");
		kml.append("</IconStyle>");
		kml.append("</Style>");
		
		try { kml.append(airForceWs.operationOne());} catch(Exception e){ /*ignore*/}
		try { kml.append(airForceWs.operationTwo());} catch(Exception e){ /*ignore*/}
				
		try { kml.append(armyWs.operationOne());} catch(Exception e){ /*ignore*/}
		try { kml.append(armyWs.operationTwo()); } catch(Exception e) { /*ignore*/}	

		try { kml.append(marineWs.operationOne());} catch(Exception e){ /*ignore*/ }
		try { kml.append(marineWs.operationTwo()); } catch(Exception e) { /*ignore*/ }
				
		try { kml.append(navyWs.operationOne());} catch(Exception e){ /*ignore*/ }
		try { kml.append(navyWs.operationTwo()); } catch(Exception e) { /*ignore*/ }
		
		kml.append("</Document>");
		kml.append("</kml>");
		
		return kml.toString();
	}
	
	private String generateJScriptSection(HttpServletRequest request)
	{
		StringBuilder builder= new StringBuilder();
		builder.append("var map = new GMap2(document.getElementById(\"map\"));map.setCenter(new GLatLng(49.496675,-102.65625), 5);");
		builder.append("map.addControl(new GLargeMapControl());map.addControl(new GMapTypeControl());map.addControl(new GScaleControl");
		builder.append("());var exml =new EGeoXml(\"exml\", map, \"\",{sidebarid:\"sideBar\",iwwidth:300,sortbyname:true});exml.parse();");

		builder.append("exml.parseString('");
		builder.append(getKmlData(request));

		builder.append("');StringGEvent.addListener(map, \"click\", function(overlay,latlng) {var polyPoints = Array();  var polyNumSides");
		builder.append("= 20;var polySideLength = 18;  var mapZoom=8;  var mapNormalProj = G_NORMAL_MAP.getProjection();  var clickedPixel");
		builder.append("= mapNormalProj.fromLatLngToPixel(latlng, mapZoom);  var rad=prompt(\"Enter the megaton value:\");  rad1=Math.sqrt");
		builder.append("(rad)*(26);  for (var a = 0; a<(polyNumSides+1); a++) {var aRad = polySideLength*a*(Math.PI/180); var pixelX = ");
		builder.append("clickedPixel.x + rad1 * Math.cos(aRad); var pixelY = clickedPixel.y + rad1 * Math.sin(aRad);var polyPixel = new ");
		builder.append("GPoint(pixelX,pixelY); var polyPoint = mapNormalProj.fromPixelToLatLng(polyPixel,mapZoom);polyPoints.push(polyPoint");
		builder.append(");} var polygon = new GPolygon(polyPoints,\"#000000\",2,.5,\"#F50525\",.5);map.addOverlay(polygon);polyPoints = Array");
		builder.append("();  rad2=Math.sqrt(rad)*(6);  for (var a = 0; a<(polyNumSides+1); a++) {var aRad = polySideLength*a*(Math.PI/180);var");
		builder.append("pixelX = clickedPixel.x + rad2 * Math.cos(aRad);var pixelY = clickedPixel.y + rad2 * Math.sin(aRad);var polyPixel = new");
		builder.append("GPoint(pixelX,pixelY);var polyPoint = mapNormalProj.fromPixelToLatLng(polyPixel,mapZoom);polyPoints.push(polyPoint);  ");
		builder.append("}polygon = new GPolygon(polyPoints,\"#000000\",2,.5,\"#F50525\",.5);map.addOverlay(polygon);});");
		
		return builder.toString();
	}

	private String generateBeginningSection(String username)
	{
		StringBuilder builder= new StringBuilder();
		builder.append("<html><head><title>Service Provider</title><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"");
		builder.append("/><script type=\"text/javascript\" src=\"http://maps.google.com/maps?file=api&v=2&sensor=false&key=ABQIAAAAz3VdOSm7RYmw");
		builder.append("NEXpijAMXxR_tDoVW5HyCt4mym8KqFxN71svthRAlUOIhSV9C_mZ0hzQcE9za3nk3A\"></script><script type=\"text/javascript\" ");
		builder.append("src=\"egeoxml.js\"></script><style type=\"text/css\">body{background: url(images/bg.jpg);color:#3D3D3D;}a{text-decoration:");
		builder.append("none;color:#3D3D3D;}a:visited{}a:hover{text-decoration:underline;color:#787878;}#header, #main, #footer{background: url(");
		builder.append("images/textBack.png);}#main{margin-top:20px;padding-top:20px;padding-bottom:20px;}#footer{text-align:center;margin-top:20px;");
		builder.append("padding-top:40px;padding-bottom:40px;}#everything{width: 1175px; margin-left: auto; margin-right: auto;}#sideBar{width:300px;");
		builder.append("padding:10px;height: 400px; border-left:solid 1px; border-top:solid 1px;overflow: scroll; line-height:1.8}</style></head><body>");
		builder.append("<div id=\"everything\"><div id=\"title\"><img style=\"width: 500px; margin-top: 10px;margin-bottom:5px;\" src=\"images/Title1.");
		builder.append("png\"/><img src=\"images/logo.png\"/ style=\"width:225px;margin:10px 0px -10px 450px;\"></div><div id=\"header\"><table ");
		builder.append("style=\"width: 1135px;margin:15px;\"><tr><td>Welcome "+username+"</td><td style=\"text-align:right\"><a href=\"?GLO=true\">");
		builder.append("LogOut</a></td></tr></table></div><div id=\"main\"> <table align=center style=\"width: 1135px;\"> <tr> <td valign=\"top\">");
		builder.append("<div id=\"sideBar\"></div></td> <td style=\"width: 750px; height: 420px;border:solid 1px;\"><div id=\"map\" style=\"width: ");
		builder.append("750px; height: 420px;\"></div> </td> </tr> </table></div><div id=\"footer\">SecurePaaS</div></div><script type=\"text/javascript\">");
	
		return builder.toString();
	}
	
	/**
	 * Method determines the BaseURL requested from a given HttpServletRequest.
	 * 
	 * @param request The HttpServletRequest to parse.
	 * @return        The baseURL requested from a given HttpServletRequest
	 */
	public final static String getServletBaseURL(HttpServletRequest request){
		return getServletBaseURL(request.getRequestURL().toString());
	}
	
	/**
	 * Method determines the BaseURL requested from a given string representing the requested URL.
	 * 
	 * @param requestURL String representing the requested URL
	 * @return           The baseURL requested from a given HttpServletRequest
	 */
	public final static String getServletBaseURL(String requestURL){
		int count = countMatches(requestURL,"/");
		if(count<=3)
			return requestURL;
		else
		{
			int i=requestURL.indexOf("/");
			i=requestURL.indexOf("/",i+2);

			if((i+1)!=requestURL.length())
			{
				//Gonna check for the random situation that a user typed multiple slashes here
				int unnecessarySlashes=i;
				while(requestURL.charAt(unnecessarySlashes+1)=='/')
					unnecessarySlashes++;
				
				String baseUrl=requestURL.substring(0,i+1);
				i=requestURL.indexOf("/",unnecessarySlashes+1);	
				baseUrl=baseUrl+requestURL.substring(unnecessarySlashes+1,i+1);
									
				return baseUrl;
			} else
				return requestURL.substring(0,i);
		}
	}
	
	/**
	 * Determines the number of occurrences of a given substring in a given 
	 * string.
	 * 
	 * @param str
	 * @param sub
	 * @return
	 */
	public static int countMatches(String str, String sub) {
		if ((str==null || str.length()==0 ) || (sub==null || sub.length()==0 ))
			return 0;
		
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}
}
