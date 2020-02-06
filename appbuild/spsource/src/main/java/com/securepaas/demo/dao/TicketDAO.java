package com.securepaas.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.securepaas.demo.parking.object.Ticket;

public class TicketDAO {
	private static TicketDAO ticketDao;
	private static ReadWriteLock rwLock=new ReentrantReadWriteLock();
	private static Logger log = Logger.getLogger(JMSBrokerConnection.class.getCanonicalName());
	private static boolean trace=log.isLoggable(Level.INFO);
	private List<Ticket> tickets=new ArrayList<Ticket>();
	private final Connection conn;
	
	private TicketDAO() throws Exception
	{
        String userName = System.getenv("DATABASE_USER");
        String password = System.getenv("DATABASE_PASSWORD");  
        
        // EX. "jdbc:mysql://localhost:3306/library";
        String url = System.getenv("DATABASE_JDBCURL");

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {
        	conn = DriverManager.getConnection(url, userName, password);
        	updateTickets();
        } // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
        	log.log(Level.SEVERE,e.getMessage());
        	throw new Exception(e.getMessage(),e);
        }        	
	}
	
	private boolean commitTicket(Ticket ticket)
	{
		return false;
	}
	
	private void updateTickets()
	{
		rwLock.writeLock().lock();
		//To be filled in
		DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
    	Result<Record> result = create.select().from(AUTHOR).fetch();
		rwLock.writeLock().unlock();
	}
	
	@Override
	protected void finalize()
	{
		try {conn.close();} catch(Exception e){/* do nothing */};
	}
	
	private static synchronized TicketDAO getInstance() throws Exception
	{
		if(ticketDao==null)
			ticketDao=new TicketDAO();
		return ticketDao;
	}
	
	public static boolean createTicket(Ticket ticket)
	{
		try {
			return getInstance().commitTicket(ticket);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static List<Ticket> getTickets()
	{
		try {
			List<Ticket> tickets;
			rwLock.readLock().lock();
			tickets=Collections.unmodifiableList(getInstance().tickets);
			rwLock.readLock().unlock();
			return tickets;
		} catch (Exception e) {
			return new ArrayList<Ticket>();
		}
	}
	
	protected static void refreshTickets()
	{
		try {
			getInstance().updateTickets();
		} catch (Exception e) {
			/*DO NOTHING*/
		}
	}

}
