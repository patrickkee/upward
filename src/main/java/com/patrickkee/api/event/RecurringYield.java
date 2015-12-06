package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

/**
 * The RecurringInflation class allows users to adjust the value of the model
 * for yields returned from investment
 * 
 * @author Kee
 *
 */
public class RecurringYield extends Event {

	private RecurringYield(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringYield getNew(String name, Period period, LocalDate startDate, LocalDate endDate, BigDecimal value) {
		return new RecurringYield(name, period, EventType.RECURRING_YIELD, startDate, endDate,
				value);
	}
}
