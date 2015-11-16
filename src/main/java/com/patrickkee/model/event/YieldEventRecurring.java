package com.patrickkee.model.event;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import com.patrickkee.model.event.type.Event;
import com.patrickkee.model.event.type.EventInstance;
import com.patrickkee.model.event.type.Period;
import com.patrickkee.model.event.type.MultiplierRecurring;

public class YieldEventRecurring extends MultiplierRecurring implements Event {

	private BigDecimal _percent;
	private Period _period;
	private LocalDate _startDate;
	private LocalDate _endDate;
	private final MultiplierEventInstance _yeildEventInstance = new MultiplierEventInstance();
	
	private YieldEventRecurring() {	}

	@Override
	public ArrayList<EventInstance> getInstances(LocalDate valueAsOfDate) {
		valueAsOfDate = (valueAsOfDate.isBefore(_endDate)) ? valueAsOfDate : _endDate;
		return super.getInstances(_percent, _period, _startDate, valueAsOfDate, _yeildEventInstance);
	}
	
	public static YieldEventRecurring getNew(BigDecimal percent, Period period, LocalDate startDate,
			LocalDate endDate) {
		YieldEventRecurring tmpEvnt = new YieldEventRecurring();
		tmpEvnt.setPercent(percent);
		tmpEvnt.setPeriod(period);
		tmpEvnt.setStartDate(startDate);
		tmpEvnt.setEndDate(endDate);

		return tmpEvnt;
	}

	// Setters...
	private void setPercent(BigDecimal percent) {
		this._percent = percent;
	}

	private void setPeriod(Period period) {
		this._period = period;
	}

	private void setStartDate(LocalDate startDate) {
		this._startDate = startDate;
	}

	private void setEndDate(LocalDate endDate) {
		this._endDate = endDate;
	}

}
