package com.securepaas.demo.chartplugin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends com.vaadin.server.VaadinServlet {
	private static final long serialVersionUID = -8988185525993851149L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		MyVaadinUI.isAllowed("mapLess3");
    	MyVaadinUI.isAllowed("map3to10");
    	MyVaadinUI.isAllowed("map10to30");
    	MyVaadinUI.isAllowed("map30to100");
    	MyVaadinUI.isAllowed("map100to300");
    	MyVaadinUI.isAllowed("map300to1000");
    	MyVaadinUI.isAllowed("map1000Plus");
		super.doGet(req, res);	
	}
}
