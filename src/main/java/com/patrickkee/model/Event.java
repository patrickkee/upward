package com.patrickkee.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Event {

	/**
	 * Returns a collection of all event instances
	 * @return ArrayList<Event> 
	 */
	public ArrayList<Event> getInstances();
	
	/**
	 * Applies the event to the value and returns the resultant amount
	 * @param value the initial value
	 * @return the resultant value
	 */
	public BigDecimal apply(BigDecimal value);
	 
}
