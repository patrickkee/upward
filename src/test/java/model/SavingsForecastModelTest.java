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

import com.patrickkee.model.event.DepositEventRecurring;
import com.patrickkee.model.event.YieldEventRecurring;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.SavingsForecastModel;

public class SavingsForecastModelTest {

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
		
		model.addEvent(YieldEventRecurring.getNew(BigDecimal.valueOf(1.00416), Period.MONTHLY, startDate, endDate));

		assertEquals(BigDecimal.valueOf(-6708.60).setScale(2, BigDecimal.ROUND_HALF_UP),
				model.valueVsTarget().setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void getValuesTest() {
		TreeMap<LocalDate, BigDecimal> manuallyComputedModel = new TreeMap<>();
		manuallyComputedModel.put(new LocalDate(2010, 1, 31),
				BigDecimal.valueOf(2108.74).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 2, 28),
				BigDecimal.valueOf(2217.92).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 3, 31),
				BigDecimal.valueOf(2327.57).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 4, 30),
				BigDecimal.valueOf(2437.67).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 5, 31),
				BigDecimal.valueOf(2548.22).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 6, 30),
				BigDecimal.valueOf(2659.24).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 7, 31),
				BigDecimal.valueOf(2770.72).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 8, 31),
				BigDecimal.valueOf(2882.66).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 9, 30),
				BigDecimal.valueOf(2995.07).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 10, 31),
				BigDecimal.valueOf(3107.94).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 11, 30),
				BigDecimal.valueOf(3221.29).setScale(2, BigDecimal.ROUND_HALF_UP));

		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("11/30/2010");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.newModel().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		model.addEvent(YieldEventRecurring.getNew(BigDecimal.valueOf(1.00416), Period.MONTHLY, startDate.plusDays(1), endDate.plusDays(1)));
		model.addEvent(DepositEventRecurring.getNew(BigDecimal.valueOf(100), Period.MONTHLY, startDate, endDate));
		
		TreeMap<LocalDate, BigDecimal> modelValues = model.getValues(Period.MONTHLY);
		assertTrue(modelValues.equals(manuallyComputedModel));
	}
	
}
