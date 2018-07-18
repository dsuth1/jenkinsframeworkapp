package com.securepaas.demo.chartplugin;

import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Theme("valo")
public class MyVaadinUI extends UI {
	private static String baseURL;
    @Override
    protected void init(VaadinRequest request) {
        setContent(new ChartPluginExamples(request.getUserPrincipal()));
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
				port="80";
			
			baseURL="http://"+host;
			
			if(!port.equals("80"))
				baseURL=baseURL+":"+port;
		}
		return baseURL;
	}
    
    public static boolean isAllowed(String restString){
    	Client client = ClientBuilder.newClient();
		Response res = client.target(getBaseURL()+"/RestWebServices/" + restString).request("text/plain").get();
		InputStream is=res.readEntity(InputStream.class);
		if(is!=null && res.getStatus()!=403)
			return true;
		return false;
    }

}
