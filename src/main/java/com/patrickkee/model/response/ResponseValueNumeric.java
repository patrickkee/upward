package com.patrickkee.model.response;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ResponseValueNumeric {

	private BigDecimal value;

	private ResponseValueNumeric() {
	}

	public static ResponseValueNumeric getNew(BigDecimal value) {
		ResponseValueNumeric rv = new ResponseValueNumeric();
		rv.setValue(value);
		return rv;
	}

	@JsonProperty("value")
	public BigDecimal getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(BigDecimal value) {
		this.value = value;
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
