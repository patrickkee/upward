package com.patrickkee.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.JSONP;

import com.patrickkee.model.application.ApplicationStatus;

@Path("health")
public class HealthResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHeath() {
		return Response.ok().entity(ApplicationStatus.getNew("HEALTHY", "Application is healthy!")).build();
	}

	@GET
	@Path("/jsonp")
	@JSONP(callback = "callback", queryParam = "callback")
	@Produces({"application/json", "application/javascript"})
	public Response getHeathJsonp() {
		return Response.ok().entity(ApplicationStatus.getNew("HEALTHY", "Application is healthy!")).build();
	}
}
