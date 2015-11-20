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
import com.patrickkee.model.event.type.Event;
import com.patrickkee.model.event.type.EventInstance;
import com.patrickkee.model.event.type.MultiplierRecurring;
import com.patrickkee.model.event.type.Period;

public class YieldEventRecurring extends MultiplierRecurring implements Event {

	private String _name;
	private BigDecimal _percent;
	private Period _period;
	private LocalDate _startDate;
	private LocalDate _endDate;
	private final MultiplierEventInstance _yeildEventInstance = new MultiplierEventInstance();

	private YieldEventRecurring() {
	}

	public static YieldEventRecurring getNew(String name, BigDecimal percent, Period period, LocalDate startDate,
			LocalDate endDate) {
		YieldEventRecurring tmpEvnt = new YieldEventRecurring();
		tmpEvnt.setName(name);
		tmpEvnt.setPercent(percent);
		tmpEvnt.setPeriod(period);
		tmpEvnt.setStartDate(startDate);
		tmpEvnt.setEndDate(endDate);

		return tmpEvnt;
	}

	@JsonIgnore
	@Override
	public ArrayList<EventInstance> getInstances(LocalDate valueAsOfDate) {
		valueAsOfDate = (valueAsOfDate.isBefore(_endDate)) ? valueAsOfDate : _endDate;
		return super.getInstances(_percent, _period, _startDate, valueAsOfDate, _yeildEventInstance);
	}

	// Getters & Setters...
	@Override
	@JsonProperty("name")
	public String getName() {
		return this._name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this._name = name;
	}

	@JsonProperty("percent")
	public BigDecimal getPercent() {
		return this._percent;
	}

	@JsonProperty("percent")
	public void setPercent(BigDecimal percent) {
		this._percent = percent;
	}

	@JsonProperty("period")
	public void setPeriod(Period period) {
		this._period = period;
	}
	@JsonProperty("period")
	public Period getPeriod() {
		return this._period;
	}
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("startDate")
	public LocalDate getStartDate() {
		return this._startDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("startDate")
	public void setStartDate(LocalDate startDate) {
		this._startDate = startDate;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonProperty("endDate")
	public LocalDate getEndDate() {
		return this._endDate;
	}

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("endDate")
	public void setEndDate(LocalDate endDate) {
		this._endDate = endDate;
	}

}
