package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.model.response.ResponseValueNumeric;

public class AccountResourceTest extends BaseJerseyTest {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	@SuppressWarnings("unused")
	@Test(expected = NotFoundException.class)
	public void getMissingAccountTest() {
		String email = "getMissingAccountTest@gmail.com";
		String acct = target("accounts/" + email).request().get(String.class);
	}

	@Test
	public void getAccountTest() {
		// Create the account
		String email = "getAccountTest@gmail.com";
		Response response = target("accounts").queryParam("accountName", "Savings").queryParam("firstName", "Patrick")
				.queryParam("lastName", "Kee").queryParam("email", email).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.TEXT_PLAIN), Response.class);

		Assert.assertEquals(201, response.getStatus());
		// TODO: De-serialze to Account.class rather than String.class,
		// currently blocked b/c of jackson de-serialization errors
		assertTrue(response.readEntity(String.class).contains(email));

		// Find the account
		response = target("accounts/" + email).request().get(Response.class);
		assertEquals(200, response.getStatus());
		assertTrue(response.readEntity(String.class).contains(email));
	}

	@Test
	public void createAccountTest() {
		Response response = target("accounts").queryParam("accountName", "Savings").queryParam("firstName", "Patrick")
				.queryParam("lastName", "Kee").queryParam("email", "foo.bar@gmail.com")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.TEXT_PLAIN), Response.class);

		String jsonEntity = response.readEntity(String.class);

		Assert.assertTrue(jsonEntity.contains("Savings"));
		Assert.assertTrue(jsonEntity.contains("Patrick"));
		Assert.assertTrue(jsonEntity.contains("Kee"));
		Assert.assertTrue(jsonEntity.contains("foo.bar@gmail.com"));
	}

	@Test
	public void getAccountValueTest() {
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

		
		//Add a recurring deposit event
		Event event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("Payroll Contribution");
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(101.24));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);

		//Add a recurring yield event
		event = new Event();
		event.setPeriod(Period.MONTHLY);
		event.setName("Savings Interest");
		event.setEventType(EventType.RECURRING_YIELD);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setValue(BigDecimal.valueOf(0.00416));
		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity(event, MediaType.APPLICATION_JSON_TYPE), Response.class);
		
		//Get the account's value
		response = target("accounts/" + EMAIL + "/value")
					.queryParam("date", "12/31/2010")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.get(Response.class);
		
		ResponseValueNumeric respMsg = response.readEntity(ResponseValueNumeric.class);
		assertEquals(200, response.getStatus());
		assertEquals(BigDecimal.valueOf(3350.39), respMsg.getValue());
	}
	
}
