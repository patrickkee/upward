package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.patrickkee.model.impl.RecurringYieldEvent;
import com.patrickkee.model.impl.SavingsForecastModel;
import com.patrickkee.model.type.Frequency;

public class SavingsForecastModelTest {

	@Test
	public void getValueTest() {
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.newModel().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		model.addEvent(RecurringYieldEvent.getNew(BigDecimal.valueOf(1.00416), Frequency.MONTHLY, startDate, endDate));

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

	@Test
	public void valueVsTargetTest() {
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.newModel().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		model.addEvent(RecurringYieldEvent.getNew(BigDecimal.valueOf(1.00416), Frequency.MONTHLY, startDate, endDate));

		assertEquals(BigDecimal.valueOf(-6708.60).setScale(2, BigDecimal.ROUND_HALF_UP),
				model.valueVsTarget().setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void getValuesTest() {
		TreeMap<LocalDate, BigDecimal> manuallyComputedModel = new TreeMap<>();
		manuallyComputedModel.put(new LocalDate(2010, 1, 1),
				BigDecimal.valueOf(2008.32).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 2, 1),
				BigDecimal.valueOf(2016.67).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 3, 1),
				BigDecimal.valueOf(2025.06).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 4, 1),
				BigDecimal.valueOf(2033.49).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 5, 1),
				BigDecimal.valueOf(2041.95).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 6, 1),
				BigDecimal.valueOf(2050.44).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 7, 1),
				BigDecimal.valueOf(2058.97).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 8, 1),
				BigDecimal.valueOf(2067.54).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 9, 1),
				BigDecimal.valueOf(2076.14).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 10, 1),
				BigDecimal.valueOf(2084.77).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 11, 1),
				BigDecimal.valueOf(2093.45).setScale(2, BigDecimal.ROUND_HALF_UP));

		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("11/30/2010");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.newModel().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		model.addEvent(RecurringYieldEvent.getNew(BigDecimal.valueOf(1.00416), Frequency.MONTHLY, startDate, endDate));

		TreeMap<LocalDate, BigDecimal> modelValues = model.getValues();
		assertTrue(modelValues.equals(manuallyComputedModel));
	}
}
