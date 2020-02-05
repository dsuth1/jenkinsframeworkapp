package com.securepaas.demo.parking;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.securepaas.demo.dao.TicketDAO;
import com.securepaas.demo.parking.object.Ticket;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
public class TicketWs{
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets() {
    	 return TicketDAO.getTickets();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response commitTickets(Ticket ticket){
    	if(TicketProducer.commit(ticket))
    		return Response.status(Status.ACCEPTED).build();
    	else
    		return Response.status(Status.EXPECTATION_FAILED).build();
    }
}