package org.openyu.mix.train.vo;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.ex.EditorException;
import org.openyu.commons.bean.supporter.BaseEditorSupporter;
import org.openyu.commons.bean.supporter.LocaleNameBeanSupporter;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.commons.util.LocaleHelper;

public class TrainEditor extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static TrainEditor trainEditor;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 5;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public TrainEditor() {
	}

	// --------------------------------------------------
	public synchronized static TrainEditor getInstance() {
		return getInstance(true);
	}

	public synchronized static TrainEditor getInstance(boolean initial) {
		if (trainEditor == null) {
			trainEditor = new TrainEditor();
		}
		return trainEditor;
	}

	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(TrainCollector.class);
			File file = new File(excelName);
			workBook = Workbook.createWorkbook(file);
			// 資料
			writeToSheet_0(workBook);
			// 常數
			writeToSheet_1(workBook);
			//
			workBook.write();
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

	/**
	 * 工作表 0
	 * 
	 * @param workbook
	 * @return
	 */
	protected WritableSheet writeToSheet_0(WritableWorkbook workbook) {
		WritableSheet result = null;
		try {
			TrainCollector collector = beanCollector
					.readFromXml(TrainCollector.class);
			//
			result = workbook.createSheet("資料", 0);
			int dataRow = BEG_DATA_ROW;

			// 說明
			int col = 0;
			addDef(result, col, 0, "descriptions");
			addDef(result, col, 1, "idx");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "索引");
			//
			col = 1;
			addDef(result, col, 1, "locale");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "語系");
			//
			col = 2;
			addDef(result, col, 1, "name");
			result.setColumnView(col, 25);
			addHeader(result, col, BEG_HEADER_ROW, "說明");
			//
			NamesBean descriptions = collector.getDescriptions();
			Set<LocaleNameBean> names = descriptions.getNames();
			int idx = 0;
			for (LocaleNameBean entry : names) {
				Locale locale = entry.getLocale();
				String name = entry.getName();
				addNumber(result, 0, dataRow, idx++);// 索引
				addString(result, 1, dataRow, locale);// 語系
				addString(result, 2, dataRow, name);// 說明
				//
				++dataRow;
			}
			//
			col = 3;
			addDef(result, col, 0, "levelLimit");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "等級限制");
			addNumber(result, col, BEG_DATA_ROW, collector.getLevelLimit());
			//
			col = 4;
			addDef(result, col, 0, "dailyMills");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "每日上限毫秒");
			addNumber(result, col, BEG_DATA_ROW, collector.getDailyMills());
			//
			col = 5;
			addDef(result, col, 0, "intervalMills");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "間隔毫秒");
			addNumber(result, col, BEG_DATA_ROW, collector.getIntervalMills());
			//
			col = 6;
			addDef(result, col, 0, "inspireItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "鼓舞的道具");
			addString(result, col, BEG_DATA_ROW, collector.getInspireItem());
			//
			col = 7;
			addDef(result, col, 0, "inspireCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "鼓舞的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getInspireCoin());
			//
			col = 8;
			addDef(result, col, 0, "inspireRate");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "鼓舞效果,經驗增加比率 (萬分位)");
			addNumber(result, col, BEG_DATA_ROW, collector.getInspireRate());

			// 每周期獲得的經驗
			col = 9;
			addDef(result, col, 0, "exps");
			addDef(result, col, 1, "key");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "等級");
			//
			col = 10;
			addDef(result, col, 1, "value");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "經驗");
			//
			dataRow = BEG_DATA_ROW;
			Map<Integer, Long> exps = collector.getExps();
			for (Map.Entry<Integer, Long> entry : exps.entrySet()) {
				Integer index = entry.getKey();
				Long exp = entry.getValue();
				addNumber(result, 9, dataRow, index);// 等級
				addNumber(result, 10, dataRow, exp);// 經驗
				++dataRow;
			}
		} catch (Exception ex) {
			throw new EditorException(ex);
		}
		//
		return result;
	}

	/**
	 * 工作表 1
	 * 
	 * @param workbook
	 * @return
	 */
	protected WritableSheet writeToSheet_1(WritableWorkbook workbook) {
		WritableSheet result = null;
		try {
			//
		} catch (Exception ex) {
			throw new EditorException(ex);
		}
		//
		return result;
	}

	public String writeToXml() {
		String result = null;
		//
		Workbook workBook = null;
		try {
			TrainCollector collector = TrainCollector.getInstance(false);
			String excelName = excelName(TrainCollector.class);
			File file = new File(excelName);
			workBook = Workbook.getWorkbook(file);
			//
			Sheet sheet = workBook.getSheet(0);
			// int columns = result.getColumns();
			int rows = sheet.getRows();

			// 說明
			NamesBean descriptions = collector.getDescriptions();
			collector.setDescriptions(descriptions);
			//
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String locale = sheet.getCell(1, i).getContents();
				Locale localeValue = LocaleHelper.toLocale(locale);
				if (localeValue == null) {
					continue;
				}
				//
				LocaleNameBean localeNameBean = new LocaleNameBeanSupporter();
				localeNameBean.setLocale(localeValue);
				//
				String name = sheet.getCell(2, i).getContents();
				localeNameBean.setName(name);
				descriptions.getNames().add(localeNameBean);
			}
			// 等級限制
			String levelLimit = sheet.getCell(3, BEG_DATA_ROW).getContents();
			collector.setLevelLimit(NumberHelper.toInt(levelLimit));

			// 每日限制毫秒
			String dailyMills = sheet.getCell(4, BEG_DATA_ROW).getContents();
			collector.setDailyMills(NumberHelper.toLong(dailyMills));

			// 間隔毫秒
			String intervalMills = sheet.getCell(5, BEG_DATA_ROW).getContents();
			collector.setIntervalMills(NumberHelper.toLong(intervalMills));

			// 鼓舞的道具
			String inspireItem = sheet.getCell(6, BEG_DATA_ROW).getContents();
			collector.setInspireItem(inspireItem);

			// 鼓舞的儲值幣
			String inspireCoin = sheet.getCell(7, BEG_DATA_ROW).getContents();
			collector.setInspireCoin(NumberHelper.toInt(inspireCoin));

			// 刷新毫秒
			String inspireRate = sheet.getCell(8, BEG_DATA_ROW).getContents();
			collector.setInspireRate(NumberHelper.toInt(inspireRate));

			// 每周期獲得的經驗
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String level = sheet.getCell(9, i).getContents();
				if (StringHelper.notBlank(level)) {
					String exp = sheet.getCell(10, i).getContents();
					collector.getExps().put(NumberHelper.toInt(level),
							NumberHelper.toLong(exp));
				}
			}
			//
			result = beanCollector.writeToXml(TrainCollector.class, collector);
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		//
		return result;
	}
}
