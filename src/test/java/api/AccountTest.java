package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.patrickkee.model.Account;

public class AccountTest {

	@Test
	public void TestAccountCreation () {
		Account acct = Account.getAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		assertEquals("Savings",acct.getName());
		assertEquals("Patrick",acct.getFirstName());
		assertEquals("Kee",acct.getLastName());
		assertEquals("patrick.kee0@gmail.com",acct.getEmail());
	}
	
	@Test
	public void TestGetAccountValue () {
		Account acct = Account.getAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		assertTrue(new BigDecimal("100.21").equals(acct.getValue(new Date())));
	}
	
}
