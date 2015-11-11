package com.patrickkee.model.type;

import java.util.ArrayList;

public interface Event {

	/**
	 * Returns a collection of all event instances
	 * @return ArrayList<Event> 
	 */
	public ArrayList<EventInstance> getInstances();
	 
}
