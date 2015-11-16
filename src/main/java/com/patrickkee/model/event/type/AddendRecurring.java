package com.patrickkee.model.event.type;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.FrequencyIterator;

public abstract class AddendRecurring {

	public ArrayList<EventInstance> getInstances(BigDecimal addend, Period frequency, LocalDate startDate,
			LocalDate endDate, EventInstance eventInstance) {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>();
		LocalDate currDate;

		FrequencyIterator itr = FrequencyIterator.getNew(frequency, startDate, endDate);
		while (itr.hasNext()) {
			// Addends should be applied at the beginning of the period
			currDate = frequency.getBeginning(itr.getCursor());
			tmpInstances.add(eventInstance.getNew(currDate, addend));
			itr.next();
		}

		return tmpInstances;
	}
}
