package com.patrickkee.model.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.patrickkee.model.model.Model;

/**
 * Stores a collection of models and metadata about the owner of those models.
 * Provies easy access to aggregated, cross model functions like getting the
 * total account value.
 * 
 * @author Kee
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private ConcurrentHashMap<Integer, Model> models = new ConcurrentHashMap<>();

	private Account() {	} //Hidden to prevent null instances

	public Account(@JsonProperty("acctName") String name, 
				   @JsonProperty("firstName") String firstName, 
				   @JsonProperty("lastName") String lastName, 
				   @JsonProperty("email") String email) {
		
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(firstName);
		Preconditions.checkNotNull(lastName);
		Preconditions.checkNotNull(email);

		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	// Getters
	@JsonProperty("acctId")
	public int getAccountId() {
		return hashCode();
	}

	@JsonProperty("acctName")
	public String getAccountName() {
		return name;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public ConcurrentHashMap<Integer, Model> getModels() {
		return models;
	}

	public void addModel(Model model) {
		models.putIfAbsent(model.getModelId(), model);
	}

	public void removeModel(int modelId) {
		models.remove(modelId);
	}

	public Optional<Model> getModel(int modelId) {
		Model m = models.get(modelId);
		if (null != m) {
			return Optional.of(models.get(modelId));
		} else {
			return Optional.absent();
		}

	}

	/**
	 * Provides easy access to calculating the value of the user's models at one
	 * point in time
	 * 
	 * @param date
	 *            as of when the user wants the account value
	 * @return account value as {@code BigDecimal}
	 */
	public BigDecimal getValue(LocalDate date) {
		BigDecimal totalValue = BigDecimal.valueOf(0.0);
		for (Model model : models.values()) {
			totalValue = totalValue.add(model.getValue(date));
		}
		return totalValue.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	// Hashcode & Equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String json = "";

		try {
			json = mapper.writeValueAsString(this);
		} catch (IOException e) {
			// TODO: Something intelligent rather than ridiculously printing the
			// stack trace
			e.printStackTrace();
		}

		return json;
	}
}
