package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.SavingsForecastModel;

public class SavingsForecastModelTest {

	@Test
	public void valueVsTargetTest() {
		final String EVENT_NAME = "valueVsTargetTest";
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.getNew().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		
		//Create the event and add it to the model
		Event event = new Event();
		event.setName(EVENT_NAME);
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_YIELD);
		event.setValue(BigDecimal.valueOf(1.00416));
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		model.addEvent(event);

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

		SavingsForecastModel model = SavingsForecastModel.getNew().name("test").startDate(startDate).endDate(endDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		
		//Create the yield event and add it to the model
		Event event = new Event();
		event.setName("Monthly Yield");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_YIELD);
		event.setValue(BigDecimal.valueOf(1.00416));
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		model.addEvent(event);
		
		//Create the yield event and add it to the model
		event = new Event();
		event.setName("Monthly Deposit");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setValue(BigDecimal.valueOf(100));
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		model.addEvent(event);
		
		TreeMap<LocalDate, BigDecimal> modelValues = model.getValues(Period.MONTHLY);
		assertTrue(modelValues.equals(manuallyComputedModel));
	}

	@Test
	public void testSavingsForecastModelSerialization() {
				// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.getNew().name("test").startDate(startDate).endDate(endDate).description("foobar")
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			String genJson = mapper.writeValueAsString(model);
			SavingsForecastModel deserializedModel = mapper.readValue(genJson, SavingsForecastModel.class);
			assertEquals("test", deserializedModel.getName());
			assertEquals(startDate, deserializedModel.getStartDate());
			assertEquals(endDate, deserializedModel.getEndDate());
			assertEquals("foobar", deserializedModel.getDescription());
			assertEquals(BigDecimal.valueOf(2000.0), deserializedModel.getInitialValue());
			assertEquals(BigDecimal.valueOf(10000.0), deserializedModel.getTargetValue());
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0,1);
		} 
	}
	
}
