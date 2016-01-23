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

import org.glassfish.jersey.server.JSONP;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Optional;
import com.patrickkee.application.util.UnprocessableEntityStatusType;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.response.ResponseValueNumeric;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts")
public class AccountResource {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	@GET
	@Path("/{email}")
	@JSONP(callback = "callback", queryParam = "callback")
	@Produces({"application/json", "application/javascript"})
	/**
	 * Allows users to get a particular account by email
	 * 
	 * @param accountId - the id of the account to be returned
	 * @return the account
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
	@JSONP(callback = "callback", queryParam = "callback")
	@Produces({"application/json", "application/javascript"})
	public Response createAccount(@QueryParam("accountName") String accountName,
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
			@QueryParam("email") String email, @Context UriInfo uriInfo) {
		
		Account acct = new Account(accountName, firstName, lastName, email);
		FinancialModelsDb.persistAccount(acct);

		UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
		locationBuilder.path(email);

		return Response.created(locationBuilder.build()).entity(FinancialModelsDb.getAccount(email).get()).build();
	}


	/**
	 * Allows users to get the value of an account for a given date
	 * 
	 * @param accountId
	 * @return
	 */
	@GET
	@Path("/{email}/value")
	@Produces("application/json")
	public Response getValueByDate(@PathParam("email") String email, @QueryParam("date") String date ) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		
		//TODO: Convert to standardized validation pattern
		// Parse the dates and throw unprocessable entity response if date
		// formats are invalid
		LocalDate valueAsOfDate = null;
		try {
			DateTime dt = DATE_FORMATTER.parseDateTime(date);
			valueAsOfDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		} catch (IllegalArgumentException e) {
			return Response.status(UnprocessableEntityStatusType.getNew()).entity(
					ResponseMessage.getNew("Could not parse date", "Date should be provided in mm/dd/yyyy format"))
					.build();
		}
		
		if (acct.isPresent()) {
			return Response.ok().entity(ResponseValueNumeric.getNew(acct.get().getValue(valueAsOfDate))).build();
		} else {
			throw new WebApplicationException(404);
		}
	}
	
}
