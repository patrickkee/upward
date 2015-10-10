package com.patrickkee.hello;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.patrickkee.model.Account;

@Path("echo")
public class EchoResource {

	@GET
	@Produces("application/json")
	public Account getModel(@QueryParam("m") String name) {
		return Account.getAccount().accountName("Savings").firstName("Patrick").lastName("Kee")
				.email("patrick.kee0@gmail.com");
		// return new SavingsForecastModel(name);
	}

	@POST
	@Produces("application/json")
	public Account createModel(@QueryParam("m") String name) {
		return Account.getAccount().accountName("Savings").firstName("Patrick").lastName("Kee")
				.email("patrick.kee0@gmail.com");
		// return new SavingsForecastModel(name);
	}
}
