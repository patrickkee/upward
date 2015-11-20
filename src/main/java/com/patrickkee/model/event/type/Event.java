package com.patrickkee.model.event.type;

import java.util.ArrayList;

import org.joda.time.LocalDate;

public interface Event {

	/**
	 * Returns a collection of all event instances
	 * 
	 * @return ArrayList<Event>
	 */
	public ArrayList<EventInstance> getInstances(LocalDate valueAsOfDate);

	/**
	 * Returns the name of the particular event
	 * @return
	 */
	public String getName();
}
