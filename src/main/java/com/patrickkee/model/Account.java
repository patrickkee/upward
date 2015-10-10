package com.patrickkee.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.impl.SavingsForecastModel;

/**
 * Stores a collection of models and metadata about the owner of those models. 
 * Provies easy access to aggregated, cross model functions like getting the total account value.
 * @author Kee
 * */
@XmlRootElement
public class Account {

	private String _accountName;
	private String _firstName;
	private String _lastName;
	private String _email;
	private ArrayList<SavingsForecastModel> _models; 
	
	private Account() {}
		
	/**
	 * Provides easy access to calculating the value of the user's models at one point in time
	 * @param date as of when the user wants the account value
	 * @return account value as {@code BigDecimal}
	 */
	public BigDecimal getValue(Date date) {
		return new BigDecimal("100.21");
	}
	
	//Getters
	public String getName() {
		return _accountName;
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public String getEmail() {
		return _email;
	}
	public ArrayList<SavingsForecastModel> getModels() {
		return _models;
	}
	public void addModel(SavingsForecastModel model) {
		_models.add(model);
	}
	
	//Builder pattern for account construction
	public static Account getAccount() {
		return new Account();
	}
	public Account accountName(String name) {
		this._accountName = name;
		return this;
	}
	public Account firstName(String firstName) {
		this._firstName = firstName;
		return this;
	}
	public Account lastName(String lastName) {
		this._lastName = lastName;
		return this;
	}
	public Account email(String email) {
		this._email = email;
		return this;
	}
}
