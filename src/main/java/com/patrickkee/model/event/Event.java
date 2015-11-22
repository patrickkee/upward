package com.patrickkee.model.event;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.patrickkee.jaxrs.util.LocalDateDeserializer;
import com.patrickkee.jaxrs.util.LocalDateSerializer;
import com.patrickkee.model.event.EventInstance;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

public class Event {

	private String name;
	private Period period;
	private EventType eventType;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal value;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("period")
	public Period getPeriod() {
		return period;
	}

	@JsonProperty("period")
	public void setPeriod(Period period) {
		this.period = period;
	}

	@JsonProperty("type")
	public EventType getEventType() {
		return eventType;
	}

	@JsonProperty("type")
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("startDate")
	public LocalDate getStartDate() {
		return startDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("startDate")
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("endDate")
	public LocalDate getEndDate() {
		return endDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("endDate")
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@JsonProperty("value")
	public BigDecimal getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@JsonIgnore
	public ArrayList<EventInstance> getInstances(Event event, LocalDate valueAsOfDate) {
		return this.getEventType().getInstances(event, valueAsOfDate);
	}
}
