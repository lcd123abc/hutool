package com.xiaoleilu.hutool.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.xiaoleilu.hutool.util.ClassUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;

/**
 * {@link ClassUtil} 单元测试
 * @author Looly
 *
 */
public class ClassUtilTest {
	
	@Test
	public void getClassNameTest(){
		String className = ClassUtil.getClassName(ClassUtil.class, false);
		Assert.assertEquals("com.xiaoleilu.hutool.util.ClassUtil", className);
		
		String simpleClassName = ClassUtil.getClassName(ClassUtil.class, true);
		Assert.assertEquals("ClassUtil", simpleClassName);
	}
	
	@SuppressWarnings("unused")
	class TestClass {
		private String privateField;
		protected String field;
		private void privateMethod(){}
		public void publicMethod(){}
	}
	
	@SuppressWarnings("unused")
	class TestSubClass extends TestClass{
		private String subField;
		private void privateSubMethod(){}
		public void publicSubMethod(){}

	}

	@Test
	@Ignore
	public void invoke() throws Exception {
		ClassUtil.invoke("Class.method", new String[]{"arg"});

		ClassUtil.invoke("class.method", false, "arg1", "arg2");

		ClassUtil.invoke("class", "method", false, new String[]{"arg1", "arg2"});

		ClassUtil.invoke("Class", "method", "arg1", "arg2");

		ClassUtil.invoke(1, "method", "args1", "arg2");
	}

	@Test
	public void getPublicMethod(){
		Method superPublicMethod = ClassUtil.getPublicMethod(TestSubClass.class, "publicMethod");
		Assert.assertNotNull(superPublicMethod);
		Method superPrivateMethod = ClassUtil.getPublicMethod(TestSubClass.class, "privateMethod");
		Assert.assertNull(superPrivateMethod);

		Method publicMethod = ClassUtil.getPublicMethod(TestSubClass.class, "publicSubMethod");
		Assert.assertNotNull(publicMethod);
		Method privateMethod = ClassUtil.getPublicMethod(TestSubClass.class, "privateSubMethod");
		Assert.assertNull(privateMethod);
	}

	@Test
	public void getDeclaredMethod() throws Exception {
		Method noMethod = ClassUtil.getDeclaredMethod(TestSubClass.class, "noMethod");
		Assert.assertNull(noMethod);

		Method privateMethod = ClassUtil.getDeclaredMethod(TestSubClass.class, "privateMethod");
		Assert.assertNotNull(privateMethod);
		Method publicMethod = ClassUtil.getDeclaredMethod(TestSubClass.class, "publicMethod");
		Assert.assertNotNull(publicMethod);

		Method publicSubMethod = ClassUtil.getDeclaredMethod(TestSubClass.class, "publicSubMethod");
		Assert.assertNotNull(publicSubMethod);
		Method privateSubMethod = ClassUtil.getDeclaredMethod(TestSubClass.class, "privateSubMethod");
		Assert.assertNotNull(privateSubMethod);

	}

	@Test
	public void getDeclaredField()  {
		System.out.println(CollectionUtil.newArrayList(TestSubClass.class.getDeclaredFields()));

		Field noField = ClassUtil.getDeclaredField(TestSubClass.class, "noField");
		Assert.assertNull(noField);

		//能够获取到父类字段
		Field privateField = ClassUtil.getField(TestSubClass.class, "privateField");
		Assert.assertNotNull(privateField);

		//获取不到父类字段
		Field field = ClassUtil.getDeclaredField(TestSubClass.class, "field");
		Assert.assertNull(field);

		Field subField = ClassUtil.getDeclaredField(TestSubClass.class, "subField");
		Assert.assertNotNull(subField);
	}
}