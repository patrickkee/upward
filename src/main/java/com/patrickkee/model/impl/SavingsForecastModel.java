package com.patrickkee.model.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.Model;

@XmlRootElement
public class SavingsForecastModel implements Model {

	private String _name;

	public SavingsForecastModel() {}
	
	public SavingsForecastModel(String _name){
		this._name = _name;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	@Override
	public BigDecimal getValue(Date date) {
		return new BigDecimal("100.42");
	} 
	
	
	
}
