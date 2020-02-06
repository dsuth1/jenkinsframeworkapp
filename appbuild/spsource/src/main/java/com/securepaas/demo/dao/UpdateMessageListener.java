package com.securepaas.demo.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.PersistenceException;

import com.securepaas.demo.dao.TicketDAO;

public class UpdateMessageListener implements MessageListener {

	private static Logger log = Logger.getLogger(UpdateMessageListener.class.getCanonicalName());
	private static boolean trace=log.isLoggable(Level.INFO);
	
	public UpdateMessageListener()
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
				TicketDAO.refreshTickets();
				message.acknowledge();	
			} catch (JMSException e) {
				log.log(Level.SEVERE,"Failed to get text from JMS TextMessage object",e);
			} catch (PersistenceException e) {
				log.log(Level.SEVERE,e.getMessage());
			}

		} else if(trace)
			log.log(Level.INFO,"Message received was not of type TextMessage");
		
	}

}
