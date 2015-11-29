package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;
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
		assertTrue(response.getLocation().toString().contains("/accounts/createModelTest@gmail.com/models/338106447"));
		assertEquals("createModelTestModelName", model.getName());
		assertEquals("createModelTestModelDesc", model.getDescription());
		assertEquals(BigDecimal.valueOf(2000), model.getInitialValue());
		assertEquals(BigDecimal.valueOf(20000), model.getTargetValue());
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
	public void getModelValueTest() {
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

		Event event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("anEvent");
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(101.24));

		// Post the new event
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Get the model's value
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/value").queryParam("date", "12/31/2010")
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
		Event event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("Recurring Savings");
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(101.24));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Create a recurring yield event
		event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("Recurring Interest");
		event.setEventType(EventType.RECURRING_YIELD);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(1.00416));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		// Get the model's values
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/values")
				.request(MediaType.APPLICATION_JSON_TYPE).get(Response.class);

		//TODO: Enhance this validation, currently can't do much with the response as a string
		String respMsg = response.readEntity(String.class);
		assertEquals(200, response.getStatus());
		assertTrue(respMsg.length() > 0);
	}
}
