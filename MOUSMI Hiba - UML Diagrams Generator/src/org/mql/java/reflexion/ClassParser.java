package org.mql.java.reflexion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class ClassParser {

	public ClassParser() {
	}

	public StringBuffer parse(String qName) throws ClassNotFoundException {

		Class<?> parsed = Class.forName(qName);

		StringBuffer squelette = new StringBuffer();

		int modifiers = parsed.getModifiers();
		squelette.append(Modifier.toString(modifiers) + " class " + parsed.getTypeName());

		Class<?> superCls = parsed.getSuperclass();
		if (superCls != null && superCls != Object.class)
			squelette.append(" extends " + superCls);

		Class<?>[] interfaces = parsed.getInterfaces();
		if (interfaces.length > 0) {
			squelette.append(" implements ");
			for (int i = 0; i < interfaces.length; i++) {
				squelette.append(interfaces[i].getTypeName());
				if (i < interfaces.length - 1) {
					squelette.append(", ");
				}
			}
		}

		squelette.append(" {\n");

		Field[] fields = parsed.getDeclaredFields();
		if (fields.length != 0) {
			for (Field field : fields) {
				if (!field.isSynthetic()) {
					squelette.append("  " + Modifier.toString(field.getModifiers()) + " "
							+ field.getType().getTypeName() + " " + field.getName() + ";\n");
				}
			}
		}

		Constructor<?>[] constructors = parsed.getDeclaredConstructors();

		/*
		 * for (Constructor<?> constructor : constructors) {
		 * squelette.append("\n"+constructor); } // descriptif par défaut
		 */

		for (Constructor<?> constructor : constructors) {
			squelette.append("  " + Modifier.toString(constructor.getModifiers()) + " " + parsed.getSimpleName() + "(");
			Parameter[] parameters = constructor.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				if (!parameters[i].isSynthetic()) {
					squelette.append(parameters[i].getType().getTypeName());
					if (i < parameters.length - 1) {
						squelette.append(", ");
					}
				}
			}
			squelette.append(") {}\n");
		}

		Method[] methods = parsed.getDeclaredMethods();

		for (Method method : methods) {
			squelette.append("  " + Modifier.toString(method.getModifiers()) + " "
					+ method.getReturnType().getSimpleName() + " " + method.getName() + "(");

			Type[] genericParameterTypes = method.getGenericParameterTypes();
			if (genericParameterTypes.length > 0) {
				for (int i = 0; i < genericParameterTypes.length; i++) {
					squelette.append(genericParameterTypes[i].getTypeName());
					if (i < genericParameterTypes.length - 1) {
						squelette.append(", ");
					}
				}

			}
			squelette.append(") {}");
			squelette.append("\n");
		}

		Class<?>[] innerCls = parsed.getDeclaredClasses();
		if (innerCls.length > 0) {
			for (Class<?> nestedClass : innerCls) {

				squelette.append(parse(nestedClass.getName()));
				squelette.append("}\n");
			}
		}

		StringBuilder chaineHeritage = new StringBuilder();
		while (superCls != null) {
			if (chaineHeritage.length() > 0) {
				chaineHeritage.insert(0, " <- ");
			}
			chaineHeritage.insert(0, superCls.getSimpleName());
			superCls = superCls.getSuperclass();
		}

		squelette.append("\nNombre de propriétés : " + fields.length + "\n")
				.append("Nombre de constructeurs : " + constructors.length + "\n")
				.append("Nombre de méthodes : " + methods.length + "\n")
				.append("Nombre de classes internes : " + innerCls.length + "\n")
				.append("Nombre d'interfaces implémentées : " + interfaces.length + "\n").append("Héritage : ")
				.append(chaineHeritage + "\n");

	
		Annotation[] anotations = parsed.getAnnotations();
		if (anotations.length > 0) {
			for (Annotation annotation : anotations) {
				squelette.append("Annotation de Type Classe: " + annotation.annotationType());
			}
		}

		for (Method method : methods) {
			Annotation[] methodAnnotations = method.getAnnotations();
			if (methodAnnotations.length > 0) {
				for (Annotation annotation : methodAnnotations) {
					squelette.append(
							"\nMéthode : " + method.getName() + " - Type Annotation: " + annotation.annotationType());
				}
			}
		}

		return squelette;

	}
}
