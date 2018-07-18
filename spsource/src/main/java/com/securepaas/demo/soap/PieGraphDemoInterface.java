package com.securepaas.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@WebService
@SOAPBinding(style = Style.RPC)
public interface PieGraphDemoInterface{
	public final static String TARGET_NAMESPACE="http://demo.securepaas.com/";
    
	@WebMethod public boolean others();
    @WebMethod public boolean opera();
    @WebMethod public boolean safari();
    @WebMethod public boolean chrome();
    @WebMethod public boolean ie();
    @WebMethod public boolean firefox();

}
