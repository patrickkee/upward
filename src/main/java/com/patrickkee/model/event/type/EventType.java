package com.patrickkee.model.event.type;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventGenerator;
import com.patrickkee.model.event.EventInstance;

public enum EventType {
	RECURRING_YIELD {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.MULTIPLY, dateLimit);
		}
	},
	RECURRING_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.ADD, dateLimit);
		}
	},
	ONE_TIME_DEPOSIT {
		@Override
		public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit) {
			return EventGenerator.getInstances(event, Operation.ADD, dateLimit);
		}
	};

	abstract public ArrayList<EventInstance> getInstances(Event event, LocalDate dateLimit);
}
