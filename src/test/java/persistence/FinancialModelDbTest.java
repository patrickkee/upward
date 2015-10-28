package persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.patrickkee.model.impl.Account;
import com.patrickkee.persistence.AccountsDb;

public class FinancialModelDbTest {

	@Test
	public void testGetByIdWhenMissing() {
		Account acct = AccountsDb.getAccountByEmail("foooobar").get();
		assertEquals("", acct.getAccountName());
	}
	
	@Test
	public void testGetByIdWhenPresent() {
		Account acct = Account.newAccount().accountName("foo").firstName("bar").lastName("man").email("choo");
		AccountsDb.persistAccount(acct);
		acct = null;
		acct = AccountsDb.getAccountByEmail("choo").get();
		assertEquals("foo", acct.getAccountName());
	}
}
