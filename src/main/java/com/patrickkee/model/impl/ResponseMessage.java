package com.patrickkee.model.impl;

public class ResponseMessage {

	private String _message;
	private String _description;
	
	private ResponseMessage() {};
	
	private ResponseMessage(String message, String description) {
		_message = message;
		_description = description;
	}
	
	public String getMessage() {
		return _message;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public static ResponseMessage getNew(String message, String description) { 
		return new ResponseMessage(message, description);
	}
}
