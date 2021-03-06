package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.patrickkee.api.event.RecurringDeposit;
import com.patrickkee.api.event.RecurringYield;
import com.patrickkee.model.event.Periods;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.model.response.ResponseValueNumeric;
import com.patrickkee.resources.ResponseMessage;

public class ModelResourceTest extends BaseJerseyTest {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");

	@Test
	public void createModelTest() {
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", "createModelTest@gmail.com").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/createModelTest@gmail.com/models")
				.queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc").queryParam("initialValue", 2000)
				.queryParam("targetValue", 20000).queryParam("startDate", START_DATE).queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		SavingsForecastModel model = response.readEntity(SavingsForecastModel.class);
		
		assertEquals(201, response.getStatus());
		assertTrue(response.getLocation().toString().contains("/accounts/createModelTest@gmail.com/models/" + model.getModelId()));
		assertEquals("createModelTestModelName", model.getName());
		assertEquals("createModelTestModelDesc", model.getDescription());
		assertEquals(BigDecimal.valueOf(2000), model.getInitialValue());
		assertEquals(BigDecimal.valueOf(20000), model.getTargetValue());
	}

	@Test
	public void updateModelTest() {
		final String EMAIL = "updateModelTest@gmail.com";
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
		
		final String UPDATED_START_DATE = "01/25/2012";
		final String UPDATED_END_DATE = "01/25/2022";
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTime dt; 
		dt = formatter.parseDateTime(UPDATED_START_DATE);
		LocalDate updatedStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		dt = formatter.parseDateTime(UPDATED_END_DATE);
		LocalDate updatedEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", EMAIL).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		//Create the model initially
		response = target("accounts/" + EMAIL + "/models")
				.queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc")
				.queryParam("initialValue", 2000)
				.queryParam("targetValue", 2000)
				.queryParam("startDate", START_DATE)
				.queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);
		SavingsForecastModel initialModel = response.readEntity(SavingsForecastModel.class);
				
		//Now update the model that was just created
		response = target("accounts/" + EMAIL + "/models/" + initialModel.getModelId())
				.queryParam("description", "updatedCreateModelTestModelDesc")
				.queryParam("initialValue", 2002)
				.queryParam("targetValue", 2002)
				.queryParam("startDate", UPDATED_START_DATE)
				.queryParam("endDate", UPDATED_END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);
		SavingsForecastModel updatedModel = response.readEntity(SavingsForecastModel.class);
		
		assertEquals(201, response.getStatus());
		assertEquals("createModelTestModelName", updatedModel.getName());
		assertEquals("updatedCreateModelTestModelDesc", updatedModel.getDescription());
		assertEquals(BigDecimal.valueOf(2002), updatedModel.getInitialValue());
		assertEquals(BigDecimal.valueOf(2002), updatedModel.getTargetValue());
		assertEquals(updatedStartDate, updatedModel.getStartDate());
		assertEquals(updatedEndDate, updatedModel.getEndDate());
	}

	
	@Test
	public void createModelTestInvalidDateFormat() {
		final String START_DATE = "foobar";
		final String END_DATE = "01/25/2020";
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", "createModelTestInvalidDateFormat@gmail.com")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/createModelTestInvalidDateFormat@gmail.com/models")
				.queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc").queryParam("initialValue", 2000)
				.queryParam("targetValue", 20000).queryParam("startDate", START_DATE).queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		// TODO: validate response object intelligently by de-serializing into
		// object
		String jsonEntity = response.readEntity(String.class);

		assertEquals(422, response.getStatus());
		assertEquals("Unprocessable Entity", response.getStatusInfo().getReasonPhrase());
		assertEquals(Response.Status.Family.CLIENT_ERROR, response.getStatusInfo().getFamily());
		assertTrue(jsonEntity.contains("Could not parse date"));
		assertTrue(jsonEntity.contains("Date should be provided in mm/dd/yyyy format"));
	}

	@Test
	public void createModelTestAcctNotFound() {
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";

		Response response = target("accounts/createModelTestAcctNotFound@gmail.com/models")
				.queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc").queryParam("initialValue", 2000)
				.queryParam("targetValue", 20000).queryParam("startDate", START_DATE).queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		// TODO: validate response object intelligently by de-serializing into
		// object
		String jsonEntity = response.readEntity(String.class);

		assertEquals(404, response.getStatus());
		assertTrue(jsonEntity.contains("ACCOUNT_NOT_FOUND"));
		assertTrue(jsonEntity.contains("Unable to create model because account was not found"));
	}

	@Test
	public void deleteModelModelNotFoundTest() {
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", "deleteModelModelNotFoundTest@gmail.com").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/deleteModelModelNotFoundTest@gmail.com/models/0")
				.request(MediaType.APPLICATION_JSON_TYPE).delete(Response.class);

		ResponseMessage responseObj = response.readEntity(ResponseMessage.class);

		assertEquals(404, response.getStatus());
		assertEquals("MODEL_NOT_FOUND", responseObj.getMessage());
		assertEquals("Could not delete model because it could not be found in this account",
				responseObj.getDescription());
	}

	@Test
	public void getModelValueTest() throws UnsupportedEncodingException {
		final String EMAIL = "getModelValueTest@gmail.com";
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
		Response response = target("accounts").queryParam("accountName", "getModelValueTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", EMAIL).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/" + EMAIL + "/models").queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc").queryParam("initialValue", 2000)
				.queryParam("targetValue", 20000).queryParam("startDate", START_DATE).queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		SavingsForecastModel aModel = response.readEntity(SavingsForecastModel.class);
		final String MODEL_ID = Integer.toString(aModel.getModelId());

		// Build the event to be created
		DateTime dt = DATE_FORMATTER.parseDateTime(START_DATE);
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime(END_DATE);
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		RecurringDeposit event = RecurringDeposit.getNew("getModelValueTest", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(101.24));

		// Post the new event
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Get the model's value
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/values/" + URLEncoder.encode("12/31/2010", "UTF-8"))
				.request(MediaType.APPLICATION_JSON_TYPE).get(Response.class);

		ResponseValueNumeric respMsg = response.readEntity(ResponseValueNumeric.class);
		assertEquals(200, response.getStatus());
		assertEquals(BigDecimal.valueOf(3214.88), respMsg.getValue());
	}

	@Test
	public void getModelValuesTest() {
		final String EMAIL = "createEventTest@gmail.com";
		final String START_DATE = "01/01/2010";
		final String END_DATE = "01/31/2020";
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", EMAIL).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/" + EMAIL + "/models").queryParam("modelName", "createModelTestModelName")
				.queryParam("description", "createModelTestModelDesc").queryParam("initialValue", 2000)
				.queryParam("targetValue", 20000).queryParam("startDate", START_DATE).queryParam("endDate", END_DATE)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		SavingsForecastModel aModel = response.readEntity(SavingsForecastModel.class);
		final String MODEL_ID = Integer.toString(aModel.getModelId());

		// Build the event to be created
		DateTime dt = DATE_FORMATTER.parseDateTime(START_DATE);
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime(END_DATE);
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		// Create a recurring deposit event
		RecurringDeposit depositEvent = RecurringDeposit.getNew("Recurring Savings", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(101.24));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(depositEvent, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Create a recurring yield event
		RecurringYield yieldEvent = RecurringYield.getNew("Recurring Interest", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(0.00416));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(yieldEvent, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Get the model's values
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/values")
				.request(MediaType.APPLICATION_JSON_TYPE).get(Response.class);

		// TODO: Enhance this validation, currently can't do much with the
		// response as a string
		String respMsg = response.readEntity(String.class);
		assertEquals(200, response.getStatus());
		assertTrue(respMsg.length() > 0);
	}

	@Ignore
	@Test
	public void getModelsTest() {
		// TODO: Implement test to validate that account models are returned.
		assertTrue(false);
	}

}
