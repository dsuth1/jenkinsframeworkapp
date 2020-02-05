package com.securepaas.demo.dao;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.client.AMQQueue;


public class TicketQueueConnection {
	private static TicketQueueConnection ticketQueueConnection;
	private static Logger log = Logger.getLogger(TicketQueueConnection.class.getCanonicalName());
	private static boolean trace=log.isLoggable(Level.INFO);
	private final Connection connection;
	private final Session session;
	private final Destination destinationQueue;

	
	private TicketQueueConnection() throws JMSException
	{
		try {
			String QUEUE_NAME=System.getenv("TICKETBROKER_QUEUE");
		    String QUEUE_TYPE=System.getenv("TICKETBROKER_QUEUETYPE");
		    String CLIENT_ID=System.getenv("TICKETBROKER_CLIENTID");
		    String VIRTUAL_HOST=System.getenv("TICKETBROKER_VIRTHOST");
		    String HOSTNAME=System.getenv("TICKETBROKER_HOST");
		    int PORT=Integer.valueOf(System.getenv("TICKETBROKER_PORT"));
		    if(PORT > 65535 || PORT <=0)
		    	throw new IllegalArgumentException("Property TICKETBROKER_PORT must be a non-negative less then 65536");
		    	
		    boolean SSL=Boolean.valueOf(System.getenv("TICKETBROKER_ENABLE_SSL"));
		    String USERNAME=null;
		    String PASSWORD=null;
		    try {
				USERNAME=System.getenv("TICKETBROKER_USER");
				PASSWORD=System.getenv("TICKETBROKER_PASS");
			} catch (Exception e) {
				log.log(Level.INFO,e.getMessage());
			}
	    		
			StringBuilder builder=new StringBuilder().append("amqp://");
			
			if(USERNAME!=null && PASSWORD!=null)
			{
				builder.append(USERNAME)
				.append(":")
				.append(PASSWORD);
			} else
				builder.append(":");		
			builder.append("@")
			.append(CLIENT_ID)
			.append("/")
			.append(VIRTUAL_HOST)
			.append("?ssl='")
			.append(SSL)
			.append("'&brokerlist='tcp://")
			.append(HOSTNAME)
			.append(":")
			.append(PORT)
			.append("?ssl='")
			.append(SSL)
			.append("''");
			
			String url=builder.toString();
			
			if(trace)
				log.info("AMQP URL is "+url+" and Queue Type/Name is "+QUEUE_TYPE+"/"+QUEUE_NAME);
			
			connection = new AMQConnection(new URI(url).toASCIIString());
			
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destinationQueue = new AMQQueue(QUEUE_TYPE, QUEUE_NAME);
	
			if(trace)
				log.info("AMQP Message Reciever successfully initialized");
			
		} catch (Exception e) {
			log.log(Level.SEVERE,"Failed to connect to AMQ broker: "+e.getMessage(),e);
			throw new JMSException("Failed to connect to AMQ broker");
		}
	}
	
	private static synchronized TicketQueueConnection getInstance() throws JMSException
	{
		if (ticketQueueConnection==null)
			ticketQueueConnection=new TicketQueueConnection();
		return ticketQueueConnection;
	}
	
	public static Session getSession() throws JMSException
	{
		return getInstance().session;
	}
	
	public static MessageConsumer createConsumer() throws JMSException
	{
		return getInstance().session.createConsumer(getInstance().destinationQueue);
	}
	
	public static MessageProducer createProducer() throws JMSException
	{
		return getInstance().session.createProducer(getInstance().destinationQueue);
	}
	
	@Override
	protected void finalize() {
		// release any resources (close AMQP connection/session)
		//@formatter:off
		try {session.close();} catch(Exception e){/* do nothing */};
		try {connection.close();} catch (Exception e) {/* do nothing */};//@formatter:on
	}
}
