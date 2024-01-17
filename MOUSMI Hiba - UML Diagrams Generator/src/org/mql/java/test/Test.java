package org.mql.java.test;

import java.util.List;

import org.mql.java.models.ClassEntity;
import org.mql.java.models.ClassEntity.Attribute;
import org.mql.java.models.ClassEntity.Method;
import org.mql.java.models.PackageEntity;
import org.mql.java.reflexion.ProjectParser;

public class Test {

	public Test() {}

	public static void main(String[] args) {
		
		 ProjectParser parser = new ProjectParser();
	        String projectPath = "C:\\Users\\PC\\eclipse-workspace\\p03-Annotations and Reflection"; 
	        List<PackageEntity> extractedProject = parser.parse(projectPath);

	       parser.persistToXml("resources/output.xml");
	     

	}

}
