package org.mql.java.test;

import java.util.List;

import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.reflexion.ProjectParser;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		 ProjectParser extractor = new ProjectParser();
	        String projectPath = "C:\\Users\\PC\\eclipse-workspace\\p03-Annotations and Reflection"; // Replace with your project path
	        List<PackageEntity> extractedProject = extractor.parse(projectPath);

	        // Process or display the extracted project information here
	        for (PackageEntity pkg : extractedProject) {
	            System.out.println("Package: " + pkg.getName());
	            for (ClassEntity cls : pkg.getClasses()) {
	                System.out.println("  Class: " + cls.getName());
	               
	            }
	        }

	}

}
