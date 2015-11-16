package com.patrickkee.model.event;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.type.EventInstance;

public class MultiplierEventInstance implements EventInstance {

	private LocalDate _date;
	private BigDecimal _percent;

	protected MultiplierEventInstance() { }

	@Override
	public MultiplierEventInstance getNew(LocalDate date, BigDecimal percent) {
		MultiplierEventInstance evnt = new MultiplierEventInstance();
		evnt.setDate(date);
		evnt.setPercent(percent);

		return evnt;
	}

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
	public BigDecimal apply(BigDecimal value) {
		return value.multiply(_percent);
	}

	@Override
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
