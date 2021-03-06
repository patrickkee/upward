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
import com.google.common.base.Preconditions;
import com.patrickkee.api.event.Actual;
import com.patrickkee.application.util.LocalDateDeserializer;
import com.patrickkee.application.util.LocalDateSerializer;

public class Event {

	private int eventId;
	private String name;
	private Periods period;
	private EventTypes eventType;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal value;

	private static final String VALUE_ERROR_MSG = "Event value must be greater than zero";
	
	@SuppressWarnings("unused") //Intentionally hidden for immutability
	private Event() {} 
	
	@JsonCreator
	protected Event(@JsonProperty("name") String name,
				 	@JsonProperty("period") Periods period,
				 	@JsonProperty("type") EventTypes eventType,
				 	@JsonDeserialize(using = LocalDateDeserializer.class) @JsonProperty("startDate") LocalDate startDate,
				 	@JsonDeserialize(using = LocalDateDeserializer.class) @JsonProperty("endDate") LocalDate endDate,
				 	@JsonProperty("value") BigDecimal value) {
		
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(eventType);
		Preconditions.checkNotNull(startDate);
		Preconditions.checkNotNull(endDate);
		Preconditions.checkState(value.compareTo(BigDecimal.valueOf(0)) > 0 , VALUE_ERROR_MSG);
		
		//Enforce the default values for one time events
		if (eventType.equals(EventTypes.ACTUAL) ||
			eventType.equals(EventTypes.ONE_TIME_DEPOSIT) ||
			eventType.equals(EventTypes.ONE_TIME_WITHDRAWL)) {
			
			period = Periods.POINT_IN_TIME;
        	endDate = startDate;
		}
						
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
	public Periods getPeriod() {
		return period;
	}

	@JsonProperty("type")
	public EventTypes getEventType() {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
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
