package com.securepaas.demo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/map10to30")
public class Map10to30Ws{
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }
    
    @POST
    @Path("/post")
    @Consumes("application/json")
    public Response putResource(String string){
    	
    	String result = "Created : " + string;
    	return Response.status(201).entity(result).build();
    	
    	
    }
}