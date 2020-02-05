package com.securepaas.demo.dao;

import java.util.ArrayList;
import java.util.List;

import com.securepaas.demo.parking.object.Ticket;

public class TicketDAO {
	private static final TicketDAO ticketDao=new TicketDAO();
	
	private TicketDAO()
	{
		
	}
	
	public static boolean createTicket(Ticket ticket)
	{
		return true;
	}
	
	public static List<Ticket> getTickets()
	{
		return new ArrayList<Ticket>();
	}

}
