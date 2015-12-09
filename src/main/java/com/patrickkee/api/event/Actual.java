package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;

/**
 * The Actual class allows users to specify the actual value of the account that
 * the model represents for a point in time
 * 
 * @author Kee
 *
 */
public class Actual extends Event {

	private Actual(String name, Periods period, EventTypes eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static Actual getNew(LocalDate effectiveDate, BigDecimal value) {
		return new Actual("ACTUAL", Periods.POINT_IN_TIME, EventTypes.ACTUAL, effectiveDate, effectiveDate, value);
	}
}
