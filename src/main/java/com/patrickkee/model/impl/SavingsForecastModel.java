package com.patrickkee.model.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.Model;

@XmlRootElement
public class SavingsForecastModel implements Model {

	private int _modelId = 0;
	private String _name;
	private String _description;
	private BigDecimal _initialValue;
	private BigDecimal _targetValue;
	private Date _startDate;
	private Date _endDate;

	private SavingsForecastModel() {
	}

	// Getters
	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public BigDecimal getInitialValue() {
		return _initialValue;
	}

	public BigDecimal getTargetValue() {
		return _targetValue;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public BigDecimal getValue(Date date) {
		return new BigDecimal("100.42");
	}

	@Override
	public int getModelId() {
		if (_modelId == 0) {
			_modelId = hashCode();
			return _modelId;
		} else {
			return _modelId;
		}

	}

	// Methods for builder pattern
	public static SavingsForecastModel newModel() {
		return new SavingsForecastModel();
	}

	public SavingsForecastModel name(String name) {
		this._name = name;
		return this;
	}

	public SavingsForecastModel description(String description) {
		this._description = description;
		return this;
	}

	public SavingsForecastModel initialValue(BigDecimal initialValue) {
		this._initialValue = initialValue;
		return this;
	}

	public SavingsForecastModel targetValue(BigDecimal targetValue) {
		this._targetValue = targetValue;
		return this;
	}

	public SavingsForecastModel startDate(Date startDate) {
		this._startDate = startDate;
		return this;
	}

	public SavingsForecastModel endDate(Date endDate) {
		this._endDate = endDate;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_description == null) ? 0 : _description.hashCode());
		result = prime * result + ((_endDate == null) ? 0 : _endDate.hashCode());
		result = prime * result + ((_initialValue == null) ? 0 : _initialValue.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_startDate == null) ? 0 : _startDate.hashCode());
		result = prime * result + ((_targetValue == null) ? 0 : _targetValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingsForecastModel other = (SavingsForecastModel) obj;
		if (_description == null) {
			if (other._description != null)
				return false;
		} else if (!_description.equals(other._description))
			return false;
		if (_endDate == null) {
			if (other._endDate != null)
				return false;
		} else if (!_endDate.equals(other._endDate))
			return false;
		if (_initialValue == null) {
			if (other._initialValue != null)
				return false;
		} else if (!_initialValue.equals(other._initialValue))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_startDate == null) {
			if (other._startDate != null)
				return false;
		} else if (!_startDate.equals(other._startDate))
			return false;
		if (_targetValue == null) {
			if (other._targetValue != null)
				return false;
		} else if (!_targetValue.equals(other._targetValue))
			return false;
		return true;
	}

}
