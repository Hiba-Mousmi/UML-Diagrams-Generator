package org.mql.java.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

import org.mql.java.models.AnnotationEntity;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.RelationEntity;
import org.mql.java.models.ClassEntity.Attribute;
import org.mql.java.models.ClassEntity.Method;
import org.mql.java.models.EnumEntity;
import org.mql.java.models.InterfaceEntity;

public class XmlPersistence {

	 public static void persistToXml(List<PackageEntity> packages, String outputPath) {
	        try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	            Document doc = docBuilder.newDocument();

	            Element rootElement = doc.createElement("Project");
	            doc.appendChild(rootElement);

	           
	            for (PackageEntity pkg : packages) {
	                Element packageElement = doc.createElement("Package");
	                packageElement.setAttribute("name", pkg.getName());
	                rootElement.appendChild(packageElement);
	                
	                if(!pkg.getClasses().isEmpty())
	                	persistClassesToXml(doc, packageElement, pkg.getClasses());
	                if(!pkg.getInterfaces().isEmpty())
	                	persistInterfacesToXml(doc, packageElement, pkg.getInterfaces());
	                if(!pkg.getEnums().isEmpty())
	                	persistEnumsToXml(doc, packageElement, pkg.getEnums());
	                if(!pkg.getAnnotations().isEmpty())
	                	persistAnnotationsToXml(doc, packageElement, pkg.getAnnotations());
	            }

	           
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty("indent", "yes");
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File(outputPath));

	            transformer.transform(source, result);

	            System.out.println("Structure de données persistée dans le fichier XML avec succès.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void persistClassesToXml(Document doc, Element packageElement, List<ClassEntity> classes) {
	        for (ClassEntity cls : classes) {
	            Element classElement = doc.createElement("Class");
	            classElement.setAttribute("name", cls.getName());
	            
	          
	            
	            List<Attribute> attEntities = cls.getAttributes();
	            if(!attEntities.isEmpty()) {
	            	Element attElements = doc.createElement("Attributes");
	            	  
		            for (Attribute attEntity : attEntities) {
						Element attElement = doc.createElement("Attribute");
						attElement.setAttribute("name", attEntity.getAttributeName());
						attElement.setAttribute("type", attEntity.getType());
						attElement.setAttribute("accessModifier", attEntity.getAccessModifier());
						
						attElements.appendChild(attElement);
					} 
		            classElement.appendChild(attElements);
	            }
	            
	           
	            
	          
	           
	            List<Method> methodEntities = cls.getMethods();
	            if (!methodEntities.isEmpty()) {
	            	Element methodElements = doc.createElement("Methods");
	            	 
	                for (Method methodEntity : methodEntities) {
	                    Element methodElement = doc.createElement("Method");
	                    methodElement.setAttribute("name", methodEntity.getMethodName());
	                    methodElement.setAttribute("returnType", methodEntity.getReturnType());
	                    methodElement.setAttribute("accessModifier", methodEntity.getAccessModifier());

	                    
	                    
	                    List<String> parametersTypes = methodEntity.getParametersTypes();
	                    if (!parametersTypes.isEmpty()) {
	                    	Element parametersElement = doc.createElement("Parameters");
	                    	
	                        for (String parameterType : parametersTypes) {
	                            Element parameterElement = doc.createElement("Parameter");
	                            parameterElement.setAttribute("type", parameterType);
	                            parametersElement.appendChild(parameterElement);
	                        }
	                      methodElement.appendChild(parametersElement);
	                    }
	                    

	                    methodElements.appendChild(methodElement);
	                }
	              classElement.appendChild(methodElements);
	            }
	            

	            
	            
	            List<RelationEntity> relationEntities = cls.getRelations();
	            if (!relationEntities.isEmpty()) {
	            	Element relationElements = doc.createElement("Relations");
	            	
	                for (RelationEntity relationEntity : relationEntities) {
	                    Element relationElement = doc.createElement("Relation");
	                    relationElement.setAttribute("targetClass", relationEntity.getTarget());
	                    relationElement.setAttribute("relationType", relationEntity.getType().toString());
	                    relationElements.appendChild(relationElement);
	                }
	               classElement.appendChild(relationElements);
	            }
	            

	        
	            packageElement.appendChild(classElement);
	           

	        }
	    }
	    
