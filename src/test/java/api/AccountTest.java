package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.patrickkee.model.Account;
import com.patrickkee.model.Model;
import com.patrickkee.model.impl.SavingsForecastModel;

public class AccountTest {

	@Test
	public void TestAccountCreation() {
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		int accountId = -59763533; 
		assertEquals(accountId, acct.getAccountId());
		assertEquals("Savings", acct.getAccountName());
		assertEquals("Patrick", acct.getFirstName());
		assertEquals("Kee", acct.getLastName());
		assertEquals("patrick.kee0@gmail.com", acct.getEmail());
	}

	@Test
	public void TestGetAccountValue() {
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee")
				.email("patrick.kee0@gmail.com");
		assertTrue(new BigDecimal("100.21").equals(acct.getValue(new Date())));
	}

	@Test
	public void TestAddModelToAccount() throws ParseException {
		Model savingsForecastModel = SavingsForecastModel.newModel().name("Lilah's College Savings")
																	.initialValue(new BigDecimal(2000))
																	.targetValue(new BigDecimal(100000))
																	.startDate(new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2015"))
																	.endDate(new SimpleDateFormat("dd/MM/yyyy").parse("08/01/2030"));
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		acct.addModel(savingsForecastModel);
		assertEquals(1, acct.getModels().size());
	}
}
