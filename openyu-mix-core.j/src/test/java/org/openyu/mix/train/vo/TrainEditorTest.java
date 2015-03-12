package org.openyu.mix.train.vo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.mix.train.vo.TrainEditor;

public class TrainEditorTest {

	@Test
	public void writeToExcel() {
		TrainEditor editor = TrainEditor.getInstance();
		String result = null;
		//
		long beg = System.currentTimeMillis();
		//
		result = editor.writeToExcel();
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToXml() {
		TrainEditor editor = TrainEditor.getInstance();
		String result = null;
		//
		long beg = System.currentTimeMillis();
		//
		result = editor.writeToXml();
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

}
