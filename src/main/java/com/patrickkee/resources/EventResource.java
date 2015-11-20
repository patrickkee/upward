package com.patrickkee.resources;

import java.math.BigDecimal;

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

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Optional;
import com.patrickkee.jaxrs.util.UnprocessableEntityStatusType;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.type.Model;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts/{email}/models/{modelId}/events")
public class EventResource {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");

	@POST
	@Produces("application/json")
	public Response createEvent(@PathParam("email") String email, @PathParam("modelId") int modelId,
			@QueryParam("eventName") String eventName, @QueryParam("amount") BigDecimal amount,
			@QueryParam("period") Period period, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @QueryParam("eventType") EventType eventType,
			@Context UriInfo uriInfo) {

		// TODO: Repetitive code, should be extracted to framework entity
		// validation and type conversion
		// Parse the dates and throw unprocessable entity response if date
		// formats are invalid
		LocalDate localStartDate = null;
		LocalDate localEndDate = null;
		try {
			DateTime dt = DATE_FORMATTER.parseDateTime(startDate);
			localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

			dt = DATE_FORMATTER.parseDateTime(endDate);
			localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		} catch (IllegalArgumentException e) {
			return Response.status(UnprocessableEntityStatusType.getNew()).entity(
					ResponseMessage.getNew("Could not parse date", "Date should be provided in mm/dd/yyyy format"))
					.build();
		}

		// Add the event to the model only if the account and model are found
		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		if (acct.isPresent() && acct.get().getModel(modelId).isPresent()) {
			Optional<Model> model = acct.get().getModel(modelId);

			model.get().addEvent(eventType.getNew(eventName, amount, period, localStartDate, localEndDate));

			UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
			locationBuilder.path(eventName);

			// Persist the account with the new event
			FinancialModelsDb.persistAccount(acct.get());
			return Response.created(locationBuilder.build()).entity(model.get().getEvent(eventName)).build();

		} else {
			throw new WebApplicationException(404);
		}

	}
}
