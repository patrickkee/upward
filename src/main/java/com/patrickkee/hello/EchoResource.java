package com.patrickkee.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.patrickkee.model.Model;

@Path("echo")
public class EchoResource {

	@GET @Produces("application/json")
	public Model getModel(@QueryParam("m") String name) {
		return new Model(name);
	}
}
