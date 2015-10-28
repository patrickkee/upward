package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class AccountResourceTest extends BaseJerseyTest {

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

}
