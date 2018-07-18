package com.securepaas.demo.chartexport;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.securepaas.demo.soap.PieGraphDemoInterface;
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
        getPage().setTitle("Vaadin Charts Export Demo");
        tryWebService();
	    setContent(new ChartExportDemo(request.getUserPrincipal()));
		
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
    
    public static void tryWebService()
    {
    	String wsBaseURL=getBaseURL()+"/SoapWebServices/";      
		try{
		Service service = Service.create(new URL(wsBaseURL+"PieChart?wsdl"), new QName(PieGraphDemoInterface.TARGET_NAMESPACE, "PieChartWs"));
		PieGraphDemoInterface pieChartWs=service.getPort(PieGraphDemoInterface.class);
					
			try{
				if(pieChartWs.opera())
					ChartExportDemo.opera = true;
			}catch(Exception e){ChartExportDemo.opera = false;}
			try{
				if(pieChartWs.safari())
					ChartExportDemo.safari = true;
			}catch(Exception e){ChartExportDemo.safari = false;}
			try{
				if(pieChartWs.chrome())
					ChartExportDemo.chrome = true;
			}catch(Exception e){ChartExportDemo.chrome = false;}
			try{
				if(pieChartWs.ie())
					ChartExportDemo.ie = true;
			}catch(Exception e){ChartExportDemo.ie = false;}
			try{
				if(pieChartWs.firefox())
					ChartExportDemo.firefox = true;
			}catch(Exception e){ChartExportDemo.firefox = false;}
			try{
				if(pieChartWs.others())
					ChartExportDemo.others = true;
			}catch(Exception e){ChartExportDemo.others = false;}
		}catch(Exception e){System.out.println("Service creation bombed");e.printStackTrace();}
    }

}
