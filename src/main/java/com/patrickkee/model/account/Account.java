package com.patrickkee.model.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.patrickkee.model.model.type.Model;

/**
 * Stores a collection of models and metadata about the owner of those models.
 * Provies easy access to aggregated, cross model functions like getting the
 * total account value.
 * 
 * @author Kee
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String _accountName;
	private String _firstName;
	private String _lastName;
	private String _email;
	private HashMap<Integer, Model> _models = new HashMap<Integer, Model>();

	private Account() {
	}

	// Getters
	@JsonProperty("acctId")
	public int getAccountId() {
		return hashCode();
	}

	@JsonProperty("acctName")
	public String getAccountName() {
		return _accountName;
	}
	@JsonProperty("acctName")
	public void setAccountName(String name) {
		this._accountName = name;
	}
	
	@JsonProperty("firstName")
	public String getFirstName() {
		return _firstName;
	}
	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this._firstName = firstName;
	}
	
	@JsonProperty("lastName")
	public String getLastName() {
		return _lastName;
	}
	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this._lastName = lastName;
	}

	@JsonProperty("email")
	public String getEmail() {
		return _email;
	}
	@JsonProperty("email")
	public void setEmail(String email) {
		this._email = email;
	}
	
	@JsonIgnore
	public HashMap<Integer, Model> getModels() {
		return _models;
	}

	public void addModel(Model model) {
		_models.putIfAbsent(model.getModelId(), model);
	}

	public void removeModel(int modelId) {
		_models.remove(modelId);
	}

	public Optional<Model> getModel(int modelId) {
		return Optional.of(_models.get(modelId));
	}

	/**
	 * Provides easy access to calculating the value of the user's models at one
	 * point in time
	 * 
	 * @param date
	 *            as of when the user wants the account value
	 * @return account value as {@code BigDecimal}
	 */
	public BigDecimal getValue(Date date) {
		return new BigDecimal("100.21");
	}

	// Builder pattern for account construction
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

	// Hashcode & Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_accountName == null) ? 0 : _accountName.hashCode());
		return result * -1;
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
