package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

/**
 * The RecurringInflation class allows users to adjust the value of the model
 * for inflation
 * 
 * @author Kee
 *
 */
public class RecurringInflation extends Event {

	private RecurringInflation(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringInflation getNew(String name, Period period, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		return new RecurringInflation(name, period, EventType.RECURRING_INFLATION, startDate, endDate, value);
	}
}
