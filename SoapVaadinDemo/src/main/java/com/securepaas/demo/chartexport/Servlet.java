package com.securepaas.demo.chartexport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends com.vaadin.server.VaadinServlet {
	private static final long serialVersionUID = -8988185525993851149L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		MyVaadinUI.tryWebService();
		super.doGet(req, res);	
	}
}
