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
import com.patrickkee.model.event.YieldEventRecurring;
import com.patrickkee.model.event.type.Period;

public class YieldEventRecurringTest {

	@Test
	public void yieldEventRecurringSerializationTest() {
		// Set up the model
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

		DateTime dt = formatter.parseDateTime("01/01/2010");
		LocalDate startDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		dt = formatter.parseDateTime("12/31/2019");
		LocalDate endDate = new LocalDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());

		YieldEventRecurring obj = YieldEventRecurring.getNew("name", BigDecimal.valueOf(1.0041), Period.MONTHLY, startDate, endDate);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			String genJson = mapper.writeValueAsString(obj);
			YieldEventRecurring deserializedObj = mapper.readValue(genJson, YieldEventRecurring.class);
			assertEquals("name", deserializedObj.getName());
			assertEquals(startDate, deserializedObj.getStartDate());
			assertEquals(endDate, deserializedObj.getEndDate());
			assertEquals(BigDecimal.valueOf(1.0041), deserializedObj.getPercent());
			assertEquals(Period.MONTHLY, deserializedObj.getPeriod());
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0, 1);
		}
	}
}
