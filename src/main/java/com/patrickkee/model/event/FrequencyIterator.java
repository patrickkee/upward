package com.patrickkee.model.event;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.joda.time.LocalDate;

public final class FrequencyIterator implements Iterator<LocalDate> {
	private LocalDate cursor;
	private final LocalDate end;
	private final Periods freq;

	private FrequencyIterator(Periods freq, LocalDate start, LocalDate end) {
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
		
		if (freq.equals(Periods.MONTHLY)) {
			cursor = cursor.plusMonths(1);
		} 
		return cursor;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	public static FrequencyIterator getNew(Periods freq, LocalDate start, LocalDate end) {
		return new FrequencyIterator(freq, start, end);
		
	}

	public LocalDate getCursor() {
		return cursor;
	}
}