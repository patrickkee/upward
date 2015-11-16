package com.patrickkee.model.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.type.EventInstance;

public class AddendEventInstance implements EventInstance {
	
	private LocalDate _date;
	private BigDecimal _amount;
	
	protected AddendEventInstance() {}
	
	@Override
	public AddendEventInstance getNew(LocalDate date, BigDecimal amount) {
		AddendEventInstance evnt = new AddendEventInstance();
		evnt.setDate(date);
		evnt.setAmount(amount);
		
		return evnt;
	}
	
	@Override
	public BigDecimal apply(BigDecimal value) {
		return value.add(_amount);
	}

	@Override
	public LocalDate getDate() {
		return _date;
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

	public void setDate(LocalDate _date) {
		this._date = _date;
	}

	public void setAmount(BigDecimal amount) {
		this._amount = amount;
	}

}
