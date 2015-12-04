package org.openyu.mix.manor.vo;

import static org.junit.Assert.assertNotNull;
import jxl.write.WritableWorkbook;

import org.junit.Test;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.manor.vo.ManorEditor;

public class ManorEditorTest extends BaseTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式excel後,不應再使用,以免覆蓋掉正式的excel
	 */
	public void writeToExcel() {
		ManorEditor editor = ManorEditor.getInstance();
		String result = null;
		//
		result = editor.writeToExcel();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToXml() {
		ManorEditor editor = ManorEditor.getInstance();
		String result = null;
		//
		result = editor.writeToXml();
		//
		System.out.println(result);
		assertNotNull(result);
	}
}
