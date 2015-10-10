package com.patrickkee.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.patrickkee.model.Account;

@Path("accounts")
public class Accounts {

	@GET
	@Produces("application/json")
	public Account getAccount(@QueryParam("accountName") String accountName, 
							  @QueryParam("firstName") String firstName,
							  @QueryParam("lastName") String lastName, 
							  @QueryParam("email") String email) {
		return Account.getAccount().accountName(accountName).firstName(firstName).lastName(lastName).email(email);
	}

	@POST
	@Produces("application/json")
	public Account createAccount( @QueryParam("accountName") String accountName, 
								  @QueryParam("firstName") String firstName,
								  @QueryParam("lastName") String lastName, 
								  @QueryParam("email") String email) 
	{
		return Account.getAccount().accountName(accountName).firstName(firstName).lastName(lastName).email(email);
	}
}
