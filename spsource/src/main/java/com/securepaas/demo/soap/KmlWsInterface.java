package com.securepaas.demo.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@WebService
@SOAPBinding(style = Style.RPC)
public interface KmlWsInterface{
	public final static String TARGET_NAMESPACE="http://demo.securepaas.com/";
    
	@WebMethod public String operationOne();
    @WebMethod public String operationTwo();
}
