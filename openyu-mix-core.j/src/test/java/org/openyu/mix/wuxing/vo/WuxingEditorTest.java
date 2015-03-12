package org.openyu.mix.wuxing.vo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class WuxingEditorTest {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式excel後,不應再使用,以免覆蓋掉正式的excel
	 */
	public void writeToExcel() {
		WuxingEditor editor = WuxingEditor.getInstance();
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
		WuxingEditor editor = WuxingEditor.getInstance();
		String result = null;
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
