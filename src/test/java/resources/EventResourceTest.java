package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.patrickkee.model.event.YieldEventRecurring;
import com.patrickkee.model.model.SavingsForecastModel;

public class EventResourceTest extends BaseJerseyTest {

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

		response = target("accounts/" + EMAIL + "/models/" + MODEL_ID + "/events")
					.queryParam("eventName", "anEventName")
					.queryParam("amount", "1.0041")
					.queryParam("period", "MONTHLY")
					.queryParam("startDate", START_DATE)
					.queryParam("endDate", END_DATE)
					.queryParam("eventType", "RECURRING_YIELD")
					.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		YieldEventRecurring anEvent = response.readEntity(YieldEventRecurring.class);
		assertEquals(201, response.getStatus());
		assertTrue(response.getLocation().toString().contains("/accounts/createEventTest@gmail.com/models/338106447/events/anEventName"));
		assertEquals("anEventName", anEvent.getName());

	}

}



