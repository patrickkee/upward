package com.patrickkee.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.common.base.Optional;
import com.patrickkee.model.account.Account;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts")
public class AccountResource {

	@GET
	@Path("/{email}")
	@Produces("application/json")
	/**
	 * Allows users to get a particular account by email
	 * 
	 * @param accountId
	 * @return
	 */
	public Account getAccountByEmail(@PathParam("email") String email) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent()) {
			return acct.get();
		} else {
			throw new WebApplicationException(404);
		}
	}

	@POST
	@Produces("application/json")
	public Response createAccount(@QueryParam("accountName") String accountName,
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
			@QueryParam("email") String email, @Context UriInfo uriInfo) {
		Account acct = Account.newAccount().accountName(accountName).firstName(firstName).lastName(lastName)
				.email(email);
		FinancialModelsDb.persistAccount(acct);

		UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
		locationBuilder.path(email);

		return Response.created(locationBuilder.build()).entity(FinancialModelsDb.getAccount(email).get()).build();
	}
}
