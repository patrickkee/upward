package com.patrickkee.model.impl;

import org.joda.time.DateTime;

public class ApplicationStatus {

	private String _status;
	private String _description;
	private DateTime _dateTime;

	private ApplicationStatus() {	}

	public static ApplicationStatus getNew(String status, String desc) {
		ApplicationStatus stat = new ApplicationStatus();
		stat.setDateTime(DateTime.now());
		stat.setStatus(status);
		stat.setDescription(desc);
		return stat;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String _status) {
		this._status = _status;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String _description) {
		this._description = _description;
	}

	public DateTime getDateTime() {
		return _dateTime;
	}

	public void setDateTime(DateTime _dateTime) {
		this._dateTime = _dateTime;
	}

}
