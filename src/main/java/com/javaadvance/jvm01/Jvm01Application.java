package com.javaadvance.jvm01;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jvm01Application {

	public static void main(String[] args) {
		try {
			String resPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			MyClassLoader myClassLoader = new MyClassLoader(resPath);
			Class<?> content = myClassLoader.findClass("Hello");
			Object obj = content.newInstance();
			obj.getClass().getMethod("hello").invoke(obj);

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

}
