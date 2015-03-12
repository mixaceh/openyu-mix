package org.openyu.mix.sasang.vo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class SasangEditorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式excel後,不應再使用,以免覆蓋掉正式的excel
	 */
	public void writeToExcel() {
		SasangEditor editor = SasangEditor.getInstance();
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
		SasangEditor editor = SasangEditor.getInstance();
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
