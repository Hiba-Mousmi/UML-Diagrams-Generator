package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class PackageEntity {
	
	 private String name;
	    private List<ClassEntity> classes;
	    private List<InterfaceEntity> interfaces;
	    private List<EnumEntity> enums;
	    private List<AnnotationEntity> annotations;


	public PackageEntity(String name) {
		this.name = name;
        this.classes = new Vector<>();
        this.interfaces = new Vector<>();
        this.enums = new Vector<>();
        this.annotations = new Vector<>();
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
	 
	 public List<AnnotationEntity> getAnnotations() {
		return annotations;
	}
	 public void addAnnotation(AnnotationEntity annot) {
		annotations.add(annot);

	}
	 
	 public List<EnumEntity> getEnums() {
		return enums;
	}
	 
	 public void addEnum(EnumEntity enumEntity) {
		enums.add(enumEntity);

	}
	 public List<InterfaceEntity> getInterfaces() {
		return interfaces;
	}
	 
	 public void addInterface(InterfaceEntity inter) {
		interfaces.add(inter);

	}

}
