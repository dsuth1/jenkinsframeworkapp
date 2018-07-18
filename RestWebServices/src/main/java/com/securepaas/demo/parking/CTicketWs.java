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

import com.securepaas.demo.parking.object.Ticket;

@Path("/ctickets")
@Produces(MediaType.APPLICATION_JSON)
public class CTicketWs{
	private static final List<Ticket> result=ATicketWs.generateTickets('C');
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getTickets() {
    	return result;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response commitTickets(Ticket ticket){
    	
    	result.add(ticket);
    	return Response.status(Status.ACCEPTED).build(); 
    }
}