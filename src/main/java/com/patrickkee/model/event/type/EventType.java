package com.patrickkee.model.event.type;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.DepositEventRecurring;
import com.patrickkee.model.event.YieldEventRecurring;

public enum EventType {
	RECURRING_YIELD {
		@Override
		public Event getNew(String name, BigDecimal amount, Period period, LocalDate startDate, LocalDate endDate) {
			return YieldEventRecurring.getNew(name, amount, period, startDate, endDate);
		}
	},
	RECURRING_DEPOSIT
	{
		@Override
		public Event getNew(String name, BigDecimal amount, Period period, LocalDate startDate, LocalDate endDate) {
			return DepositEventRecurring.getNew(name, amount, period, startDate, endDate);
		}
	};

	abstract public Event getNew(String name, BigDecimal amount, Period period, LocalDate startDate, LocalDate endDate);
}
