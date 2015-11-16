package com.patrickkee.model.event.type;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.FrequencyIterator;

public abstract class MultiplierRecurring {

	public ArrayList<EventInstance> getInstances(BigDecimal multiplier, Period frequency, LocalDate startDate,
			LocalDate endDate, EventInstance eventInstance) {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>();
		LocalDate currDate;

		FrequencyIterator itr = FrequencyIterator.getNew(frequency, startDate, endDate);
		while (itr.hasNext()) {
			// Multipliers should be applied at the end of the period
			currDate = frequency.getEnd(itr.getCursor());
			tmpInstances.add(eventInstance.getNew(currDate, multiplier));
			itr.next();
		}

		return tmpInstances;
	}
}
