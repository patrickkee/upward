package com.patrickkee.model.event;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.patrickkee.jaxrs.util.LocalDateDeserializer;
import com.patrickkee.jaxrs.util.LocalDateSerializer;
import com.patrickkee.model.event.type.EventType;
import com.patrickkee.model.event.type.Period;

public class Event {

	private int eventId;
	private String name;
	private Period period;
	private EventType eventType;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal value;

	@SuppressWarnings("unused") //Intentionally hiding the construction for immutability
	private Event() {} 
	
	@JsonCreator
	protected Event(@JsonProperty("name") String name,
				 @JsonProperty("period") Period period,
				 @JsonProperty("type") EventType eventType,
				 @JsonDeserialize(using = LocalDateDeserializer.class) @JsonProperty("startDate") LocalDate startDate,
				 @JsonDeserialize(using = LocalDateDeserializer.class) @JsonProperty("endDate") LocalDate endDate,
				 @JsonProperty("value") BigDecimal value) {
		
		this.name = name;
		this.period = period;
		this.eventType = eventType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.value = value; 		
	}
	
	@JsonProperty("eventId")
	public int getEventId() {
		if (eventId == 0) {
			eventId = hashCode();
			return eventId;
		} else {
			return eventId;
		}
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("period")
	public Period getPeriod() {
		return period;
	}

	@JsonProperty("type")
	public EventType getEventType() {
		return eventType;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("startDate")
	public LocalDate getStartDate() {
		return startDate;
	}
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("endDate")
	public LocalDate getEndDate() {
		return endDate;
	}

	@JsonProperty("value")
	public BigDecimal getValue() {
		return value;
	}

	@JsonIgnore
	public ArrayList<EventInstance> getInstances(Event event, LocalDate valueAsOfDate) {
		return this.getEventType().getInstances(event, valueAsOfDate);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result * -1;
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
