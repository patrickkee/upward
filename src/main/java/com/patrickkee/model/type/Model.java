package com.patrickkee.model.type;

import java.math.BigDecimal;
import java.util.TreeMap;

import org.joda.time.LocalDate;

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
	
	public TreeMap<LocalDate, BigDecimal> getValues();
}
