package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class PackageEntity {
	
	 private String name;
	    private List<ClassEntity> classes;


	public PackageEntity(String name) {
		this.name = name;
        this.classes = new Vector<>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<ClassEntity> getClasses() {
		return classes;
	}
	
	 public void addClass(ClassEntity cls) {
	        classes.add(cls);
	    }

}
