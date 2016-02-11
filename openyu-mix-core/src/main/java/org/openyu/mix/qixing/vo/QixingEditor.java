package org.openyu.mix.qixing.vo;

import java.io.File;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.openyu.commons.editor.ex.EditorException;
import org.openyu.commons.editor.supporter.BaseEditorSupporter;

public class QixingEditor extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static QixingEditor qixingEditor;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 5;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public QixingEditor() {
	}

	// --------------------------------------------------
	public synchronized static QixingEditor getInstance() {
		return getInstance(true);
	}

	public synchronized static QixingEditor getInstance(boolean initial) {
		if (qixingEditor == null) {
			qixingEditor = new QixingEditor();
		}
		return qixingEditor;
	}

	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(QixingCollector.class);
			File file = new File(excelName);
			workBook = Workbook.createWorkbook(file);
			// 資料
			// writeToSheet_0(result);
			// 常數
			// writeToSheet_1(result);
			//
			//workBook.write();
			//
			result = excelName;
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		//
		return result;
	}

	public String writeToXml() {
		String result = null;
		//
		Workbook workBook = null;
		try {
			// TODO Auto-generated method stub
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		return result;
	}
}
