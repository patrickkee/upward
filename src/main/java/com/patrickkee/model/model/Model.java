package com.patrickkee.model.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;

import com.google.common.base.Optional;
import com.patrickkee.model.event.Event;

public interface Model {
	public int getModelId();

	/**
	 * Returns the value of a model on a given date
	 * 
	 * @param date
	 * @return
	 */
	public BigDecimal getValue(LocalDate date);

	/**
	 * Returns the difference between the account value minus the target to
	 * evaluate if the target will be achieved.
	 * 
	 * @return BigDecimal account ending value minus target
	 */
	public BigDecimal valueVsTarget();

	/**
	 * Returns a collection of account values, sorted in natural order, at the
	 * frequency specified
	 * 
	 * @param the
	 *            frequency of account values; eg. daily, monthly, etc
	 * @return map of LocalDates and account values
	 */
	public TreeMap<LocalDate, BigDecimal> getValues();

	/**
	 * Adds a new event or updates an existing event in the model's collection of events
	 * 
	 * @param model
	 */
	public void addOrUpdateEvent(Event event);

	public Optional<Event> getEvent(int eventId);
	
	public Map<Integer, Event> getEvents();

	void removeEvent(int eventId);
	
}
