package resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.patrickkee.model.impl.Account;
import com.patrickkee.resources.AccountResource;

public class AccountResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(AccountResource.class);
	}

	@Test
	public void missingAccountTest() {
		String email = "foo.bar@gmail.com";
		final String acct = target("accounts/" + email).request().get(String.class);
		int acctId = Account.newAccount().email(email).getAccountId();
		Assert.assertThat("accountId\":31", CoreMatchers.containsString(Integer.toString(acctId)));
	}
}
