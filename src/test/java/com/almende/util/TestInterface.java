package com.almende.util;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;

import com.almende.util.AnnotationUtil.AnnotatedClass;
import com.almende.util.AnnotationUtil.AnnotatedMethod;
import com.almende.util.AnnotationUtil.AnnotatedParam;
import com.almende.util.annotation.ClassA;
import com.almende.util.annotation.ClassB;
import com.almende.util.annotation.MethodA;
import com.almende.util.annotation.MethodB;
import com.almende.util.annotation.MethodC;
import com.almende.util.annotation.ParamA;
import com.almende.util.annotation.ParamB;

public class TestInterface {
	/**
	 * Test merging of class annotations
	 */
	@Test 
	public void classAnnotations() {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<Annotation> annotations = c.getAnnotations();
		assertEquals(2, annotations.size());
		Annotation a1 = annotations.get(0);
		Annotation a2 = annotations.get(1);
		assertTrue(a1 instanceof ClassA || a1 instanceof ClassB);
		assertTrue(a2 instanceof ClassA || a2 instanceof ClassB);

		ClassA a = c.getAnnotation(ClassA.class);
		assertNotNull(a);
		assertEquals("class1", a.name());
		
		ClassB b = c.getAnnotation(ClassB.class);
		assertNotNull(b);
		assertEquals("interface1", b.name());
	}
	/**
	 * Test merging of method annotations
	 */
	@Test 
	public void methodAnnotations() {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method5");
		assertEquals(1, methods.size());
		
		AnnotatedMethod method5 = methods.get(0);
		
		List<Annotation> annotations = method5.getAnnotations();
		assertEquals(2, annotations.size());
		Annotation a1 = annotations.get(0);
		Annotation a2 = annotations.get(1);
		assertTrue(a1 instanceof MethodA || a1 instanceof MethodB);
		assertTrue(a2 instanceof MethodA || a2 instanceof MethodB);
		
		MethodA a = method5.getAnnotation(MethodA.class);
		assertNotNull(a);
		assertEquals("method5", a.name());
		
		MethodB b = method5.getAnnotation(MethodB.class);
		assertNotNull(b);
		assertEquals("method5interface", b.name());
		
		assertNull(method5.getAnnotation(MethodC.class));
	}
	
	/**
	 * Test if annotations from the class itself are read
	 */
	@Test
	public void paramAnnotations1 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method1");
		assertEquals(1, methods.size());
		
		AnnotatedMethod method1 = methods.get(0);
		List<AnnotatedParam> params = method1.getParams();
		assertEquals(2, params.size());
		
		AnnotatedParam param1 = params.get(0);
		List<Annotation> annotations1 = param1.getAnnotations();
		assertEquals(1, annotations1.size());
		assertTrue(annotations1.get(0) instanceof ParamA);
		ParamA param1a = (ParamA)annotations1.get(0);
		assertEquals("param1", param1a.name());
		
		AnnotatedParam param2 = params.get(1);
		List<Annotation> annotations2 = param2.getAnnotations();
		assertEquals(1, annotations2.size());
	}
	
	/**
	 * Test if annotations from an interface are read
	 */
	@Test
	public void paramAnnotations2 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method2");
		assertEquals(1, methods.size());
		
		AnnotatedMethod method1 = methods.get(0);
		List<AnnotatedParam> params = method1.getParams();
		assertEquals(2, params.size());
		
		AnnotatedParam param1 = params.get(0);
		List<Annotation> annotations1 = param1.getAnnotations();
		assertEquals(1, annotations1.size());
		assertTrue(annotations1.get(0) instanceof ParamA);
		ParamA param1a = (ParamA)annotations1.get(0);
		assertEquals("param1interface", param1a.name());
	}
	
	/**
	 * Test if overridden annotations from an interface are overridden correctly
	 */
	@Test
	public void paramAnnotations3 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method3");
		assertEquals(1, methods.size());
		
		AnnotatedMethod method1 = methods.get(0);
		List<AnnotatedParam> params = method1.getParams();
		assertEquals(2, params.size());
		
		AnnotatedParam param1 = params.get(0);
		List<Annotation> annotations1 = param1.getAnnotations();
		assertEquals(1, annotations1.size());
		assertTrue(annotations1.get(0) instanceof ParamA);
		ParamA param1a = (ParamA)annotations1.get(0);
		assertEquals("param1", param1a.name());
	}

	
	/**
	 * Test if differing annotations from an interface are merged correctly
	 */
	@Test
	public void paramAnnotations4 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method4");
		assertEquals(1, methods.size());
		
		AnnotatedMethod method1 = methods.get(0);
		List<AnnotatedParam> params = method1.getParams();
		assertEquals(2, params.size());
		
		// first parameter
		AnnotatedParam param1 = params.get(0);
		List<Annotation> annotations1 = param1.getAnnotations();
		assertEquals(1, annotations1.size());
		assertTrue(annotations1.get(0) instanceof ParamA);
		ParamA param1a = (ParamA)annotations1.get(0);
		assertEquals("param1", param1a.name());
		
		// second parameter
		AnnotatedParam param2 = params.get(1);
		List<Annotation> annotations2 = param2.getAnnotations();
		assertEquals(2, annotations2.size());
		Annotation a1 = annotations2.get(0);
		Annotation a2 = annotations2.get(1);
		assertTrue(a1 instanceof ParamA || a1 instanceof ParamB);
		assertTrue(a2 instanceof ParamA || a2 instanceof ParamB);

		ParamA a = param2.getAnnotation(ParamA.class);
		assertNotNull(a);
		assertEquals("param2", a.name());
		
		ParamB b = param2.getAnnotation(ParamB.class);
		assertNotNull(b);
		assertEquals("param2interface", b.name());		
	}
	
	@ClassA(name="class1")
	class Class1 implements Interface1 {
		public void method1(
				@ParamA(name="param1") String param1, 
				@ParamA(name="param2") String param2) {}
	
		public void method2(String param1, String param2) {}
		
		public void method3(
				@ParamA(name="param1") String param1, 
				@ParamA(name="param2") String param2) {}
	
		public void method4(
				@ParamA(name="param1") String param1, 
				@ParamA(name="param2") String param2) {}
		
		@MethodA(name="method5")
		public void method5 () {}
	}
	
	@ClassA(name="interface1")
	@ClassB(name="interface1")
	interface Interface1 {
		void method1(String param1, String param2);
		
		void method2(
				@ParamA(name="param1interface") String param1, 
				@ParamA(name="param2interface") String param2);
	
		void method3(
				@ParamA(name="param1interface") String param1, 
				@ParamA(name="param2interface") String param2);
		
		void method4(
				@ParamA(name="param1interface") String param1, 
				@ParamA(name="param2interface") @ParamB(name="param2interface") String param2);
		
		@MethodA(name="method5interface")
		@MethodB(name="method5interface")
		void method5();
	}
}

