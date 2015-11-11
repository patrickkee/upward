package com.patrickkee.model.type;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

/**
 * An instance of any event that may occur in a {@code Model} which occurs on a particular date. 
 * 
 * @author Kee
 */
public interface EventInstance extends Comparable<EventInstance> {

	/**
	 * Applies the event to the value and returns the resultant amount
	 * 
	 * @param value
	 *            the initial value
	 * @return the resultant value
	 */
	public BigDecimal apply(BigDecimal value);

	public LocalDate getDate();

	public int compareTo(EventInstance arg0);
}
