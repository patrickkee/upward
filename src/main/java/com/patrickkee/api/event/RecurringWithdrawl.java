package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

/**
 * The RecurringWithdrawl class allows users to adjust the value of the model
 * for repeated withdrawls made against their account
 * 
 * @author Kee
 *
 */
public class RecurringWithdrawl extends Event {

	private RecurringWithdrawl(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringWithdrawl getNew(String name, Period period, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		return new RecurringWithdrawl(name, period, EventType.RECURRING_WITHDRAWL, startDate, endDate, value);
	}
}
