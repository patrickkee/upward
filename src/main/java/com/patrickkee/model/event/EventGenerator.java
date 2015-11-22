package com.patrickkee.model.event;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.EventInstance;
import com.patrickkee.model.event.type.Operation;

public class EventGenerator {

	public static ArrayList<EventInstance> getInstances(Event event, Operation operation, LocalDate dateLimit) {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>();
		LocalDate currDate;

		FrequencyIterator itr = FrequencyIterator.getNew(event.getPeriod(), event.getStartDate(), event.getEndDate());
		while (itr.hasNext() && (itr.getCursor().isBefore(dateLimit) || itr.getCursor().isEqual(dateLimit))) {
			// TODO: IF/Else If is Hacky, clean up later
			// Multipliers should be applied at the end of the period, addends
			// should be applied at the beginning
			currDate = itr.getCursor(); 
			if (operation.equals(Operation.MULTIPLY)) {
				currDate = event.getPeriod().getEnd(itr.getCursor());
			} else if (operation.equals(Operation.ADD)) {
				currDate = event.getPeriod().getBeginning(itr.getCursor());
			}

			tmpInstances.add(new EventInstance(currDate, event.getValue(), operation));
			itr.next();
		}
		return tmpInstances;
	}
}
