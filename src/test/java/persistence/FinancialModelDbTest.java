package persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.patrickkee.model.Account;
import com.patrickkee.persistence.AccountsDb;

public class FinancialModelDbTest {

	@Test
	public void testGetByIdWhenMissing() {
		Account acct = AccountsDb.getAccountById(455442242);
		assertEquals("", acct.getAccountName());
	}
	
	@Test
	public void testGetByIdWhenPresent() {
		Account acct = Account.newAccount().accountName("foo").firstName("bar").lastName("man").email("choo");
		int accountId = acct.getAccountId();
		AccountsDb.persistAccount(acct);
		acct = null;
		acct = AccountsDb.getAccountById(accountId);
		assertEquals("foo", acct.getAccountName());
	}
}
