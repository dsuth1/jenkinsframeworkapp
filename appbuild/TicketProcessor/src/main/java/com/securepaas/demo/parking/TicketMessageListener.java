package com.securepaas.demo.parking;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.PersistenceException;

import com.securepaas.demo.dao.TicketDAO;
import com.securepaas.demo.parking.object.Ticket;

public class TicketMessageListener implements MessageListener {

	private static Logger log = Logger.getLogger(TicketMessageListener.class.getCanonicalName());
	private static boolean trace=log.isLoggable(Level.INFO);
	
	public TicketMessageListener()
	{
		super();
	}
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage)
		{
			try {
				TextMessage txtMsg=(TextMessage)message;

				if(trace)
				{
					log.log(Level.INFO," Attempting to process message...");	
					log.log(Level.INFO," [x] Received '" + txtMsg.getText() + "'");
				}
				
				Ticket ticket = Ticket.deserialize(txtMsg.getText());
					
				if(!TicketDAO.createTicket(ticket))
					throw new PersistenceException("Failed to persit ticket: "+txtMsg.getText());
				else
					message.acknowledge();
				if(trace)
					log.log(Level.INFO,"Successfully saved ticket: " + ticket.serialize());
			} catch (JMSException e) {
				log.log(Level.SEVERE,"Failed to get text from JMS TextMessage object",e);
			} catch (PersistenceException e) {
				log.log(Level.SEVERE,e.getMessage());
			}

		} else if(trace)
			log.log(Level.INFO,"Message received was not of type TextMessage");
		
	}

}
