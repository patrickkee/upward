package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.resources.ResponseMessage;

public class ModelResourceTest extends BaseJerseyTest {

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
}
