package com.securepaas.demo.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class SimpleServiceProvider extends HttpServlet {
	private static final long serialVersionUID = -5529548702189522641L;
	private static final Logger log=Logger.getLogger(SimpleServiceProvider.class.getCanonicalName());
	private PrintWriter responseWriter;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String host="localhost";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}		
		String username = "Anonymous User";
		if(request.getUserPrincipal() != null)
		   username = request.getUserPrincipal().getName();
		
		//Client client = ClientBuilder.newClient().register(arg0);
		Client client = ClientBuilder
				.newClient();
		Response res = client.target("http://"+host+":8080/RestWebServices/helloworld").request("text/plain").get();
		InputStream is=res.readEntity(InputStream.class);
		String output=null;
		if(is!=null && res.getStatus()!=403)
			output=convertStreamToString(is);

		responseWriter=response.getWriter();		
		responseWriter.write(generateHTMLSection(username,output));
		//responseWriter.write("<br>");
	//	responseWriter.write(generatePostForm());
		

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String user = request.getParameter("username");
		String password = request.getParameter("password");
		
		responseWriter = response.getWriter();
		responseWriter.println("<h1> Your username is: " + user + "</h1> <br>");
		responseWriter.println("<h2> Your password is: " + password + "</h2>");
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s=null;
	    try {
			s = new java.util.Scanner(is).useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} catch (Exception e) {
			log.log(Level.SEVERE,e.getMessage(),e);
			return "";
		} finally {
			if(s!=null)
				s.close();
			try {is.close();} catch (IOException e) {/*DO NOTHING*/}
		}
	}

	private String generateHTMLSection(String username,String output)
	{
		if(output==null || output.isEmpty())
			output="...unable to get response";
		StringBuilder builder= new StringBuilder();
		builder.append("<html><head><title>Dream Shiny "+username+"</title></head><body>Dream Shiny "+username+"<br/> Also a back end rest service says "+output);
		builder.append("<br/>");
		builder.append("<center>");
		builder.append("<form action=\"/SimpleServiceProvider/*\" method=\"post\">");
		builder.append("Username: <input type=\"text\" name=\"username\" width=\"20\" />");
		builder.append("Password: <input type=\"password\" name=\"password\" width=\"20\"/>");
		builder.append("<input type=\"submit\" name=\"submit\" value=\"Login\" />");
		builder.append("</form>");
		builder.append("</center>");
		builder.append("</body></html>");	
		return builder.toString();
	}
	
	
	

}
