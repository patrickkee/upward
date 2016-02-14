package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.patrickkee.model.account.Account;
import com.patrickkee.model.model.Model;
import com.patrickkee.model.model.SavingsForecastModel;

public class AccountTest {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("MM/dd/yyyy");

	@Test
	public void testAccountCreation() {
		Account acct = new Account("Savings", "Patrick", "Kee", "patrick.kee0@gmail.com");
		
		assertEquals("Savings", acct.getAccountName());
		assertEquals("Patrick", acct.getFirstName());
		assertEquals("Kee", acct.getLastName());
		assertEquals("patrick.kee0@gmail.com", acct.getEmail());
	}

	@Test
	public void testUpdateModel() {
		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime("08/01/2030");
		LocalDate localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		Model savingsForecastModel = SavingsForecastModel.getNew().name("Lilah's College Savings")
				.initialValue(new BigDecimal(2000)).targetValue(new BigDecimal(100000)).startDate(localStartDate)
				.endDate(localEndDate);
		
		Account acct = new Account("AccountName", "FirstName", "LastName", "testAddOrUpdateModel@gmail.com");
		
		acct.addModel(savingsForecastModel);
		acct.addModel(savingsForecastModel);
		assertEquals(1, acct.getModels().size()); //There Should only be 1 model added, regardless of executions
		BigDecimal persistedModelTargetValue = ((SavingsForecastModel) acct.getModel(savingsForecastModel.getModelId()).get()).getTargetValue();
		assertEquals(BigDecimal.valueOf(100000).setScale(2, BigDecimal.ROUND_HALF_UP),
				persistedModelTargetValue.setScale(2, BigDecimal.ROUND_HALF_UP));
				
		//Now create a model with the same name (name is identity), but change the target value
		//and update in the account. In the end there should still only be one model, but now the account should
		//be changed
		Model updatedSavingsForecastModel = SavingsForecastModel.getNew().name("Lilah's College Savings")
				.initialValue(new BigDecimal(2000)).targetValue(new BigDecimal(5000)).startDate(localStartDate)
				.endDate(localEndDate);
		acct.updateModel(updatedSavingsForecastModel);
		persistedModelTargetValue = ((SavingsForecastModel) acct.getModel(savingsForecastModel.getModelId()).get()).getTargetValue();
		
		assertEquals(1, acct.getModels().size()); 
		assertEquals(BigDecimal.valueOf(5000).setScale(2, BigDecimal.ROUND_HALF_UP),
				persistedModelTargetValue.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test
	public void testGetAccountValue() {
		Account acct = new Account("Savings", "Patrick", "Kee", "patrick.kee0@gmail.com");

		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate valueAsOfDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		assertTrue(new BigDecimal("0.00").equals(acct.getValue(valueAsOfDate)));
	}

	@Test
	public void testAddModelToAccount() throws ParseException {
		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime("08/01/2030");
		LocalDate localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		Model savingsForecastModel = SavingsForecastModel.getNew().name("Lilah's College Savings")
				.initialValue(new BigDecimal(2000)).targetValue(new BigDecimal(100000)).startDate(localStartDate)
				.endDate(localEndDate);
		Account acct = new Account("Savings", "Patrick", "Kee", "patrick.kee0@gmail.com");
		acct.addModel(savingsForecastModel);
		assertEquals(1, acct.getModels().size());
	}

	@Test
	public void testRemoveModelFromAccount() throws ParseException {
		DateTime dt = DATE_FORMATTER.parseDateTime("10/01/2015");
		LocalDate localStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = DATE_FORMATTER.parseDateTime("08/01/2030");
		LocalDate localEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		Model savingsForecastModel = SavingsForecastModel.getNew().name("Lilah's College Savings")
				.initialValue(new BigDecimal(2000)).targetValue(new BigDecimal(100000)).startDate(localStartDate)
				.endDate(localEndDate);
		Account acct = new Account("Savings", "Patrick", "Kee", "patrick.kee0@gmail.com");
		acct.addModel(savingsForecastModel);
		acct.removeModel(savingsForecastModel.getModelId());
		assertEquals(0, acct.getModels().size());
	}

	@Test
	public void testAccountSerialization() {
		Account acct = new Account("Savings", "Patrick", "Kee", "patrick.kee0@gmail.com");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			String genJson = mapper.writeValueAsString(acct);
			
			Account deserializedAcct = mapper.readValue(genJson, Account.class);
			assertEquals("Savings", deserializedAcct.getAccountName());
			assertEquals("Patrick", deserializedAcct.getFirstName());
			assertEquals("Kee", deserializedAcct.getLastName());
			assertEquals("patrick.kee0@gmail.com", deserializedAcct.getEmail());
			
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0,1);
		} 
	}

}
