package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class AnnotationEntity {
	private String name;
    private List<AnnotationMethod> methods;
	private List<String> targetTypes;
    
	public AnnotationEntity(String name) {
		this.name = name;
		this.methods = new Vector<>();
		this.targetTypes = new Vector<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<AnnotationMethod> getMethods() {
		return methods;
	}
	public void setMethods(List<AnnotationMethod> methods) {
		this.methods = methods;
	}
	public List<String> getTargetTypes() {
		return targetTypes;
	}
	public void setTargetTypes(List<String> targetTypes) {
		this.targetTypes = targetTypes;
	}

	public static class AnnotationMethod {
        private String name;
        private String type;
        private String defaultValue;
        
        public String getName() {
			return name;
		}
        public void setName(String name) {
			this.name = name;
		}
        public String getType() {
			return type;
		}
        public void setType(String type) {
			this.type = type;
		}
        public String getDefaultValue() {
			return defaultValue;
		}
        public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
	}
}