	    private static void persistInterfacesToXml(Document doc, Element packageElement, List<InterfaceEntity> interfaces) {
	    	
	    	 for (InterfaceEntity interf : interfaces) {
	             Element interfaceElement = doc.createElement("Interface");
	             interfaceElement.setAttribute("name", interf.getName());

	            
	             List<InterfaceEntity.Method> methodEntities = interf.getMethods();
	             if (!methodEntities.isEmpty()) {
	            	 Element methodElements = doc.createElement("Methods");
	            	 
	                 for (InterfaceEntity.Method methodEntity : methodEntities) {
	                     Element methodElement = doc.createElement("Method");
	                     methodElement.setAttribute("name", methodEntity.getMethodName());
	                     methodElement.setAttribute("returnType", methodEntity.getReturnType());
	                     methodElement.setAttribute("accessModifier", methodEntity.getAccessModifier());

	                     List<String> parametersTypes = methodEntity.getParametersTypes();
		                    if (!parametersTypes.isEmpty()) {
		                    	Element parametersElement = doc.createElement("Parameters");
		                    	
		                        for (String parameterType : parametersTypes) {
		                            Element parameterElement = doc.createElement("Parameter");
		                            parameterElement.setAttribute("type", parameterType);
		                            parametersElement.appendChild(parameterElement);
		                        }
		                      methodElement.appendChild(parametersElement);
		                    }
		                    
	                     methodElements.appendChild(methodElement);
	                 }
	                 interfaceElement.appendChild(methodElements);
	             }

	             packageElement.appendChild(interfaceElement);
	         }
	    }
	    private static void persistEnumsToXml(Document doc, Element packageElement, List<EnumEntity> enums) {
	    	
	    	  for (EnumEntity enumEntity : enums) {
	              Element enumElement = doc.createElement("Enum");
	              enumElement.setAttribute("name", enumEntity.getName());

	              
	              List<String> constants = enumEntity.getConstants();
	              if (!constants.isEmpty()) {
	            	  Element constantElements = doc.createElement("Constants");
	                  for (String constant : constants) {
	                      Element constantElement = doc.createElement("Constant");
	                      constantElement.setAttribute("name", constant);
	                      constantElements.appendChild(constantElement);
	                  }
	                  enumElement.appendChild(constantElements);
	              }
	              

	              packageElement.appendChild(enumElement);
	          }
	    }
	    private static void persistAnnotationsToXml(Document doc, Element packageElement, List<AnnotationEntity> annots) {
	    	
	    	  for (AnnotationEntity annotationEntity : annots) {
	              Element annotationElement = doc.createElement("Annotation");
	              annotationElement.setAttribute("name", annotationEntity.getName());
	              
	           
	              List<String> targetTypes = annotationEntity.getTargetTypes();
	              if (!targetTypes.isEmpty()) {
	                  Element targetElement = doc.createElement("Target");
	                  for (String targetType : targetTypes) {
	                      Element targetTypeElement = doc.createElement("Type");
	                      targetTypeElement.appendChild(doc.createTextNode(targetType));
	                      targetElement.appendChild(targetTypeElement);
	                  }
	                  annotationElement.appendChild(targetElement);
	              }

	           
	              List<AnnotationEntity.AnnotationMethod> methods = annotationEntity.getMethods();
	              if (!methods.isEmpty()) {
	                  Element methodsElement = doc.createElement("Methods");
	                  for (AnnotationEntity.AnnotationMethod method : methods) {
	                      Element methodElement = doc.createElement("Method");
	                      methodElement.setAttribute("name", method.getName());
	                      methodElement.setAttribute("type", method.getType());

	                     
	                      String defaultValue = method.getDefaultValue();
	                      if (defaultValue != null && !defaultValue.isEmpty()) {
	                          methodElement.setAttribute("defaultValue", defaultValue);
	                      }

	                      methodsElement.appendChild(methodElement);
	                  }
	                  annotationElement.appendChild(methodsElement);
	              }
	              
	              packageElement.appendChild(annotationElement);
	          }
	    }

}
