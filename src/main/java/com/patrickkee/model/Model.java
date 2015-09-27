package com.patrickkee.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Model {

	private String _name;

	public Model() {}
	
	public Model(String _name){
		this._name = _name;
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	} 
	
	
	
}
