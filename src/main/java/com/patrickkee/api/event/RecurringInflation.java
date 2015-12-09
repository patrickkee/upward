package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;

/**
 * The RecurringInflation class allows users to adjust the value of the model
 * for inflation
 * 
 * @author Kee
 *
 */
public class RecurringInflation extends Event {

	private RecurringInflation(String name, Periods period, EventTypes eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringInflation getNew(String name, Periods period, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		return new RecurringInflation(name, period, EventTypes.RECURRING_INFLATION, startDate, endDate, value);
	}
}
