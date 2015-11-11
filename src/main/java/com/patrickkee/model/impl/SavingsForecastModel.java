package com.patrickkee.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import org.joda.time.LocalDate;

import com.patrickkee.model.type.EventInstance;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.patrickkee.model.type.Event;
import com.patrickkee.model.type.Model;
import com.patrickkee.jaxrs.util.LocalDateSerializer;

public class SavingsForecastModel implements Model {

	private int _modelId = 0;
	private String _name;
	private String _description;
	private BigDecimal _initialValue;
	private BigDecimal _targetValue;
	private LocalDate _startDate;
	private LocalDate _endDate;
	private List<Event> events = new ArrayList<Event>();

	private SavingsForecastModel() {
	}

	// Getters
	@JsonFormat
	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public BigDecimal getInitialValue() {
		return _initialValue;
	}

	public BigDecimal getTargetValue() {
		return _targetValue;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	public LocalDate getStartDate() {
		return _startDate;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	public LocalDate getEndDate() {
		return _endDate;
	}

	public void addEvent(Event event) {
		events.add(event);
	}
	
	@Override
	public BigDecimal getValue(LocalDate valueAsOfDate) {
		//Apply all the event instances to the account value
		BigDecimal currentValue = _initialValue;
		ListIterator<EventInstance> eventInstIterator = getSortedEventInstances().listIterator();
		
		EventInstance e = null;
		while (eventInstIterator.hasNext() && ((e = eventInstIterator.next()).getDate().isBefore(valueAsOfDate))) {
			currentValue = e.apply(currentValue);
		}
		
		return currentValue;
	}

	@Override
	public BigDecimal valueVsTarget() {
		return getValue(_endDate).subtract(_targetValue);
	}
	
	@Override
	public TreeMap<LocalDate, BigDecimal> getValues() {
		TreeMap<LocalDate, BigDecimal> modelValues = new TreeMap<>();
		
		//Apply all the event instances to the account value
		BigDecimal currentValue = _initialValue;
		ListIterator<EventInstance> eventInstIterator = getSortedEventInstances().listIterator();
		
		EventInstance e = null;
		while (eventInstIterator.hasNext()) {
			e = eventInstIterator.next();
			currentValue = e.apply(currentValue);
			modelValues.put(e.getDate(), currentValue.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		return modelValues;
	}
	
	private List<EventInstance> getSortedEventInstances() {
		List<EventInstance> eventInstances = new ArrayList<>();
		
		//Get all the event instances from the event types in the model
		for (ListIterator<Event> iter = events.listIterator(); iter.hasNext(); ) {
		    Event e = iter.next();
		    eventInstances.addAll(e.getInstances());		    
		}
		
		//Sort the event instances chronologically
		Collections.sort(eventInstances);
		
		return eventInstances;
	}
	
	@Override
	public int getModelId() {
		if (_modelId == 0) {
			_modelId = hashCode();
			return _modelId;
		} else {
			return _modelId;
		}

	}

	// Methods for builder pattern
	public static SavingsForecastModel newModel() {
		return new SavingsForecastModel();
	}

	public SavingsForecastModel name(String name) {
		this._name = name;
		return this;
	}

	public SavingsForecastModel description(String description) {
		this._description = description;
		return this;
	}

	public SavingsForecastModel initialValue(BigDecimal initialValue) {
		this._initialValue = initialValue;
		return this;
	}

	public SavingsForecastModel targetValue(BigDecimal targetValue) {
		this._targetValue = targetValue;
		return this;
	}

	public SavingsForecastModel startDate(LocalDate startDate) {
		this._startDate = startDate;
		return this;
	}

	public SavingsForecastModel endDate(LocalDate endDate) {
		this._endDate = endDate;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_description == null) ? 0 : _description.hashCode());
		result = prime * result + ((_endDate == null) ? 0 : _endDate.hashCode());
		result = prime * result + ((_initialValue == null) ? 0 : _initialValue.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_startDate == null) ? 0 : _startDate.hashCode());
		result = prime * result + ((_targetValue == null) ? 0 : _targetValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingsForecastModel other = (SavingsForecastModel) obj;
		if (_description == null) {
			if (other._description != null)
				return false;
		} else if (!_description.equals(other._description))
			return false;
		if (_endDate == null) {
			if (other._endDate != null)
				return false;
		} else if (!_endDate.equals(other._endDate))
			return false;
		if (_initialValue == null) {
			if (other._initialValue != null)
				return false;
		} else if (!_initialValue.equals(other._initialValue))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_startDate == null) {
			if (other._startDate != null)
				return false;
		} else if (!_startDate.equals(other._startDate))
			return false;
		if (_targetValue == null) {
			if (other._targetValue != null)
				return false;
		} else if (!_targetValue.equals(other._targetValue))
			return false;
		return true;
	}



}
