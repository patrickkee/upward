package com.patrickkee.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.patrickkee.model.Account;
import com.patrickkee.persistence.AccountsDb;

@Path("accounts")
public class Accounts {

	@GET
	@Path("/{accountId}")
	@Produces("application/json")
	/**
	 * Allows users to get a particular account
	 * @param accountId
	 * @return
	 */
	public Account getAccountById(@PathParam("accountId") int accountId) {
		Account acct = AccountsDb.getAccountById(accountId);
		if (null != acct && acct.getAccountId() != -1) {
			return acct;
		} else {
			return acct;
			//return Response.status(Response.Status.NOT_FOUND).entity("Account not found for accountId:"+Integer.toString(accountId));
		}
		
	}

	@POST
	@Produces("application/json")
	public Account createAccount(@QueryParam("accountName") String accountName,
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
			@QueryParam("email") String email) 
	{
		Account acct = Account.newAccount().accountName(accountName).firstName(firstName).lastName(lastName).email(email);
		AccountsDb.persistAccount(acct);
				return AccountsDb.getAccountById(acct.getAccountId());
	}
}
