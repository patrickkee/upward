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
		manuallyComputedModel.put(new LocalDate(2010, 1, 1),BigDecimal.valueOf(2100.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 1, 31),BigDecimal.valueOf(2104.34).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 2, 1),BigDecimal.valueOf(2204.34).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 2, 28),BigDecimal.valueOf(2208.90).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 3, 1),BigDecimal.valueOf(2308.90).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 3, 12),BigDecimal.valueOf(3308.90).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 3, 31),BigDecimal.valueOf(3315.75).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 4, 1),BigDecimal.valueOf(3415.75).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 4, 5),BigDecimal.valueOf(3460.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 4, 30),BigDecimal.valueOf(3467.16).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 5, 1),BigDecimal.valueOf(3567.16).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 5, 31),BigDecimal.valueOf(3574.53).setScale(2, BigDecimal.ROUND_HALF_UP));

		manuallyComputedModel.put(new LocalDate(2010, 6, 1),BigDecimal.valueOf(3174.53).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 6, 30),BigDecimal.valueOf(3181.10).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 7, 1),BigDecimal.valueOf(2781.10).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 7, 31),BigDecimal.valueOf(2786.85).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 8, 1),BigDecimal.valueOf(2386.85).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 8, 31),BigDecimal.valueOf(2391.79).setScale(2, BigDecimal.ROUND_HALF_UP));
		
		manuallyComputedModel.put(new LocalDate(2010, 9, 1),BigDecimal.valueOf(1991.79).setScale(2, BigDecimal.ROUND_HALF_UP));
		manuallyComputedModel.put(new LocalDate(2010, 9, 30),BigDecimal.valueOf(1995.91).setScale(2, BigDecimal.ROUND_HALF_UP));
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate modelStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("9/30/2010");
		LocalDate modelEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		SavingsForecastModel model = SavingsForecastModel.getNew().name("test").startDate(modelStartDate).endDate(modelEndDate)
				.initialValue(BigDecimal.valueOf(2000.0)).targetValue(BigDecimal.valueOf(10000.0));
		
		//Create the yield event and add it to the model
		Event event = new Event();
		event.setName("Monthly Yield");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_YIELD);
		event.setValue(BigDecimal.valueOf(0.00416));
		event.setStartDate(modelStartDate);
		event.setEndDate(modelEndDate);
		model.addEvent(event);
		
		//Create the yield event and add it to the model
		event = new Event();
		event.setName("Monthly Deposit");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_DEPOSIT);
		event.setValue(BigDecimal.valueOf(100));
		event.setStartDate(modelStartDate);
		event.setEndDate(modelEndDate);
		model.addEvent(event);
		
		//Create the one time deposit event and add it to the model
		dt = formatter.parseDateTime("03/12/2010");
		LocalDate pointInTime = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		event = new Event();
		event.setName("One-Time Deposit");
		event.setPeriod(Period.POINT_IN_TIME);
		event.setEventType(EventType.ONE_TIME_DEPOSIT);
		event.setValue(BigDecimal.valueOf(1000));
		event.setStartDate(pointInTime);
		event.setEndDate(pointInTime);
		model.addEvent(event);
		
		//Create an "actual" event and add it to the model
		dt = formatter.parseDateTime("04/05/2010");
		pointInTime = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		event = new Event();
		event.setName("Actual Account Value");
		event.setPeriod(Period.POINT_IN_TIME);
		event.setEventType(EventType.ACTUAL);
		event.setValue(BigDecimal.valueOf(3460.00));
		event.setStartDate(pointInTime);
		event.setEndDate(pointInTime);
		model.addEvent(event);
		
		//Add the recurring withdrawl event
		dt = formatter.parseDateTime("06/01/2010");
		LocalDate withdrawlStartDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("09/30/2010");
		LocalDate withdrawlEndDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
		
		event = new Event();
		event.setName("Recurring Withdrawl");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_WITHDRAWL);
		event.setValue(BigDecimal.valueOf(500)); 
		event.setStartDate(withdrawlStartDate);
		event.setEndDate(withdrawlEndDate);
		model.addEvent(event);
		
		//Create a recurring inflation event and add it to the model
		event = new Event();
		event.setName("Recurring Inflation");
		event.setPeriod(Period.MONTHLY);
		event.setEventType(EventType.RECURRING_INFLATION);
		event.setValue(BigDecimal.valueOf(0.002083)); //2.5% APR inflation, monthly
		event.setStartDate(modelStartDate);
		event.setEndDate(withdrawlEndDate);
		model.addEvent(event);
		
		TreeMap<LocalDate, BigDecimal> modelValues = model.getValues();
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
