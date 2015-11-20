package com.patrickkee.model.event;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.type.Event;
import com.patrickkee.model.event.type.EventInstance;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.event.type.AddendRecurring;

public class DepositEventRecurring extends AddendRecurring implements Event {

	private String _name;
	private BigDecimal _amount;
	private Period _period;
	private LocalDate _startDate;
	private LocalDate _endDate;
	private final AddendEventInstance _depositEventInstance = new AddendEventInstance();

	private DepositEventRecurring() { }

	public static DepositEventRecurring getNew(String name, BigDecimal amount, Period period, LocalDate startDate,
			LocalDate endDate) {
		DepositEventRecurring tmpEvnt = new DepositEventRecurring();
		tmpEvnt.setName(name);
		tmpEvnt.setAmount(amount);
		tmpEvnt.setPeriod(period);
		tmpEvnt.setStartDate(startDate);
		tmpEvnt.setEndDate(endDate);

		return tmpEvnt;
	}

	@Override
	public ArrayList<EventInstance> getInstances(LocalDate valueAsOfDate) {
		valueAsOfDate = (valueAsOfDate.isBefore(_endDate)) ? valueAsOfDate : _endDate;
		return super.getInstances(_amount, _period, _startDate, valueAsOfDate, _depositEventInstance);
	}

	//Getters & Setters...
	@Override
	public String getName() {
		return this._name;
	}
	
	private void setName(String name) {
		this._name = name;		
	}
	
	public void setAmount(BigDecimal amount) {
		this._amount = amount;
	}

	public void setPeriod(Period frequency) {
		this._period = frequency;
	}

	public void setStartDate(LocalDate startDate) {
		this._startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this._endDate = endDate;
	}

}
