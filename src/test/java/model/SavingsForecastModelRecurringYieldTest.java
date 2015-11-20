package model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.model.event.YieldEventRecurring;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.SavingsForecastModel;

public class SavingsForecastModelRecurringYieldTest {

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
		model.addEvent(YieldEventRecurring.getNew("VALUE_TEST_EVENT", BigDecimal.valueOf(1.00416), Period.MONTHLY, startDate, endDate));

		// Test intermediary model value
		dt = formatter.parseDateTime("01/01/2011");
		LocalDate currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		BigDecimal value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(2102.16).setScale(2, BigDecimal.ROUND_HALF_UP),
				value.setScale(2, BigDecimal.ROUND_HALF_UP));

		// Test final model value
		dt = formatter.parseDateTime("1/31/2020");
		currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(3291.40).setScale(2, BigDecimal.ROUND_HALF_UP),
				value.setScale(2, BigDecimal.ROUND_HALF_UP));

		// Test initial model value
		dt = formatter.parseDateTime("12/31/2009");
		currentDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		value = model.getValue(currentDate);
		assertEquals(BigDecimal.valueOf(2000.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				value.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

}
