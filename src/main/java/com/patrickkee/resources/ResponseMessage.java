package com.patrickkee.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {

	private String _message;
	private String _description;
	
	private ResponseMessage() {};
	
	private ResponseMessage(String message, String description) {
		_message = message;
		_description = description;
	}
	
	@JsonProperty("message")
	public String getMessage() {
		return _message;
	}
	@JsonProperty("message")
	public void setMessage(String message) {
		this._message = message;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return _description;
	}
	@JsonProperty("description")
	public void SetDescription(String description) {
		this._description = description;
	}
	
	public static ResponseMessage getNew(String message, String description) { 
		return new ResponseMessage(message, description);
	}
}
