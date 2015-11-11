package com.patrickkee.model.impl;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.type.EventInstance;

public class YieldEventInstance implements EventInstance {

	private LocalDate _date;
	private BigDecimal _percent;
	
	private YieldEventInstance() {}
	
	@Override
	public BigDecimal apply(BigDecimal value) {
		return value.multiply(_percent);
	}

	public static YieldEventInstance getNew(LocalDate date, BigDecimal percent) {
		YieldEventInstance evnt = new YieldEventInstance();
		evnt.setDate(date);
		evnt.setPercent(percent);
		
		return evnt;
	}

	//Getters and setters
	private void setDate(LocalDate _date) {
		this._date = _date;
	}

	public LocalDate getDate() {
		return _date;
	}
	
	public BigDecimal getPercent() {
		return _percent;
	}

	private void setPercent(BigDecimal _percent) {
		this._percent = _percent;
	}

	
	@Override
	public int compareTo(EventInstance arg0) {
		if (_date.isBefore(arg0.getDate())) {
			return -1;
		}
		else if (_date.isAfter(arg0.getDate())) {
			return 1;
		} 
		else {
			return 0;
		}
	}	
}
