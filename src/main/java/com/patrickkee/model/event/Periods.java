package com.patrickkee.model.event;

import org.joda.time.LocalDate;

public enum Periods {
	POINT_IN_TIME 
	{
		@Override
		public LocalDate getBeginning(LocalDate date) {
			return date;
		}

		@Override
		public LocalDate getEnd(LocalDate date) {
			return date;
		}
	},
	MONTHLY
	{
		@Override
		public LocalDate getBeginning(LocalDate date) {
			return new LocalDate(date.getYear(), date.getMonthOfYear(), 1);
		}

		@Override
		public LocalDate getEnd(LocalDate date) {
			return new LocalDate(date.getYear(), date.getMonthOfYear(), date.dayOfMonth().withMaximumValue().getDayOfMonth());
		}
	};
	
	public abstract LocalDate getBeginning(LocalDate date);
	public abstract LocalDate getEnd(LocalDate date);
}
