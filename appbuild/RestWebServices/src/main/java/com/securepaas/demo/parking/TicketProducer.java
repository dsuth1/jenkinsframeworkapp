package com.securepaas.demo.parking;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import com.securepaas.demo.dao.TicketQueueConnection;
import com.securepaas.demo.parking.object.Ticket;

public class TicketProducer {
	private static TicketProducer producer;
	private static Logger log = Logger.getLogger(TicketProducer.class.getCanonicalName());
	private static boolean trace=log.isLoggable(Level.INFO);
	
	private final MessageProducer producerQueue;

	
	private TicketProducer() throws JMSException 
	{
		producerQueue = TicketQueueConnection.createProducer();

		if(trace)
			log.info("AMQP Message Reciever successfully initialized");
	}
	
	private synchronized static TicketProducer getInstance() throws JMSException
	{
		if(producer==null)
			producer=new TicketProducer();
		return producer;
	}
	
	public static boolean commit(Ticket ticket)
	{
		try {
			String messageText=ticket.serialize();
			TextMessage txtQueueMsg = TicketQueueConnection.getSession().createTextMessage(messageText);
			getInstance().producerQueue.send(txtQueueMsg);
			return true;
		} catch (JMSException e) {
			log.log(Level.SEVERE,"Failed to submit ticket to queue: "+e.getMessage(),e);
			return false;
		} catch (Exception e) {
			log.log(Level.SEVERE,"Failed to submit ticket to queue due to an unknown error: "+e.getMessage(),e);
			return false;
		}
		
	}
	
	@Override
	protected void finalize() {
		try {producerQueue.close();} catch(Exception e){/* do nothing */};
	}
}
