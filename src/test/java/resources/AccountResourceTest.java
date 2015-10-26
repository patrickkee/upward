package resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.patrickkee.model.impl.Account;

public class AccountResourceTest extends BaseJerseyTest {

	@Test
	public void getMissingAccountTest() {
		String email = "foo.bar@gmail.com";
		final String acct = target("accounts/" + email).request().get(String.class);
		int acctId = Account.newAccount().email(email).getAccountId();
		Assert.assertThat(acct, CoreMatchers.containsString(Integer.toString(acctId)));
	}

	@Test
	public void createAccountTest() {
		String placeholder = "blah";
		String response = target("accounts").queryParam("accountName", "Savings").queryParam("firstName", "Patrick")
				.queryParam("lastName", "Kee").queryParam("email", "foo.bar@gmail.com")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(placeholder, MediaType.APPLICATION_JSON_TYPE), String.class);

		Assert.assertTrue(response.contains("Savings"));
		Assert.assertTrue(response.contains("Patrick"));
		Assert.assertTrue(response.contains("Kee"));
		Assert.assertTrue(response.contains("foo.bar@gmail.com"));
	}
	
}
