package org.mql.java.reflexion;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.mql.java.dom.XmlPersistence;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;

public class ProjectParser {
	
	private List<PackageEntity> packages;
	private ClassParser classParser;

	public ProjectParser() {
		this.packages = new Vector<>();
		 this.classParser = new ClassParser(); 
	}
	
	 public List<PackageEntity> parse(String projectPath) {
		 
		 
	        File projectDir = new File(projectPath, "src");
	        if (!projectDir.exists() || !projectDir.isDirectory()) {
	            throw new IllegalArgumentException("Invalid project directory");
	        }
	        
	        extractPackages(projectDir, "");
	        return packages;
	    }

	    private void extractPackages(File directory, String parentPackage) {
	    	
	        File[] files = directory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isDirectory()) {
	                    String packageName = (parentPackage.isEmpty()) ? file.getName() : parentPackage + "." + file.getName();
	                    PackageEntity pkg = new PackageEntity(packageName);
	                    
	                    packages.add(pkg);
	                    extractPackages(file, packageName);
						extractClasses(file, pkg);
					
	                }
	            }
	          }
	        }
	    
	 
	 

    private void extractClasses(File directory, PackageEntity pkg) {
    	
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {  
                	
                
                   // String className = file.getName().replace(".java", "");
                    //ClassEntity cls = new ClassEntity(className);
                    
                  
                	String relativePath = file.getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
                	String className = relativePath.substring(0, relativePath.lastIndexOf(".java")).replace(File.separator, ".");

                    ClassEntity cls = new ClassEntity(className);
                    

                    try {
                  	    
                       String classQualifiedName = pkg.getName() + "." + className;
                       
                        PackageEntity pkgeParsed = classParser.parse(classQualifiedName, pkg);
                       
                        
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    
               
                }
            }
        }
    }
    
    public void persistToXml(String outputPath) {
        XmlPersistence.persistToXml(packages, outputPath);
    }

}
