package com.patrickkee.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseMessage {

	private String _message;
	
	private ResponseMessage() {};
	
	public ResponseMessage(String message) {
		_message = message;
	}
	
	public String getMessage() {
		return _message;
	}
}
