package com.patrickkee.model.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.patrickkee.jaxrs.util.LocalDateDeserializer;
import com.patrickkee.jaxrs.util.LocalDateSerializer;
import com.patrickkee.model.event.Event;
import com.patrickkee.model.event.EventInstance;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.model.type.Model;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsForecastModel implements Model {

	private int _modelId = 0;
	private String _name;
	private String _description;
	private BigDecimal _initialValue;
	private BigDecimal _targetValue;
	private LocalDate _startDate;
	private LocalDate _endDate;
	private HashMap<Integer, Event> _events = new HashMap<>();

	private SavingsForecastModel() { }

	public static SavingsForecastModel getNew() {
		return new SavingsForecastModel();
	}
	
	// Getters & Setters
	@Override
	@JsonProperty("modelId")
	public int getModelId() {
		if (_modelId == 0) {
			_modelId = hashCode();
			return _modelId;
		} else {
			return _modelId;
		}
	}
	
	@JsonProperty("name")
	public String getName() {
		return _name;
	}
	@JsonProperty("name")
	public void setName(String name) {
		this._name = name;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return _description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this._description = description;
	}
	
	@JsonProperty("initialValue")
	public BigDecimal getInitialValue() {
		return _initialValue;
	}
	@JsonProperty("initialValue")
	public void setInitialValue(BigDecimal initialValue) {
		this._initialValue = initialValue;
	}
	
	@JsonProperty("targetValue")
	public BigDecimal getTargetValue() {
		return _targetValue;
	}
	@JsonProperty("targetValue")
	public void setTargetValue(BigDecimal targetValue) {
		this._targetValue = targetValue;
	}
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("startDate")
	public LocalDate getStartDate() {
		return _startDate;
	}
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("startDate")
	public void setStartDate(LocalDate startDate) {
		this._startDate = startDate;
	}
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("endDate")
	public LocalDate getEndDate() {
		return _endDate;
	} 
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("endDate")
	public void setEndDate(LocalDate endDate) {
		this._endDate = endDate;
	}
	
	@Override
	public void addEvent(Event event) {
		_events.putIfAbsent(event.getEventId(), event);
	}

	@JsonIgnore
	@Override
	public BigDecimal getValue(LocalDate valueAsOfDate) {
		// Apply all the event instances to the account value
		BigDecimal currentValue = _initialValue;
		List<EventInstance> instances = getSortedEventInstances(valueAsOfDate);

		//TODO: Convert to Java8 Stream iteration style
		for (EventInstance e : instances) {
			currentValue = e.apply(currentValue);
		}

		return currentValue;
	}

	@JsonIgnore
	@Override
	public BigDecimal valueVsTarget() {
		return getValue(_endDate).subtract(_targetValue);
	}

	@JsonIgnore
	@Override
	public TreeMap<LocalDate, BigDecimal> getValues() {
		TreeMap<LocalDate, BigDecimal> modelValues = new TreeMap<>();

		// Apply all the event instances to the account value
		BigDecimal currentValue = _initialValue;
		List<EventInstance> instances = getSortedEventInstances(_endDate);
		
		for (EventInstance e : instances) {
			currentValue = e.apply(currentValue);
			modelValues.put(e.getDate(), currentValue.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		return modelValues;
	}

	@JsonIgnore
	private List<EventInstance> getSortedEventInstances(LocalDate valueAsOfDate) {
		List<EventInstance> eventInstances = new ArrayList<>();

		// Get all the event instances from the event types in the model
		for (Event event : _events.values()) {
			eventInstances.addAll(event.getInstances(event, valueAsOfDate));
		}

		// Sort the event instances chronologically
		Collections.sort(eventInstances);

		return eventInstances;
	}

	// Methods for builder pattern
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
		return result * -1;
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

	@Override
	@JsonIgnore
	public Event getEvent(String eventName) {
		return _events.get(eventName);
	}

	@Override 
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String json = "";
		
		try {
			json = mapper.writeValueAsString(this);
		} catch (IOException e) {
			//TODO: Something intelligent rather than ridiculously printing the stack trace
			e.printStackTrace();
		} 
		
		return json;
	}
	
}
