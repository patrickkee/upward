package model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.model.event.DepositEventRecurring;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.SavingsForecastModel;

public class SavingsForecastModelRecurringDepositTest {

	@Test
	public void getValue() {
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.getNew().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		model.addEvent(DepositEventRecurring.getNew("VALUE_TEST_EVENT", BigDecimal.valueOf(100.50), Period.MONTHLY, startDate, endDate));

		// Test intermediary model value
		dt = formatter.parseDateTime("01/01/2011");
		LocalDate currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		BigDecimal value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(3206), value.setScale(0));

		// Test final model value
		dt = formatter.parseDateTime("1/31/2020");
		currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(14060).setScale(0), value.setScale(0));

		// Test initial model value
		dt = formatter.parseDateTime("12/31/2009");
		currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(2000).setScale(0), value.setScale(0));
	}

}
