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
public class OneTimeDeposit extends Event {

	private OneTimeDeposit(String name, Period period, EventType eventType, LocalDate startDate, LocalDate endDate,
			BigDecimal value) {
		super(name, period, eventType, startDate, endDate, value);
	}

	public static OneTimeDeposit getNew(String name, LocalDate effectiveDate, BigDecimal value) {
		return new OneTimeDeposit(name, Period.POINT_IN_TIME, EventType.ONE_TIME_DEPOSIT, effectiveDate, effectiveDate, value);
	}
}
