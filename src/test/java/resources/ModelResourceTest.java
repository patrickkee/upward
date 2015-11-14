package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

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

		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			startDate = formatter.parse(START_DATE);
			endDate = formatter.parse(END_DATE);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonEntity = response.readEntity(String.class);

		assertEquals(201, response.getStatus());
		assertTrue(null != response.getLocation());
		assertTrue(startDate != null);
		assertTrue(endDate != null);
		assertTrue(jsonEntity.contains("createModelTest"));
		assertTrue(jsonEntity.contains("createModelTestFirstName"));
		assertTrue(jsonEntity.contains("createModelTestLastName"));
		assertTrue(jsonEntity.contains("createModelTest@gmail.com"));
		assertTrue(jsonEntity.contains("createModelTestModelName"));
		assertTrue(jsonEntity.contains("createModelTestModelDesc"));
		assertTrue(jsonEntity.contains("2000"));
		assertTrue(jsonEntity.contains("20000"));
		assertTrue(jsonEntity.contains("01/25/2010"));
		assertTrue(jsonEntity.contains("01/25/2020"));
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
		final String START_DATE = "01/25/2010";
		final String END_DATE = "01/25/2020";
		Response response = target("accounts").queryParam("accountName", "createModelTest")
				.queryParam("firstName", "createModelTestFirstName").queryParam("lastName", "createModelTestLastName")
				.queryParam("email", "deleteModelModelNotFoundTest@gmail.com").request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity("foo", MediaType.APPLICATION_JSON_TYPE), Response.class);

		response = target("accounts/deleteModelModelNotFoundTest@gmail.com/models/0")
				.request(MediaType.APPLICATION_JSON_TYPE).delete(Response.class);

		// TODO: validate response object intelligently by de-serializing into
		// object
		String jsonEntity = response.readEntity(String.class);

		assertEquals(404, response.getStatus());
		assertTrue(jsonEntity.contains("MODEL_NOT_FOUND"));
		assertTrue(jsonEntity.contains("Could not delete model because it could not be found in this account"));
	}
}
