package com.patrickkee.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.patrickkee.model.application.ApplicationStatus;

@Path("health")
public class HealthResource {

	@GET
	@Produces("application/json")
	public Response getHeath() {
		return Response.ok().entity(ApplicationStatus.getNew("HEALTHY", "Application is healthy!")).build();
	}

}