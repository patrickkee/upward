package com.patrickkee.model.event;

import java.util.ArrayList;

import org.joda.time.LocalDate;

public enum EventTypes {
	/*
	 * A a recurring yield which is applied as a multiple, expressed as a
	 * decimal. Ex: 0.005 for 5% annual percentage rate
	 */
	RECURRING_YIELD {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.ADD_PERCENT, dateLimit);
		}
	},
	/*
	 * A a recurring inflation which is applied as a multiple, expressed as a
	 * decimal. Ex: 0.0025 for 2.5% annual core inflation 
	 */
	RECURRING_INFLATION {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.SUBTRACT_PERCENT, dateLimit);
		}
	},
	/*
	 * A repetitive deposit event, such as monthly savings contribution
	 */
	RECURRING_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.ADD, dateLimit);
		}
	},
	/*
	 * A repetitive withdrawl event, such as a semi-annual withdrawl against a
	 * college savings account
	 */
	RECURRING_WITHDRAWL {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.SUBTRACT, dateLimit);
		}
	},
	/*
	 * A one-time (non-recurring) deposit event
	 */
	ONE_TIME_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.ADD, dateLimit);
		}
	},
	/*
	 * A one-time (non-recurring) deposit event
	 */
	ONE_TIME_WITHDRAWL {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.SUBTRACT, dateLimit);
		}
	},
	/*
	 * Sets the actual value of the model at a point in time. Used for resetting
	 * the model to a known state at a point of time.
	 */
	ACTUAL {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operations.REPLACE, dateLimit);
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
