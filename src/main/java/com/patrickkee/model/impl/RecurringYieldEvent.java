package com.patrickkee.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.joda.time.LocalDate;

import com.patrickkee.model.type.EventInstance;
import com.patrickkee.model.type.Frequency;
import com.patrickkee.model.type.Event;

public class RecurringYieldEvent implements Event {

	private BigDecimal _percent;
	private Frequency _frequency; 
	private LocalDate _startDate; 
	private LocalDate _endDate;
	
	private RecurringYieldEvent() {} 
		
	@Override
	public ArrayList<EventInstance> getInstances() {
		ArrayList<EventInstance> tmpInstances = new ArrayList<EventInstance>(); 
		LocalDate currDate;
		
		FrequencyIterator itr = new FrequencyIterator(_frequency, _startDate, _endDate);
		while (itr.hasNext()) {
			currDate = itr.cursor;
			tmpInstances.add(YieldEventInstance.getNew(currDate, _percent));
			itr.next();
		}
		
		return tmpInstances;
	}

	public static RecurringYieldEvent getNew(BigDecimal percent, Frequency frequency, LocalDate startDate, LocalDate endDate) {
		RecurringYieldEvent tmpEvnt = new RecurringYieldEvent();
		tmpEvnt.setPercent(percent);
		tmpEvnt.setFrequency(frequency);
		tmpEvnt.setStartDate(startDate);
		tmpEvnt.setEndDate(endDate);
		
		return tmpEvnt;
	}

	//Setters...
	private void setPercent(BigDecimal percent) {
		this._percent = percent;
	}

	private void setFrequency(Frequency frequency) {
		this._frequency = frequency;
	}
	
	private void setStartDate(LocalDate startDate) {
		this._startDate = startDate;
	}

	private void setEndDate(LocalDate endDate) {
		this._endDate = endDate;
	}
	
	
	private static final class FrequencyIterator implements Iterator<LocalDate> {
		private LocalDate cursor;
		private final LocalDate end;
		private final Frequency freq;

		public FrequencyIterator(Frequency freq, LocalDate start, LocalDate end) {
			this.cursor = start;
			this.end = end;
			this.freq = freq;
		}

		public boolean hasNext() {
			return this.cursor.isBefore(end);
		}

		public LocalDate next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			
			if (freq.equals(Frequency.MONTHLY)) {
				cursor = cursor.plusMonths(1);
			} 
			return cursor;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
}
