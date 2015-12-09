package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.base.Optional;
import com.patrickkee.model.account.Account;
import com.patrickkee.persistence.FinancialModelsDb;

public class FinancialModelDbTest {

	@Test
	public void testGetByIdWhenMissing() {
		Optional<Account> acct = FinancialModelsDb.getAccount("foooobar");
		assertTrue(!acct.isPresent());
	}

	@Test
	public void testGetByIdWhenPresent() {
		Account acct = new Account("foo", "bar", "man", "choo");
		FinancialModelsDb.persistAccount(acct);
		acct = null;
		acct = FinancialModelsDb.getAccount("choo").get();
		assertEquals("foo", acct.getAccountName());
	}
}
