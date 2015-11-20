package com.patrickkee.resources;

import java.math.BigDecimal;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Optional;
import com.patrickkee.jaxrs.util.UnprocessableEntityStatusType;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.model.model.type.Model;
import com.patrickkee.persistence.FinancialModelsDb;

@Path("accounts/{email}/models")
public class ModelResource {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");

	/**
	 * Creates a new model for a given account
	 * 
	 * @param email
	 * @param modelName
	 * @param description
	 * @param initialValue
	 * @param targetValue
	 * @param startDate
	 * @param endDate
	 * @param uriInfo
	 * @return Response with the account
	 */
	@POST
	@Produces("application/json")
	public Response createModel(@PathParam("email") String email, @QueryParam("modelName") String modelName,
			@QueryParam("description") String description, @QueryParam("initialValue") BigDecimal initialValue,
			@QueryParam("targetValue") BigDecimal targetValue, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @Context UriInfo uriInfo) {

		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		Model savingsForecastModel = null;

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

		// Create the new model
		savingsForecastModel = SavingsForecastModel.getNew().name(modelName).description(description)
				.initialValue(initialValue).targetValue(targetValue).startDate(localStartDate).endDate(localEndDate);

		// Validate that the account could be found and add the model to the
		// account, otherwise throw not found error
		if (acct.isPresent()) {
			acct.get().addModel(savingsForecastModel);
			FinancialModelsDb.persistAccount(acct.get());

			UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
			locationBuilder.path(Integer.toString(savingsForecastModel.getModelId()));
			
			return Response.created(locationBuilder.build()).entity(savingsForecastModel).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity(
					ResponseMessage.getNew("ACCOUNT_NOT_FOUND", "Unable to create model because account was not found"))
					.build();
		}

	}

	/**
	 * Deletes a model for the specified account
	 * 
	 * @param email
	 * @param modelId
	 * @return
	 */
	@DELETE
	@Path("/{modelId}")
	@Produces("application/json")
	public Response removeModel(@PathParam("email") String email, @PathParam("modelId") int modelId) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);

		if (acct.isPresent() && null == acct.get().getModel(modelId)) {
			return Response.status(Status.NOT_FOUND).entity(ResponseMessage.getNew("MODEL_NOT_FOUND",
					"Could not delete model because it could not be found in this account")).build();
		} else if (acct.isPresent() && null != acct.get().getModel(modelId)) {
			acct.get().removeModel(modelId);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			throw new WebApplicationException(404);
		}
	}

}
