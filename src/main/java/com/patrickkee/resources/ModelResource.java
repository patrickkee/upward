package com.patrickkee.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Optional;
import com.patrickkee.application.util.UnprocessableEntityStatusType;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.model.Model;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.model.response.ResponseValueNumeric;
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response createModel(@PathParam("email") String email, @QueryParam("modelName") String modelName,
			@QueryParam("description") String description, @QueryParam("initialValue") BigDecimal initialValue,
			@QueryParam("targetValue") BigDecimal targetValue, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @Context UriInfo uriInfo) {

		Optional<Account> acct = FinancialModelsDb.getAccount(email);
		Model savingsForecastModel = null;

		// Parse the dates and throw unprocessable entity response if date
		// formats are invalid
		//TODO: Bean should come in with dates already de-serialized to prevent the need for this boilerplate stuff
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
			acct.get().addOrUpdateModel(savingsForecastModel);
			FinancialModelsDb.persistAccount(acct.get());

			UriBuilder locationBuilder = uriInfo.getAbsolutePathBuilder();
			locationBuilder.path(Integer.toString(savingsForecastModel.getModelId()));

			return Response.created(locationBuilder.build()).entity(savingsForecastModel).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity(
					// TODO: Refactor this - account not found is returned in
					// multiple places, so reponse message should be centralized
					// ..DRY
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeModel(@PathParam("email") String email, @PathParam("modelId") int modelId) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);

		if (acct.isPresent() && !acct.get().getModel(modelId).isPresent()) {
			return Response.status(Status.NOT_FOUND).entity(ResponseMessage.getNew("MODEL_NOT_FOUND",
					"Could not delete model because it could not be found in this account")).build();
		} else if (acct.isPresent() && null != acct.get().getModel(modelId)) {
			acct.get().removeModel(modelId);
			return Response.status(Status.NO_CONTENT).build();
		} else {
			throw new WebApplicationException(404);
		}
	}

	/**
	 * Returns the value of a model ad a given point in time
	 * @param email 
	 * @param modelId
	 * @param date
	 * @return the account value as a big decimal 
	 */
	@GET
	@Path("/{modelId}/value")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueByDate(@PathParam("email") String email, @PathParam("modelId") int modelId,
			@QueryParam("date") String date) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);

		// TODO: Refactor this into central date handling
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

		if (acct.isPresent() && acct.get().getModel(modelId).isPresent()) {
			Model model = acct.get().getModel(modelId).get();
			return Response.ok().entity(ResponseValueNumeric.getNew(model.getValue(valueAsOfDate))).build();

		} else {
			return Response.status(Status.NOT_FOUND).entity(ResponseMessage.getNew("MODEL_AND_ACCT_NOT_FOUND",
					"Could not find the specified account and model")).build();
		}
	}

	@GET
	@Path("/{modelId}/values")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@PathParam("email") String email, @PathParam("modelId") int modelId) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);

		if (acct.isPresent() && acct.get().getModel(modelId).isPresent()) {
			Model model = acct.get().getModel(modelId).get();
			return Response.ok().entity(model.getValues()).build();

		} else {
			return Response.status(Status.NOT_FOUND).entity(ResponseMessage.getNew("MODEL_AND_ACCT_NOT_FOUND",
					"Could not find the specified account and model")).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModels(@PathParam("email") String email) {
		Optional<Account> acct = FinancialModelsDb.getAccount(email);

		if (acct.isPresent()) {
			List<Model> modelList = new ArrayList<Model>(acct.get().getModels().values());
			
			return Response.ok().entity(modelList).build();
		} else {
			return Response.status(404).entity(ResponseMessage.getNew("ACCOUNT_NOT_FOUND",
					"Unable to retrieve model because the account specified was not found")).build();
		}
	}
}
