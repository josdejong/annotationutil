package com.almende.util;

import java.lang.annotation.Annotation;

import com.almende.util.AnnotationUtil.AnnotatedClass;
import com.almende.util.AnnotationUtil.AnnotatedMethod;
import com.almende.util.AnnotationUtil.AnnotatedParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Output {

	public static ObjectNode toJSON(AnnotatedClass clazz) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		
		// clazz
		json.put("clazz", clazz.toString());
		
		// annotations
		ArrayNode jsonAnnotations = mapper.createArrayNode();
		if (clazz.getAnnotations() != null) {
			for (Annotation a : clazz.getAnnotations()) {
				jsonAnnotations.add(a.toString());
			}
		}
		json.put("annotations", jsonAnnotations);
		
		// methods
		ArrayNode jsonMethods = mapper.createArrayNode();
		if (clazz.getMethods() != null) {
			for (AnnotatedMethod m : clazz.getMethods()) {
				jsonMethods.add(toJSON(m));
			}
		}
		json.put("methods", jsonMethods);
		
		return json;
	}

	public static String toString(AnnotatedClass clazz) {
		return toString(toJSON(clazz));
	}
	
	public static ObjectNode toJSON(AnnotatedMethod method) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		
		// method
		json.put("method", method.getActualMethod().toString());
		
		// annotations
		ArrayNode jsonAnnotations = mapper.createArrayNode();
		if (method.getAnnotations() != null) {
			for (Annotation a : method.getAnnotations()) {
				jsonAnnotations.add(a.toString());
			}
		}
		json.put("annotations", jsonAnnotations);
		
		// params
		ArrayNode jsonParams = mapper.createArrayNode();
		if (method.getParams() != null) {
			for (AnnotatedParam p : method.getParams()) {
				jsonParams.add(toJSON(p));
			}
		}
		json.put("params", jsonParams);
		
		return json;
	}

	public static String toString(AnnotatedMethod method) {
		return toString(toJSON(method));
	}
	
	public static ObjectNode toJSON(AnnotatedParam param) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();
		
		// params
		ArrayNode jsonAnnotations = mapper.createArrayNode();
		if (param.getAnnotations() != null) {
			for (Annotation a : param.getAnnotations()) {
				jsonAnnotations.add(a.toString());
			}
		}
		json.put("annotations", jsonAnnotations);
		
		return json;
	}
	
	public static String toString(AnnotatedParam param) {
		return toString(toJSON(param));
	}
	
	public static String toString(ObjectNode json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			return mapper.writeValueAsString(json);
		} catch (Exception e) {}
		return null;
	}
}
