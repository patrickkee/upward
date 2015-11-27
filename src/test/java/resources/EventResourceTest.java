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

		
		Event event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("anEvent");
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(101.24));
	
		int eventId = event.getEventId(); //Store for validation of location response
		
		//Post the new event
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		ResponseMessage respMsg = response.readEntity(ResponseMessage.class);
		assertEquals(201, response.getStatus());
		assertTrue(response.getLocation().toString().contains("/accounts/createEventTest@gmail.com/models/338106447/events/" + Integer.toString(eventId)));
		assertEquals("EVENT_CREATION_SUCCESSFUL", respMsg.getMessage());
		assertEquals("The event was successfully created and persisted to the model", respMsg.getDescription());
	}

}



