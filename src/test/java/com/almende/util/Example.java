package com.almende.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import com.almende.util.AnnotationUtil.AnnotatedClass;
import com.almende.util.AnnotationUtil.AnnotatedMethod;
import com.almende.util.AnnotationUtil.AnnotatedParam;

public class Example {
	public static void main(String args[]) {
		// Get annotated class from a Java class
		AnnotatedClass annotatedClass = AnnotationUtil.get(MyClass.class);
		
		// Get all class methods
		List<AnnotatedMethod> methods = annotatedClass.getMethods();
		for (AnnotatedMethod method : methods) {
			System.out.println("Method: " + method.getName());
			
			// get a particular method annotation
			Expose expose = method.getAnnotation(Expose.class);
			if (expose != null) {
				System.out.println("expose: " + expose.value());
			}
			
			// loop over all parameters
			List<AnnotatedParam> params = method.getParams();
			for (AnnotatedParam param : params) {
				// loop over all parameter annotations
				for (Annotation annotation : param.getAnnotations()) {
					System.out.println("Param annotation: " + annotation);
				}
			}
		}
	}

	class MyClass implements MyInterface {
		@Expose(true)
		public void myMethod(String a, String b) {
			// ...
		}
	}

	interface MyInterface {
		public void myMethod(@Name("a") String a, @Name("b") String b);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(value=ElementType.METHOD)
	public @interface Expose {
		boolean value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(value=ElementType.PARAMETER)
	public @interface Name {
		String value();
	}
}
