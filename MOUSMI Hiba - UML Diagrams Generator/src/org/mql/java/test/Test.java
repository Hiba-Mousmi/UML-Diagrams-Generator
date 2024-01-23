package org.mql.java.test;

import java.util.List;

import javax.swing.SwingUtilities;

import org.mql.java.dom.XmlParser;
import org.mql.java.dom.XmlPersistence;
import org.mql.java.models.PackageEntity;
import org.mql.java.reflexion.ProjectParser;
import org.mql.java.ui.MainFrame;

public class Test {

	public Test() {}

	public static void main(String[] args) {
		
		
	        String projectPath = "C:\\Users\\PC\\eclipse-workspace\\p03-Annotations and Reflection"; 
	        String xmlFilePath = "resources/project.xml";
	        
	        ProjectParser parser = new ProjectParser();
	        List<PackageEntity> extractedProject = parser.parse(projectPath);

	        XmlPersistence.persistToXml(extractedProject, xmlFilePath);
	      
	        List<PackageEntity> parserProject = XmlParser.parseFromXml(xmlFilePath);
	        ConsolePrinter.printPackages(parserProject);
	        
	        new MainFrame(parserProject);
	}

}
