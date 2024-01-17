package org.mql.java.models;

import java.util.List;
import java.util.Vector;


public class InterfaceEntity {

	 private String name;
	 private List<Method> methods;
	    
	public InterfaceEntity(String name) {
		this.name = name;
		 this.methods = new Vector<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	public List<Method> getMethods() {
		return methods;
	}
	public void addMethod(Method method) {
		methods.add(method);
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
