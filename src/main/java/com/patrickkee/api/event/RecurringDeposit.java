package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;

/**
 * The RecurringDeposit class allows users to adjust the value of the model for
 * repeated deposits made into their account
 * 
 * @author Kee
 *
 */
public class RecurringDeposit extends Event {

	private RecurringDeposit(String name, Periods period, EventTypes eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringDeposit getNew(String name, Periods period, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		return new RecurringDeposit(name, period, EventTypes.RECURRING_DEPOSIT, startDate, endDate, value);
	}
}
