package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.api.event.RecurringDeposit;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.resources.ResponseMessage;

public class EventResourceTest extends BaseJerseyTest {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	@Test
	public void createEventTest() {
		final String EMAIL = "createEventTest@gmail.com";
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
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

		
		//Build the event to be created
		DateTime dt = DATE_FORMATTER.parseDateTime(START_DATE);
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime(END_DATE);
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		RecurringDeposit event = RecurringDeposit.getNew("Payroll Contribution", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(101.24));
		
		int eventId = event.getEventId(); //Store for validation of location response
		
		//Post the new event
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		ResponseMessage respMsg = response.readEntity(ResponseMessage.class);
		assertEquals(201, response.getStatus());
		assertTrue(response.getLocation().toString().contains("/accounts/createEventTest@gmail.com/models/" + 
															   MODEL_ID+"/events/" + Integer.toString(eventId)));
		assertEquals("EVENT_CREATION_SUCCESSFUL", respMsg.getMessage());
		assertEquals("The event was successfully created and persisted to the model", respMsg.getDescription());
	}

	@Test
	public void getEventsTest() {
		final String EMAIL = "getEventsTest@gmail.com";
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
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

		
		//Build the event to be created
		DateTime dt = DATE_FORMATTER.parseDateTime(START_DATE);
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime(END_DATE);
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		RecurringDeposit event1 = RecurringDeposit.getNew("event1", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(101.24));
		RecurringDeposit event2 = RecurringDeposit.getNew("event2", Periods.MONTHLY, startDate, endDate, BigDecimal.valueOf(200.00));
		
		int eventId1 = event1.getEventId(); //Store for validation of location response
		int eventId2 = event2.getEventId(); //Store for validation of location response
		
		//Post event1
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event1, MediaType.APPLICATION_JSON_TYPE), Response.class);
		assertEquals(201, response.getStatus());
		
		//Post event2
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event2, MediaType.APPLICATION_JSON_TYPE), Response.class);
		assertEquals(201, response.getStatus());
		
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events").request().get(Response.class);
		assertEquals(200, response.getStatus());
		ArrayList<Event> eventsFromServer = (ArrayList<Event>) response.readEntity(ArrayList.class);
		assertTrue(eventsFromServer.size() == 2);
	}
}



