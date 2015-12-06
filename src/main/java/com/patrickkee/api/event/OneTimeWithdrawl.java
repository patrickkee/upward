package com.patrickkee.api.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

/**
 * The OneTimeDeposit class allows users to add point in time deposit events to models
 * 
 * @author Kee
 *
 */
public class OneTimeWithdrawl extends Event {

	private OneTimeWithdrawl(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static OneTimeWithdrawl getNew(String name, LocalDate effectiveDate, BigDecimal value) {
		return new OneTimeWithdrawl(name, Period.POINT_IN_TIME, EventType.ONE_TIME_WITHDRAWL, effectiveDate, effectiveDate, value);
	}
}
