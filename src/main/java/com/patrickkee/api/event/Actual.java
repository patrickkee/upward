package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

/**
 * The Actual class allows users to specify the actual value of the account that
 * the model represents for a point in time
 * 
 * @author Kee
 *
 */
public class Actual extends Event {

	private Actual(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static Actual getNew(LocalDate effectiveDate, BigDecimal value) {
		return new Actual("ACTUAL", Period.POINT_IN_TIME, EventType.ACTUAL, effectiveDate, effectiveDate, value);
	}
}
