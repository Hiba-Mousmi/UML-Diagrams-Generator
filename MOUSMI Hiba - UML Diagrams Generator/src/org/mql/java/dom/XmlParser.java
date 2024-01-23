package org.mql.java.dom;

import org.mql.java.models.AnnotationEntity;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.EnumEntity;
import org.mql.java.models.InterfaceEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.RelationEntity;
import org.mql.java.models.RelationEntity.RelationType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {

    public static List<PackageEntity> parseFromXml(String xmlFilePath) {
        List<PackageEntity> packages = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(xmlFilePath));
            doc.getDocumentElement().normalize();

            NodeList packageNodes = doc.getElementsByTagName("Package");

            for (int i = 0; i < packageNodes.getLength(); i++) {
                Node packageNode = packageNodes.item(i);

                if (packageNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element packageElement = (Element) packageNode;
                    PackageEntity pkg = parsePackage(packageElement);
                    packages.add(pkg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packages;
    }

    private static PackageEntity parsePackage(Element packageElement) {
        PackageEntity pkg = new PackageEntity(packageElement.getAttribute("name"));

        NodeList classNodes = packageElement.getElementsByTagName("Class");
        NodeList interfaceNodes = packageElement.getElementsByTagName("Interface");
        NodeList enumNodes = packageElement.getElementsByTagName("Enum");
        NodeList annotationNodes = packageElement.getElementsByTagName("Annotation");

        for (int i = 0; i < classNodes.getLength(); i++) {
            Element classElement = (Element) classNodes.item(i);
            ClassEntity cls = parseClass(classElement);
            pkg.addClass(cls);
        }

        for (int i = 0; i < interfaceNodes.getLength(); i++) {
            Element interfaceElement = (Element) interfaceNodes.item(i);
            InterfaceEntity interf = parseInterface(interfaceElement);
            pkg.addInterface(interf);
        }

        for (int i = 0; i < enumNodes.getLength(); i++) {
            Element enumElement = (Element) enumNodes.item(i);
            EnumEntity enumEntity = parseEnum(enumElement);
            pkg.addEnum(enumEntity);
        }

        for (int i = 0; i < annotationNodes.getLength(); i++) {
            Element annotationElement = (Element) annotationNodes.item(i);
            AnnotationEntity annotationEntity = parseAnnotation(annotationElement);
            pkg.addAnnotation(annotationEntity);
        }

        return pkg;
    }

    private static ClassEntity parseClass(Element classElement) {
        ClassEntity cls = new ClassEntity(classElement.getAttribute("name"));
        cls.setAccessModifier(classElement.getAttribute("accessModifier"));

        NodeList attributeNodes = classElement.getElementsByTagName("Attribute");
        NodeList methodNodes = classElement.getElementsByTagName("Method");
        NodeList relationNodes = classElement.getElementsByTagName("Relation");

        for (int i = 0; i < attributeNodes.getLength(); i++) {
            Element attributeElement = (Element) attributeNodes.item(i);
            ClassEntity.Attribute attribute = parseAttribute(attributeElement);
            cls.getAttributes().add(attribute);
        }

        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element methodElement = (Element) methodNodes.item(i);
            ClassEntity.Method method = parseMethod(methodElement);
            cls.getMethods().add(method);
        }
        
        for (int i = 0; i < relationNodes.getLength(); i++) {
            Element relationElement = (Element) relationNodes.item(i);
            RelationEntity relation = parserelation(relationElement);
            cls.getRelations().add(relation);
        }

        return cls;
    }
    
    private static RelationEntity parserelation(Element relationElement) {
    	RelationEntity.RelationType rltypeEnum = null;
    	String rltype = relationElement.getAttribute("relationType");
    	if(rltype == RelationType.AGGREGATION.toString()) rltypeEnum =RelationType.AGGREGATION;
    	if(rltype == RelationType.EXTEND.toString()) rltypeEnum =RelationType.EXTEND;
    	if(rltype == RelationType.IMPLEMENT.toString()) rltypeEnum =RelationType.IMPLEMENT;
    	if(rltype == RelationType.INNER_CLASS.toString()) rltypeEnum =RelationType.INNER_CLASS;
    	if(rltype == RelationType.USE.toString()) rltypeEnum =RelationType.USE;
    	 RelationEntity relation = new RelationEntity(rltypeEnum, relationElement.getAttribute("targetClass"));
		return relation;
	}

    private static ClassEntity.Attribute parseAttribute(Element attributeElement) {
        ClassEntity.Attribute attribute = new ClassEntity.Attribute();
        attribute.setAttributeName(attributeElement.getAttribute("name"));
        attribute.setType(attributeElement.getAttribute("type"));
        attribute.setAccessModifier(attributeElement.getAttribute("accessModifier"));
        return attribute;
    }

    private static ClassEntity.Method parseMethod(Element methodElement) {
        ClassEntity.Method method = new ClassEntity.Method();
        method.setMethodName(methodElement.getAttribute("name"));
        method.setReturnType(methodElement.getAttribute("returnType"));
        method.setAccessModifier(methodElement.getAttribute("accessModifier"));

        NodeList parameterNodes = methodElement.getElementsByTagName("Parameter");

        for (int i = 0; i < parameterNodes.getLength(); i++) {
            Element parameterElement = (Element) parameterNodes.item(i);
            method.getParametersTypes().add(parameterElement.getAttribute("type"));
        }

        return method;
    }

    private static InterfaceEntity parseInterface(Element interfaceElement) {
        InterfaceEntity interfaceEntity = new InterfaceEntity(interfaceElement.getAttribute("name"));

        NodeList methodNodes = interfaceElement.getElementsByTagName("Method");

        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element methodElement = (Element) methodNodes.item(i);
            InterfaceEntity.Method method = new InterfaceEntity.Method();
            method.setMethodName(methodElement.getAttribute("name"));
            method.setReturnType(methodElement.getAttribute("returnType"));
            method.setAccessModifier(methodElement.getAttribute("accessModifier"));

            NodeList parameterNodes = methodElement.getElementsByTagName("Parameter");
            for (int j = 0; j < parameterNodes.getLength(); j++) {
                Element parameterElement = (Element) parameterNodes.item(j);
                method.getParametersTypes().add(parameterElement.getAttribute("type"));
            }

            interfaceEntity.addMethod(method);
        }

        return interfaceEntity;
    }

    private static EnumEntity parseEnum(Element enumElement) {
        EnumEntity enumEntity = new EnumEntity(enumElement.getAttribute("name"));

        NodeList constantNodes = enumElement.getElementsByTagName("Constant");

        for (int i = 0; i < constantNodes.getLength(); i++) {
            Element constantElement = (Element) constantNodes.item(i);
            enumEntity.getConstants().add(constantElement.getAttribute("name"));
        }

        return enumEntity;
    }

    private static AnnotationEntity parseAnnotation(Element annotationElement) {
        AnnotationEntity annotationEntity = new AnnotationEntity(annotationElement.getAttribute("name"));

       
        NodeList methodNodes = annotationElement.getElementsByTagName("Method");
        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element methodElement = (Element) methodNodes.item(i);
            AnnotationEntity.AnnotationMethod method = new AnnotationEntity.AnnotationMethod();
            method.setName(methodElement.getAttribute("name"));
            method.setType(methodElement.getAttribute("type"));
            method.setDefaultValue(methodElement.getAttribute("defaultValue"));

            annotationEntity.getMethods().add(method);
        }

        NodeList targetTypeNodes = annotationElement.getElementsByTagName("Type");
        for (int i = 0; i < targetTypeNodes.getLength(); i++) {
            Element targetTypeElement = (Element) targetTypeNodes.item(i);
            annotationEntity.getTargetTypes().add(targetTypeElement.getTextContent());
        }

        return annotationEntity;
    }
    
    
}

