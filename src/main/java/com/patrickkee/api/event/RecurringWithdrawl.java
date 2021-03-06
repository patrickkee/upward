package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventTypes;
import com.patrickkee.model.event.Periods;

/**
 * The RecurringWithdrawl class allows users to adjust the value of the model
 * for repeated withdrawls made against their account
 * 
 * @author Kee
 *
 */
public class RecurringWithdrawl extends Event {

	private RecurringWithdrawl(String name, Periods period, EventTypes eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static RecurringWithdrawl getNew(String name, Periods period, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		return new RecurringWithdrawl(name, period, EventTypes.RECURRING_WITHDRAWL, startDate, endDate, value);
	}
}
