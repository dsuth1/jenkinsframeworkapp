package com.securepaas.demo.parking;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.jboss.system.ServiceMBeanSupport;

import com.securepaas.demo.dao.JMSBrokerConnection;

/**
 * Listens for audits coming from the AMQP broker, marshalls the XML message, and stores the audits in a database.
 */
public class AmqpTicketProcessor extends ServiceMBeanSupport implements AmqpTicketProcessorMBean{
	private static Logger log = Logger.getLogger(AmqpTicketProcessor.class.getCanonicalName());
	private MessageConsumer consumerQueue;
		
	@Override
	public void startService() {

		try {
			consumerQueue = JMSBrokerConnection.createTicketConsumer();
			consumerQueue.setMessageListener(new TicketMessageListener());
			
		} catch (JMSException e) {
			log.log(Level.SEVERE,"Failed to attach TicketMessageListener to the MessageQueue");
			log.log(Level.SEVERE,e.getMessage(),e);
			return;
		} catch (Exception e) {
			log.log(Level.SEVERE,"Failed to setup a connection with the message broker");
			log.log(Level.SEVERE,e.getMessage(),e);
			return;
		}
	}
	
	@Override
	public void createService()
	{
		
	}
	
	@Override
	public void stopService()
	{
		finalize();
	}
	
	@Override
	public void destroyService()
	{
		finalize();
	}
	
	@Override
	protected void finalize() {
		try {consumerQueue.close();} catch(Exception e){/* do nothing */};
	}
}
