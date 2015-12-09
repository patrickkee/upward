package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.patrickkee.api.event.RecurringDeposit;
import com.patrickkee.api.event.RecurringYield;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;

public class EventTest {

	@Test
	public void yieldEventRecurringSerializationTest() {
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		// Create the yield event and add it to the model
		RecurringYield event = RecurringYield.getNew("name", Periods.MONTHLY, startDate, endDate,BigDecimal.valueOf(0.00416));

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			String genJson = mapper.writeValueAsString(event);
			Event deserializedObj = mapper.readValue(genJson, Event.class);
			assertEquals("name", deserializedObj.getName());
			assertEquals(startDate, deserializedObj.getStartDate());
			assertEquals(endDate, deserializedObj.getEndDate());
			assertEquals(BigDecimal.valueOf(0.00416), deserializedObj.getValue());
			assertEquals(Periods.MONTHLY, deserializedObj.getPeriod());
			assertEquals(EventTypes.RECURRING_YIELD, deserializedObj.getEventType());
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}

	@Test
	public void eventSerializationTest() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		RecurringDeposit event = RecurringDeposit.getNew("anEvent", Periods.MONTHLY, startDate, endDate,
				BigDecimal.valueOf(101.24));

		int eventId = event.getEventId();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			String genJson = mapper.writeValueAsString(event);
			Event obj = mapper.readValue(genJson, Event.class);
			assertEquals(eventId, obj.getEventId());
			assertEquals("anEvent", obj.getName());
			assertEquals(startDate, obj.getStartDate());
			assertEquals(endDate, obj.getEndDate());
			assertEquals(Periods.MONTHLY, obj.getPeriod());
			assertEquals(EventTypes.RECURRING_DEPOSIT, obj.getEventType());
			assertEquals(BigDecimal.valueOf(101.24), obj.getValue());
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}
}
