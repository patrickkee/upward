package com.patrickkee.model.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import com.patrickkee.model.Model;

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
	private HashMap<Integer, Model> _models = new HashMap<Integer, Model>();
		
	private Account() {}
		
	//Getters
	public int getAccountId() {
		return hashCode();
	}
	public String getAccountName() {
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
	public HashMap<Integer, Model> getModels() {
		return _models;
	}
	public void addModel(Model model) {
		_models.putIfAbsent(model.getModelId(), model);
	}
	public void removeModel(int modelId) {
		_models.remove(modelId);
	}
	public Model getModel(int modelId) {
		return _models.get(modelId);
	}
	
	/**
	 * Provides easy access 	to calculating the value of the user's models at one point in time
	 * @param date as of when the user wants the account value
	 * @return account value as {@code BigDecimal}
	 */
	public BigDecimal getValue(Date date) {
		return new BigDecimal("100.21");
	}
	
	//Builder pattern for account construction
	public static Account newAccount() {
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

	//Hashcode & Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_accountName == null) ? 0 : _accountName.hashCode());
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
		Account other = (Account) obj;
		if (_accountName == null) {
			if (other._accountName != null)
				return false;
		} else if (!_accountName.equals(other._accountName))
			return false;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_firstName == null) {
			if (other._firstName != null)
				return false;
		} else if (!_firstName.equals(other._firstName))
			return false;
		if (_lastName == null) {
			if (other._lastName != null)
				return false;
		} else if (!_lastName.equals(other._lastName))
			return false;
		return true;
	}
}
