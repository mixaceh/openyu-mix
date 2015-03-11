package org.openyu.mix.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		System.out.println("--- Pre Test ---");
		//
		// print applicationContext-init.xml
		InputStream in = App.class
				.getResourceAsStream("/applicationContext-init.xml");
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		//
		BufferedReader bufferedReader = null;
		StringBuilder buff = new StringBuilder();
		String line;
		try {
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				buff.append(line);
				buff.append("\r\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
				}
			}
		}
		//
		System.out.println(buff);

		// print applicationContext
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		//
		System.out.println(applicationContext);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames, Collator.getInstance(java.util.Locale.ENGLISH));
		//
		System.out.println("=========================================");
		System.out.println("Spring beans");
		System.out.println("=========================================");
		for (int i = 0; i < beanNames.length; i++) {
			System.out.println(beanNames[i]);
		}
	}
}
