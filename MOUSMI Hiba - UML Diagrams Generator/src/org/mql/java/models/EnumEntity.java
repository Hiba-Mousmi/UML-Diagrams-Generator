package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class EnumEntity {
	
	 private String name;
	 private List<String> constants;

	public EnumEntity(String name) {
		this.name = name;
		this.constants = new Vector<String>();
	} 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getConstants() {
		return constants;
	}
	public void setConstants(List<String> constants) {
		this.constants = constants;
	}

}
