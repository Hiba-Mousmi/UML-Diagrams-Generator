package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class ClassEntity {

	private String name;
	private String accessModifier;
	private List<Attribute> attributes;
	private List<Method> methods;
	private List<RelationEntity> relations;
	
	
	public ClassEntity(String name) {
		this.name = name;
		this.attributes = new Vector<>();
		this.methods = new Vector<>();
		this.setRelations(new Vector<>());
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public List<Method> getMethods() {
		return methods;
	}
	
	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	public String getAccessModifier() {
		return accessModifier;
	}
	
	public void setAccessModifier(String accessModifier) {
		this.accessModifier = accessModifier;
	}

	  public List<RelationEntity> getRelations() {
		return relations;
	}

	public void setRelations(List<RelationEntity> relations) {
		this.relations = relations;
	}

	public static class Attribute {
	        private String attributeName;
	        private String type;
	        private String accessModifier;
	        

	        public String getAttributeName() {
				return attributeName;
			}
	        public void setAttributeName(String attributeName) {
				this.attributeName = attributeName;
			}
	        public String getType() {
				return type;
			}
	        public void setType(String type) {
				this.type = type;
			}
	        public String getAccessModifier() {
				return accessModifier;
			}
	        public void setAccessModifier(String accessModifier) {
				this.accessModifier = accessModifier;
			}
	    }

	    public static class Method {
	        private String methodName;
	        private String returnType;
	        private String accessModifier;
	        private List<String> parametersTypes;

	        public Method() {
	    		
	    		this.parametersTypes = new Vector<>();
	    	
	    	}
	        
	        public String getAccessModifier() {
				return accessModifier;
			}
	        public void setAccessModifier(String accessModifier) {
				this.accessModifier = accessModifier;
			}
	        public String getMethodName() {
				return methodName;
			}
	        public void setMethodName(String methodName) {
				this.methodName = methodName;
			}
	        public String getReturnType() {
				return returnType;
			}
	        public void setReturnType(String returnType) {
				this.returnType = returnType;
			}
	        public List<String> getParametersTypes() {
				return parametersTypes;
			}
	        public void setParametersTypes(List<String> parametersTypes) {
				this.parametersTypes = parametersTypes;
			}
	    }
	    
	  
}
