package com.securepaas.demo.parking;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@WebService
@SOAPBinding(style = Style.RPC)
public interface SoapServiceInterface{
	public final static String TARGET_NAMESPACE="http://demo.securepaas.com/";
    
	@WebMethod public List<Shift> getAShifts();
    @WebMethod public List<Shift> getBShifts();
    @WebMethod public List<Shift> getCShifts();
}
