package com.patrickkee.model.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.type.Operation;

public class EventInstance implements Comparable<EventInstance> {

	private Operation operation;
	private LocalDate _date;
	private BigDecimal _value;

	public EventInstance(LocalDate date, BigDecimal percent, Operation operation) { 
		setDate(date);
		setValue(percent);
		setOperation(operation);
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	private void setDate(LocalDate _date) {
		this._date = _date;
	}

	public LocalDate getDate() {
		return _date;
	}

	public BigDecimal getValue() {
		return _value;
	}

	private void setValue(BigDecimal value) {
		this._value = value;
	}

	public BigDecimal apply(BigDecimal value) {
		if (operation.equals(Operation.ADD)) {
			return value.add(_value);
		} else 
		if (operation.equals(Operation.MULTIPLY)) {
			return value.multiply(_value);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public int compareTo(EventInstance arg0) {
		if (_date.isBefore(arg0.getDate())) {
			return -1;
		} else if (_date.isAfter(arg0.getDate())) {
			return 1;
		} else {
			return 0;
		}
	}

}
