package com.patrickkee.model.event.type;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventGenerator;
import com.patrickkee.model.event.EventInstance;

public enum EventType {
	/*
	 * A a recurring yield which is applied as a multiple. Ex: 5% annual percentage rate
	 */
	RECURRING_YIELD {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.MULTIPLY, dateLimit);
		}
	},
	/*
	 * A repetitive deposit event, such as monthly savings contribution 
	 */
	RECURRING_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.ADD, dateLimit);
		}
	},
	/*
	 * A one-time (non-recurring) deposit event
	 */
	ONE_TIME_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.ADD, dateLimit);
		}
	},
	/*
	 * Sets the actual value of the model at a point in time. Used for resetting
	 * the model to a known state at a point of time.
	 */
	ACTUAL {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.REPLACE, dateLimit);
		}
	};

	/**
	 * Returns a collection of event instance command objects which are used to
	 * transform the model over time
	 * 
	 * @param event
	 *            - a particular event for which instances are to be generated
	 * @param dateLimit
	 *            - the max date to generate event instances up to
	 * @return an ArrayList of {@code EventInstance} command objects
	 */
	abstract public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit);
}
