package com.almende.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;

import com.almende.util.AnnotationUtil.AnnotatedClass;
import com.almende.util.AnnotationUtil.AnnotatedMethod;
import com.almende.util.annotation.MethodA;
import com.almende.util.annotation.MethodB;

/**
 * Test chained inheritance
 */
public class TestChainedInheritance {
	@Test
	public void method1 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method1");
		assertEquals(1, methods.size());
		AnnotatedMethod method1 = methods.get(0);
		List<Annotation> annotations = method1.getAnnotations();
		assertEquals(1, annotations.size());
		
		Annotation a1 = annotations.get(0);
		assertTrue(a1 instanceof MethodA);
		assertEquals("method1interface1", ((MethodA) a1).name() );
	}
	
	@Test
	public void method2 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method2");
		assertEquals(1, methods.size());
		AnnotatedMethod method = methods.get(0);
		List<Annotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());
		
		Annotation a1 = annotations.get(0);
		assertTrue(a1 instanceof MethodA);
		assertEquals("method2interface2", ((MethodA) a1).name() );
	}
	
	@Test
	public void method3 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method3");
		assertEquals(1, methods.size());
		AnnotatedMethod method = methods.get(0);
		List<Annotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());
		
		Annotation a1 = annotations.get(0);
		assertTrue(a1 instanceof MethodA);
		assertEquals("method3superclass1", ((MethodA) a1).name() );
	}
	
	@Test
	public void method4 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method4");
		assertEquals(1, methods.size());
		AnnotatedMethod method = methods.get(0);
		List<Annotation> annotations = method.getAnnotations();
		assertEquals(2, annotations.size());
		
		Annotation a1 = annotations.get(0);
		Annotation a2 = annotations.get(1);
		assertTrue(a1 instanceof MethodA || a1 instanceof MethodB);
		assertTrue(a2 instanceof MethodA || a2 instanceof MethodB);

		MethodA a = method.getAnnotation(MethodA.class);
		assertNotNull(a);
		assertEquals("method4superclass2", a.name());
		
		MethodB b = method.getAnnotation(MethodB.class);
		assertNotNull(b);
		assertEquals("method4interface3", b.name());
	}
	
	@Test
	public void method5 () {
		AnnotatedClass c = AnnotationUtil.get(Class1.class);
		// System.out.println(Output.toString(c));
		
		List<AnnotatedMethod> methods = c.getMethods("method5");
		assertEquals(1, methods.size());
		AnnotatedMethod method = methods.get(0);
		List<Annotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());

		MethodA a = method.getAnnotation(MethodA.class);
		assertNotNull(a);
		assertEquals("method5superclass3", a.name());
	}
	
	
	class Class1 extends Superclass1 implements Interface1 {
		public void method1() {}
		public void method2() {}
	}

	interface Interface1 extends Interface2 {
		@MethodA(name="method1interface1")
		void method1();
	}
	
	interface Interface2 {
		@MethodA(name="method2interface2")
		void method2();
	}
	
	class Superclass1 extends Superclass2 {
		@MethodA(name="method3superclass1")
		void method3() {}
	}
	
	class Superclass2 extends Superclass3 implements Interface3 {
		@MethodA(name="method4superclass2")
		public void method4() {}
		public void method5() {}
	}

	interface Interface3 {
		@MethodB(name="method4interface3")
		void method4();
	}
	
	abstract class Superclass3 {
		@MethodA(name="method5superclass3")
		abstract void method5();
	}	
}
