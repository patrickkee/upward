package com.patrickkee.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("health")
public class HealthCheckResource {

	@GET
	@Produces("text/html")
	public String getHeath() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return "Financial Models Application is healthy as of " + dateFormat.format(date) + "!";
	}

}
