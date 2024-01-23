package org.mql.java.test;

import java.util.List;

import org.mql.java.models.AnnotationEntity;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.EnumEntity;
import org.mql.java.models.InterfaceEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.RelationEntity;

public class ConsolePrinter {

    public static void printPackages(List<PackageEntity> packages) {
    
        for (PackageEntity pkg : packages) {
            System.out.println("Package: " + pkg.getName());
            printClasses(pkg.getClasses());
            printAnnotations(pkg.getAnnotations());
            printInterfaces(pkg.getInterfaces());
            printEnums(pkg.getEnums());
            
            System.out.println("----------------------");
        }
    }

    private static void printClasses(List<ClassEntity> classes) {
        for (ClassEntity cls : classes) {
            System.out.println("Class: " + cls.getName());
            if(!cls.getAttributes().isEmpty()) {
            System.out.println("Attributes:");
            for (ClassEntity.Attribute attribute : cls.getAttributes()) {
                System.out.println("  - " + attribute.getAccessModifier() + " " + attribute.getType() + " " + attribute.getAttributeName());
            }
            }
            System.out.println("Methods:");
            for (ClassEntity.Method method : cls.getMethods()) {
                System.out.println("  - " + method.getAccessModifier() + " " + method.getReturnType() + " " + method.getMethodName());
            }
            System.out.println("Relations:");
            for (RelationEntity relation : cls.getRelations()) {
                System.out.println("  - " + cls.getName() + " " + relation.getType() + " " + relation.getTarget());
            }
            System.out.println("----------------------");
        }
        
        
    }

    public static void printInterfaces(List<InterfaceEntity> interfaces) {
        for (InterfaceEntity interf : interfaces) {
            System.out.println("Interface: " + interf.getName());
            System.out.println("Methods:");
            for (InterfaceEntity.Method method : interf.getMethods()) {
                System.out.println("  - " + method.getAccessModifier() + " " + method.getReturnType() + " " + method.getMethodName());
            }
            System.out.println("----------------------");
        }
    }

    public static void printEnums(List<EnumEntity> enums) {
        for (EnumEntity enumEntity : enums) {
            System.out.println("Enum: " + enumEntity.getName());
            System.out.println("Constants:");
            for (String constant : enumEntity.getConstants()) {
                System.out.println("  - " + constant);
            }
            System.out.println("----------------------");
        }
    }

    public static void printAnnotations(List<AnnotationEntity> annotations) {
        for (AnnotationEntity annotation : annotations) {
            System.out.println("Annotation: " + annotation.getName());
            System.out.println("Methods:");
            for (AnnotationEntity.AnnotationMethod method : annotation.getMethods()) {
                System.out.println("  - " + method.getName() + " : " + method.getType() +
                        (method.getDefaultValue() != "" ? " = " + method.getDefaultValue() : ""));
            }
            System.out.println("Target Types:");
            for (String targetType : annotation.getTargetTypes()) {
                System.out.println("  - " + targetType);
            }
            System.out.println("----------------------");
        }
    }
}
