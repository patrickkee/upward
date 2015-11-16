package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.model.account.Account;
import com.patrickkee.model.model.SavingsForecastModel;
import com.patrickkee.model.model.type.Model;

public class AccountTest {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	@Test
	public void TestAccountCreation() {
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
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
		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime("08/01/2030");
		LocalDate localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		Model savingsForecastModel = SavingsForecastModel.newModel().name("Lilah's College Savings")
																	.initialValue(new BigDecimal(2000))
																	.targetValue(new BigDecimal(100000))
																	.startDate(localStartDate)
																	.endDate(localEndDate);
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		acct.addModel(savingsForecastModel);
		assertEquals(1, acct.getModels().size());
	}

	@Test
	public void TestRemoveModelFromAccount() throws ParseException {
		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime("08/01/2030");
		LocalDate localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		Model savingsForecastModel = SavingsForecastModel.newModel().name("Lilah's College Savings")
																	.initialValue(new BigDecimal(2000))
																	.targetValue(new BigDecimal(100000))
																	.startDate(localStartDate)
																	.endDate(localEndDate);
		Account acct = Account.newAccount().accountName("Savings").firstName("Patrick").lastName("Kee").email("patrick.kee0@gmail.com");
		acct.addModel(savingsForecastModel);
		acct.removeModel(savingsForecastModel.getModelId());
		assertEquals(0, acct.getModels().size());
	}
}
