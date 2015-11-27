package com.patrickkee.model.event;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.EventInstance;
import com.patrickkee.model.event.type.Operation;
import com.patrickkee.model.event.type.OperationType;
import com.patrickkee.model.event.type.Period;

public class EventGenerator {

	public static ArrayList<EventInstance> getInstances(Event event, Operation operation, LocalDate dateLimit) {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>();

		if (event.getPeriod().equals((Period.POINT_IN_TIME))) {
			tmpInstances.add(new EventInstance(event.getEndDate(), event.getValue(), operation));
		} else {
			tmpInstances = generateRecurringInstances(event, operation, dateLimit);
		}

		return tmpInstances;
	}

	private static ArrayList<EventInstance> generateRecurringInstances(Event event, Operation operation,
			LocalDate dateLimit) {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>();
		LocalDate currDate;

		FrequencyIterator itr = FrequencyIterator.getNew(event.getPeriod(), event.getStartDate(), event.getEndDate());
		while (itr.hasNext() && (itr.getCursor().isBefore(dateLimit) || itr.getCursor().isEqual(dateLimit))) {
			// TODO: IF/Else If is Hacky, clean up later
			// Multipliers should be applied at the end of the period, addends
			// should be applied at the beginning
			currDate = itr.getCursor();
			if (operation.getType().equals(OperationType.MULTIPLICAND)) {
				currDate = event.getPeriod().getEnd(itr.getCursor());
			} else if (operation.getType().equals(OperationType.ADDEND)) {
				currDate = event.getPeriod().getBeginning(itr.getCursor());
			} else {
				throw new IllegalArgumentException();
			}

			tmpInstances.add(new EventInstance(currDate, event.getValue(), operation));
			itr.next();
		}
		return tmpInstances;
	}
}
