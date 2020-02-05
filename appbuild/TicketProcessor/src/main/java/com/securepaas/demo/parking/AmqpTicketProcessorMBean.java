package com.securepaas.demo.parking;

import org.jboss.system.ServiceMBean;

public interface AmqpTicketProcessorMBean extends ServiceMBean {

	    
	public void startService();
	public void stopService();
	public void createService();
	public void destroyService();
	
}
