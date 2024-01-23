package org.mql.java.reflexion;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.mql.java.models.AnnotationEntity;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.EnumEntity;
import org.mql.java.models.InterfaceEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.RelationEntity;
import org.mql.java.models.RelationEntity.RelationType;

public class ClassParser {
	 
	public ClassParser() {
	}

	public PackageEntity parse(String qName, PackageEntity pkg) throws ClassNotFoundException {
		
		
		Class<?> parsed = Class.forName(qName);
	       
		if (parsed.isEnum()) {
            EnumEntity enumEntity = parseEnum(parsed);
            pkg.addEnum(enumEntity);
            
		} else if (parsed.isAnnotation()) {
            AnnotationEntity annotationEntity = parseAnnotation(parsed);
            pkg.addAnnotation(annotationEntity);
            
        } else if (parsed.isInterface()) {
            InterfaceEntity interfaceEntity = parseInterface(parsed);
            pkg.addInterface(interfaceEntity);
           
        } else {
            ClassEntity classEntity = parseClass(parsed);
            pkg.addClass(classEntity);
        }
		return pkg;
    }

    private ClassEntity parseClass(Class<?> parsed) {
        ClassEntity classEntity = new ClassEntity(parsed.getSimpleName());

        int modifiers = parsed.getModifiers();
		classEntity.setAccessModifier(Modifier.toString(modifiers));
        
    	Field[] fields = parsed.getDeclaredFields();
    	
		if (fields.length != 0) {
			for (Field field : fields) {
				if (!field.isSynthetic()) {
					
					   ClassEntity.Attribute attribute = new ClassEntity.Attribute();
		                attribute.setAttributeName(field.getName());
		                attribute.setType(field.getType().getTypeName());
		                attribute.setAccessModifier(Modifier.toString(field.getModifiers()));

		                classEntity.getAttributes().add(attribute);
					
				}
			}
		}
		
		Method[] methods = parsed.getDeclaredMethods();

		if (methods.length != 0) {
		for (Method method : methods) {
			
			 ClassEntity.Method meth = new ClassEntity.Method();
			 meth.setMethodName(method.getName());
			 meth.setReturnType(method.getReturnType().getSimpleName());
			 meth.setAccessModifier(Modifier.toString(method.getModifiers()));

			 Type[] genericParameterTypes = method.getGenericParameterTypes();
			 
			 if (genericParameterTypes.length != 0) {
					for (int i = 0; i < genericParameterTypes.length; i++) {
						meth.getParametersTypes().add((genericParameterTypes[i].getTypeName()));
					}
				}
			 
             classEntity.getMethods().add(meth);
			}
		}
		
		
		Class<?> superCls = parsed.getSuperclass();
		if (superCls != null && superCls != Object.class) {
			
			//classEntity.getRelations().add(new RelationEntity(RelationType.EXTEND, superCls.getName()));
		}
			 

		Class<?>[] interfaces = parsed.getInterfaces();
		if (interfaces.length > 0) {
			
			for (Class<?> interf : interfaces) {
				classEntity.getRelations().add(new RelationEntity(RelationType.IMPLEMENT, interf.getName()));
			}
		}
		
		Class<?>[] innerCls = parsed.getDeclaredClasses();
		if (innerCls.length > 0) {
			for (Class<?> nestedClass : innerCls) {

				 classEntity.getRelations().add(new RelationEntity(RelationType.INNER_CLASS, nestedClass.getName()));
				 parseClass(nestedClass);	//the inner class is not added in the package yet!
				
			}
		}
        
        
        return classEntity;
    }

    private EnumEntity parseEnum(Class<?> parsed) {
        EnumEntity enumEntity = new EnumEntity(parsed.getSimpleName());

        Object[] enumConstants = parsed.getEnumConstants();
        if (enumConstants != null) {
            for (Object constant : enumConstants) {
                if (constant instanceof Enum<?>) {
                    Enum<?> enumConstant = (Enum<?>) constant;
                    enumEntity.getConstants().add(enumConstant.name());
                }
            }
        }
        return enumEntity;
    }

    private InterfaceEntity parseInterface(Class<?> parsed) {
        InterfaceEntity interfaceEntity = new InterfaceEntity(parsed.getSimpleName());

        Method[] methods = parsed.getDeclaredMethods();
        if (methods.length != 0) {
            for (Method method : methods) {
                InterfaceEntity.Method interfaceMethod = new InterfaceEntity.Method();
                interfaceMethod.setMethodName(method.getName());
                interfaceMethod.setReturnType(method.getReturnType().getSimpleName());
                interfaceMethod.setAccessModifier(Modifier.toString(method.getModifiers()));

                Type[] genericParameterTypes = method.getGenericParameterTypes();

                if (genericParameterTypes.length != 0) {
                    for (int i = 0; i < genericParameterTypes.length; i++) {
                        interfaceMethod.getParametersTypes().add(genericParameterTypes[i].getTypeName());
                    }
                }

                interfaceEntity.getMethods().add(interfaceMethod);
            }
        }
        return interfaceEntity;
    }

    private AnnotationEntity parseAnnotation(Class<?> parsed) {
        AnnotationEntity annotationEntity = new AnnotationEntity(parsed.getSimpleName());
        
        Class<? extends Annotation> annotationType = parsed.asSubclass(Annotation.class);
        Target targetAnnotation = annotationType.getAnnotation(Target.class);

        if (targetAnnotation != null) {
            ElementType[] elementTypes = targetAnnotation.value();
            annotationEntity.getTargetTypes().addAll(Arrays.stream(elementTypes)
                    .map(ElementType::toString)
                    .collect(Collectors.toList()));
        }

        Method[] methods = annotationType.getDeclaredMethods();
        for (Method method : methods) {
            AnnotationEntity.AnnotationMethod annotationMethod = new AnnotationEntity.AnnotationMethod();
            annotationMethod.setName(method.getName());
            annotationMethod.setType(method.getReturnType().getTypeName());

            try {
                Object defaultValue = method.getDefaultValue();
               
                annotationMethod.setDefaultValue(defaultValue.toString());
                
            } catch (Exception e) {}

            annotationEntity.getMethods().add(annotationMethod);
        }
        return annotationEntity;
    }
		

}	
		
		
		
		
		
		

