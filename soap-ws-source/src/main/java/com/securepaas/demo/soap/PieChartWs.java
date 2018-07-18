package com.securepaas.demo.soap;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServlet;

import com.securepaas.demo.soap.PieGraphDemoInterface;

@WebService(targetNamespace = PieGraphDemoInterface.TARGET_NAMESPACE, serviceName="PieChartWs")
public class PieChartWs extends HttpServlet implements PieGraphDemoInterface  {
	private static final long serialVersionUID = -3132606314883418291L;

	@WebMethod
	public boolean others() {
		return true;
	}

	@WebMethod
	public boolean opera() {
		return true;
	}

	@WebMethod
	public boolean safari() {
		return true;
	}

	@WebMethod
	public boolean chrome() {
		return true;
	}

	@WebMethod
	public boolean ie() {
		return true;
	}

	@WebMethod
	public boolean firefox() {
		return true;
	}    
}