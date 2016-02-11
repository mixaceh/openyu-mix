package org.openyu.mix.sasang.vo;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.mix.sasang.vo.impl.SasangImpl;
import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.supporter.LocaleNameBeanSupporter;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.editor.ex.EditorException;
import org.openyu.commons.editor.supporter.BaseEditorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.BooleanHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.commons.util.LocaleHelper;

public class SasangEditor extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static SasangEditor sasangEditor;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 5;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public SasangEditor() {
	}

	// --------------------------------------------------
	public synchronized static SasangEditor getInstance() {
		return getInstance(true);
	}

	public synchronized static SasangEditor getInstance(boolean initial) {
		if (sasangEditor == null) {
			sasangEditor = new SasangEditor();
		}
		return sasangEditor;
	}

	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(SasangCollector.class);
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
			SasangCollector collector = CollectorHelper
					.readFromXml(SasangCollector.class);
			//
			result = workbook.createSheet("資料", 0);
			int dataRow = BEG_DATA_ROW;

			// 說明
			int col = 0;
			addDef(result, col, 0, "descriptions");
			addDef(result, col, 1, "idx");
			result.setColumnView(col, 5);
			addHeader(result, 0, BEG_HEADER_ROW, "索引");
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
			dataRow = BEG_DATA_ROW;
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
			addDef(result, col, 0, "playGold");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "花費的金幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getPlayGold());
			//
			col = 5;
			addDef(result, col, 0, "dailyTimes");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "每日可玩的次數");
			addNumber(result, col, BEG_DATA_ROW, collector.getDailyTimes());
			//
			col = 6;
			addDef(result, col, 0, "playItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "花費的道具");
			addString(result, col, BEG_DATA_ROW, collector.getPlayItem());
			//
			col = 7;
			addDef(result, col, 0, "playCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "花費的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getPlayCoin());

			// 元素
			col = 8;
			addDef(result, col, 0, "sasangs");
			addDef(result, col, 1, "idx");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "索引");
			//
			col = 9;
			addDef(result, col, 1, "sasang");
			addDef(result, col, 2, "id");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "元素");
			//
			col = 10;
			addDef(result, col, 2, "weights");
			addDef(result, col, 3, "idx");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "索引");
			//
			col = 11;
			addDef(result, col, 3, "weight");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "權重");
			//
			dataRow = BEG_DATA_ROW;
			idx = 0;
			List<Sasang> sasangs = collector.getSasangs();
			for (Sasang entry : sasangs) {
				addNumber(result, 8, dataRow, idx++);// 索引
				addString(result, 9, dataRow, entry.getId());// 元素
				//
				List<Integer> weights = entry.getWeights();
				int i = 0;
				for (Integer e : weights) {
					addNumber(result, 10, dataRow, i++);// 索引
					addNumber(result, 11, dataRow, e);// 權重
					++dataRow;
				}
				//
				if (weights.size() == 0) {
					++dataRow;
				}
			}

			// 開出的獎
			col = 12;
			addDef(result, col, 0, "prizes");
			addDef(result, col, 1, "key");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "獎勵ID");

			// 獎勵等級
			col = 13;
			addDef(result, col, 1, "level");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "獎勵等級");

			// 是否成名
			col = 14;
			addDef(result, col, 1, "famous");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "是否成名");

			// 獎勵
			col = 15;
			addDef(result, col, 1, "awards");
			addDef(result, col, 2, "key");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "道具ID");
			//
			col = 16;
			addDef(result, col, 2, "value");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "數量");
			//
			dataRow = BEG_DATA_ROW;
			Map<String, Prize> finalPrizes = collector.getPrizes();
			for (Map.Entry<String, Prize> entry : finalPrizes.entrySet()) {
				String id = entry.getKey();
				Prize prize = entry.getValue();
				addString(result, 12, dataRow, id);// 獎勵ID
				addNumber(result, 13, dataRow, prize.getLevel());// 獎勵等級
				addBoolean(result, 14, dataRow, prize.isFamous());// 顯示成名
				//
				Map<String, Integer> awards = prize.getAwards();
				for (Map.Entry<String, Integer> e : awards.entrySet()) {
					String itemId = e.getKey();
					Integer amount = e.getValue();
					addString(result, 15, dataRow, itemId);// 道具ID
					addNumber(result, 16, dataRow, amount);// 數量
					++dataRow;
				}
				//
				if (awards.size() == 0) {
					++dataRow;
				}
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
			result = workbook.createSheet("常數", 1);
			result.setColumnView(0, 10);
			result.setColumnView(1, 25);
			result.setColumnView(2, 10);
			//
			addHeader(result, 0, 0, "四象類別");
			addHeader(result, 1, 0, "值");
			addHeader(result, 2, 0, "數字");
			//
			addString(result, 0, 1, "青龍");
			addString(result, 1, 1, SasangType.AZURE_DRAGON);
			addNumber(result, 2, 1, SasangType.AZURE_DRAGON.getValue());
			//
			addString(result, 0, 2, "白虎");
			addString(result, 1, 2, SasangType.WHITE_TIGER);
			addNumber(result, 2, 2, SasangType.WHITE_TIGER.getValue());
			//
			addString(result, 0, 3, "朱雀");
			addString(result, 1, 3, SasangType.VERMILION_BIRD);
			addNumber(result, 2, 3, SasangType.VERMILION_BIRD.getValue());
			//
			addString(result, 0, 4, "玄武");
			addString(result, 1, 4, SasangType.BLACK_TORTOISE);
			addNumber(result, 2, 4, SasangType.BLACK_TORTOISE.getValue());
			//
			addString(result, 0, 5, "陰");
			addString(result, 1, 5, SasangType.YIN);
			addNumber(result, 2, 5, SasangType.YIN.getValue());
			//
			addString(result, 0, 6, "陽");
			addString(result, 1, 6, SasangType.YANG);
			addNumber(result, 2, 6, SasangType.YANG.getValue());
			//
			addString(result, 0, 7, "虛無");
			addString(result, 1, 7, SasangType.NOTHING);
			addNumber(result, 2, 7, SasangType.NOTHING.getValue());
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
			SasangCollector collector = SasangCollector.getInstance(false);
			String excelName = excelName(SasangCollector.class);
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

			// 花費的金幣
			String playGold = sheet.getCell(4, BEG_DATA_ROW).getContents();
			collector.setPlayGold(NumberHelper.toLong(playGold));

			// 每日可玩的次數
			String dailyTimes = sheet.getCell(5, BEG_DATA_ROW).getContents();
			collector.setDailyTimes(NumberHelper.toInt(dailyTimes));

			// 花費的道具
			String playItem = sheet.getCell(6, BEG_DATA_ROW).getContents();
			collector.setPlayItem(playItem);

			// 花費的儲值幣
			String playCoin = sheet.getCell(7, BEG_DATA_ROW).getContents();
			collector.setPlayCoin(NumberHelper.toInt(playCoin));

			// 元素
			Sasang sasang = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String id = sheet.getCell(9, i).getContents();
				if (StringHelper.notBlank(id)) {
					SasangType sasangType = EnumHelper.valueOf(
							SasangType.class, id);
					// 元素
					sasang = new SasangImpl(sasangType);

					// 權重
					String weight = sheet.getCell(11, i).getContents();
					sasang.getWeights().add(NumberHelper.toInt(weight));
					//
					collector.getSasangs().add(sasang);
				} else {
					if (sasang != null) {
						// 權重
						String weight = sheet.getCell(11, i).getContents();
						sasang.getWeights().add(NumberHelper.toInt(weight));
					}
				}
			}

			// 開出的獎
			Prize prize = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String id = sheet.getCell(12, i).getContents();
				if (StringHelper.notBlank(id)) {
					// 開出的獎
					prize = new PrizeImpl(id);

					// 獎勵等級
					String level = sheet.getCell(13, i).getContents();
					prize.setLevel(NumberHelper.toInt(level));

					// 是否成名
					String famous = sheet.getCell(14, i).getContents();
					prize.setFamous(BooleanHelper.toBoolean(famous));

					// 獎勵
					String itemId = sheet.getCell(15, i).getContents();
					String amount = sheet.getCell(16, i).getContents();
					prize.getAwards().put(itemId, NumberHelper.toInt(amount));
					//
					collector.getPrizes().put(prize.getId(), prize);
				} else {
					if (prize != null) {
						// 獎勵
						String itemId = sheet.getCell(15, i).getContents();
						if (StringHelper.notBlank(itemId)) {
							String amount = sheet.getCell(16, i).getContents();
							prize.getAwards().put(itemId,
									NumberHelper.toInt(amount));
						}
					}
				}
			}
			//
			result = CollectorHelper.writeToXml(SasangCollector.class, collector);
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		//
		return result;
	}
}
