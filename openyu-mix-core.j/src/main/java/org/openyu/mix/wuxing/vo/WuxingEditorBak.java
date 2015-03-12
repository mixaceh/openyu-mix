package org.openyu.mix.wuxing.vo;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.app.vo.impl.PrizeImpl;
import org.openyu.mix.wuxing.vo.WuxingType;
import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.ex.EditorException;
import org.openyu.commons.bean.supporter.BaseEditorSupporter;
import org.openyu.commons.bean.supporter.LocaleNameBeanSupporter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.io.IoHelper;
import org.openyu.commons.lang.ClassHelper;
import org.openyu.commons.lang.StringHelper;

public class WuxingEditorBak extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static WuxingEditorBak wuxingCollector;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 7;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public WuxingEditorBak() {
	}

	// --------------------------------------------------
	public synchronized static WuxingEditorBak getInstance() {
		return getInstance(true);
	}

	public synchronized static WuxingEditorBak getInstance(boolean initial) {
		if (wuxingCollector == null) {
			wuxingCollector = new WuxingEditorBak();
		}
		return wuxingCollector;
	}

	/**
	 * 寫出到excel
	 */
	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(WuxingCollector.class);
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
			WuxingCollector collector = beanCollector
					.readFromXml(WuxingCollector.class);
			//
			result = workbook.createSheet("資料", 0);
			result.setColumnView(0, 5);
			result.setColumnView(1, 10);
			result.setColumnView(2, 25);
			result.setColumnView(3, 10);
			result.setColumnView(4, 25);
			result.setColumnView(5, 25);
			result.setColumnView(6, 25);
			result.setColumnView(7, 25);
			// 開出的獎
			result.setColumnView(8, 10);
			result.setColumnView(9, 10);
			result.setColumnView(10, 10);
			result.setColumnView(11, 15);
			result.setColumnView(12, 10);
			// 生的獎
			result.setColumnView(13, 10);
			result.setColumnView(14, 10);
			result.setColumnView(15, 10);
			result.setColumnView(16, 15);
			result.setColumnView(17, 10);
			// 和的獎
			result.setColumnView(18, 10);
			result.setColumnView(19, 10);
			result.setColumnView(20, 10);
			result.setColumnView(21, 15);
			result.setColumnView(22, 10);
			//
			// 獎勵
			addDef(result, 0, 0, "descriptions");
			addDef(result, 0, 1, NamesBeanSupporter.class.getName());
			addDef(result, 0, 2, "names");
			addDef(result, 0, 3, LinkedHashSet.class.getName());
			addDef(result, 0, 4, "*");
			addHeader(result, 0, BEG_HEADER_ROW, "索引");
			addDef(result, 1, 4, LocaleNameBeanSupporter.class.getName());
			//
			addDef(result, 1, 5, "locale");
			addDef(result, 1, 6, Locale.class.getName());
			addHeader(result, 1, BEG_HEADER_ROW, "語系");
			//
			addDef(result, 2, 5, "name");
			addDef(result, 2, 6, String.class.getName());
			addHeader(result, 2, BEG_HEADER_ROW, "說明");
			//
			int row = BEG_DATA_ROW;
			NamesBean descriptions = collector.getDescriptions();
			Set<LocaleNameBean> names = descriptions.getNames();
			int idx = 0;
			for (LocaleNameBean entry : names) {
				Locale locale = entry.getLocale();
				String name = entry.getName();
				addNumber(result, 0, row, idx++);// 索引
				addString(result, 1, row, locale);// 語系
				addString(result, 2, row, name);// 說明
				//
				++row;
			}
			//
			addDef(result, 3, 0, "levelLimit");
			addDef(result, 3, 1, int.class.getName());
			addHeader(result, 3, BEG_HEADER_ROW, "等級限制");
			addNumber(result, 3, BEG_DATA_ROW, collector.getLevelLimit());
			//
			addDef(result, 4, 0, "playGold");
			addDef(result, 4, 1, long.class.getName());
			addHeader(result, 4, BEG_HEADER_ROW, "玩一次所花費的金幣");
			addNumber(result, 4, BEG_DATA_ROW, collector.getPlayGold());
			//
			addDef(result, 5, 0, "dailyTimes");
			addDef(result, 5, 1, int.class.getName());
			addHeader(result, 5, BEG_HEADER_ROW, "花費金幣,每日可玩的次數");
			addNumber(result, 5, BEG_DATA_ROW, collector.getDailyTimes());
			//
			addDef(result, 6, 0, "playItem");
			addDef(result, 6, 1, String.class.getName());
			addHeader(result, 6, BEG_HEADER_ROW, "玩一次所花費的道具");
			addString(result, 6, BEG_DATA_ROW, collector.getPlayItem());
			//
			addDef(result, 7, 0, "playCoin");
			addDef(result, 7, 1, int.class.getName());
			addHeader(result, 7, BEG_HEADER_ROW, "玩一次所花費的儲值幣");
			addNumber(result, 7, BEG_DATA_ROW, collector.getPlayCoin());

			// 勝負獎
			addDef(result, 8, 0, "finalPrizes");
			addDef(result, 8, 1, LinkedHashMap.class.getName());
			addDef(result, 8, 2, String.class.getName());// key
			addHeader(result, 8, BEG_HEADER_ROW, "勝負獎ID");
			//
			addDef(result, 9, 2, PrizeImpl.class.getName());// value

			// 獎勵等級
			addDef(result, 9, 3, "level");
			addDef(result, 9, 4, int.class.getName());
			addHeader(result, 9, BEG_HEADER_ROW, "獎勵等級");

			// 是否成名
			addDef(result, 10, 3, "famous");
			addDef(result, 10, 4, boolean.class.getName());
			addHeader(result, 10, BEG_HEADER_ROW, "是否成名");

			// 獎勵
			addDef(result, 11, 3, "awards");
			addDef(result, 11, 4, LinkedHashMap.class.getName());
			addDef(result, 11, 5, String.class.getName());// key
			addHeader(result, 11, BEG_HEADER_ROW, "道具ID");
			//
			addDef(result, 12, 5, Integer.class.getName());// value
			addHeader(result, 12, BEG_HEADER_ROW, "數量");
			//
			row = BEG_DATA_ROW;
			Map<String, Prize> finalPrizes = collector.getFinalPrizes();
			for (Map.Entry<String, Prize> entry : finalPrizes.entrySet()) {
				String id = entry.getKey();
				Prize prize = entry.getValue();
				addString(result, 8, row, id);// 獎勵ID
				addNumber(result, 9, row, prize.getLevel());// 獎勵等級
				addBoolean(result, 10, row, prize.isFamous());// 顯示成名
				//
				Map<String, Integer> awards = prize.getAwards();
				for (Map.Entry<String, Integer> e : awards.entrySet()) {
					String itemId = e.getKey();
					Integer amount = e.getValue();
					addString(result, 11, row, itemId);// 道具ID
					addNumber(result, 12, row, amount);// 數量
					++row;
				}
				//
				if (awards.size() == 0) {
					++row;
				}
			}

			// 生獎
			addDef(result, 13, 0, "birthPrizes");
			addDef(result, 13, 1, LinkedHashMap.class.getName());
			addDef(result, 13, 2, String.class.getName());// key
			addHeader(result, 13, BEG_HEADER_ROW, "生獎ID");
			//
			addDef(result, 14, 2, PrizeImpl.class.getName());// value

			// 獎勵等級
			addDef(result, 14, 3, "level");
			addDef(result, 14, 4, int.class.getName());
			addHeader(result, 14, BEG_HEADER_ROW, "獎勵等級");

			// 是否成名
			addDef(result, 15, 3, "famous");
			addDef(result, 15, 4, boolean.class.getName());
			addHeader(result, 15, BEG_HEADER_ROW, "是否成名");

			// 獎勵
			addDef(result, 16, 3, "awards");
			addDef(result, 16, 4, LinkedHashMap.class.getName());
			addDef(result, 16, 5, String.class.getName());// key
			addHeader(result, 16, BEG_HEADER_ROW, "道具ID");
			//
			addDef(result, 17, 5, Integer.class.getName());// value
			addHeader(result, 17, BEG_HEADER_ROW, "數量");
			//
			row = BEG_DATA_ROW;
			Map<String, Prize> birthPrizes = collector.getBirthPrizes();
			for (Map.Entry<String, Prize> entry : birthPrizes.entrySet()) {
				String id = entry.getKey();
				Prize prize = entry.getValue();
				addString(result, 13, row, id);// 獎勵ID
				addNumber(result, 14, row, prize.getLevel());// 獎勵等級
				addBoolean(result, 15, row, prize.isFamous());// 顯示成名
				//
				Map<String, Integer> awards = prize.getAwards();
				for (Map.Entry<String, Integer> e : awards.entrySet()) {
					String itemId = e.getKey();
					Integer amount = e.getValue();
					addString(result, 16, row, itemId);// 道具ID
					addNumber(result, 17, row, amount);// 數量
					++row;
				}
				//
				if (awards.size() == 0) {
					++row;
				}
			}

			// 和獎
			addDef(result, 18, 0, "tiePrizes");
			addDef(result, 18, 1, LinkedHashMap.class.getName());
			addDef(result, 18, 2, String.class.getName());// key
			addHeader(result, 18, BEG_HEADER_ROW, "和獎ID");
			//
			addDef(result, 19, 2, PrizeImpl.class.getName());// value

			// 獎勵等級
			addDef(result, 19, 3, "level");
			addDef(result, 19, 4, int.class.getName());
			addHeader(result, 19, BEG_HEADER_ROW, "獎勵等級");

			// 是否成名
			addDef(result, 20, 3, "famous");
			addDef(result, 20, 4, boolean.class.getName());
			addHeader(result, 20, BEG_HEADER_ROW, "是否成名");

			// 獎勵
			addDef(result, 21, 3, "awards");
			addDef(result, 21, 4, LinkedHashMap.class.getName());
			addDef(result, 21, 5, String.class.getName());// key
			addHeader(result, 21, BEG_HEADER_ROW, "道具ID");
			//
			addDef(result, 22, 5, Integer.class.getName());// value
			addHeader(result, 22, BEG_HEADER_ROW, "數量");
			//
			row = BEG_DATA_ROW;
			Map<String, Prize> tiePrizes = collector.getTiePrizes();
			for (Map.Entry<String, Prize> entry : tiePrizes.entrySet()) {
				String id = entry.getKey();
				Prize prize = entry.getValue();
				addString(result, 18, row, id);// 獎勵ID
				addNumber(result, 19, row, prize.getLevel());// 獎勵等級
				addBoolean(result, 20, row, prize.isFamous());// 顯示成名
				//
				Map<String, Integer> awards = prize.getAwards();
				for (Map.Entry<String, Integer> e : awards.entrySet()) {
					String itemId = e.getKey();
					Integer amount = e.getValue();
					addString(result, 21, row, itemId);// 道具ID
					addNumber(result, 22, row, amount);// 數量
					++row;
				}
				//
				if (awards.size() == 0) {
					++row;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
			addHeader(result, 0, 0, "五行類別");
			addHeader(result, 1, 0, "值");
			addHeader(result, 2, 0, "數字");
			//
			addString(result, 0, 1, "金");
			addString(result, 1, 1, WuxingType.METAL);
			addNumber(result, 2, 1, WuxingType.METAL.getValue());
			//
			addString(result, 0, 2, "木");
			addString(result, 1, 2, WuxingType.WOOD);
			addNumber(result, 2, 2, WuxingType.WOOD.getValue());
			//
			addString(result, 0, 3, "土");
			addString(result, 1, 3, WuxingType.EARTH);
			addNumber(result, 2, 3, WuxingType.EARTH.getValue());
			//
			addString(result, 0, 4, "水");
			addString(result, 1, 4, WuxingType.WATER);
			addNumber(result, 2, 4, WuxingType.WATER.getValue());
			//
			addString(result, 0, 5, "火");
			addString(result, 1, 5, WuxingType.FIRE);
			addNumber(result, 2, 5, WuxingType.FIRE.getValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//
		return result;
	}

	public String writeToXml() {
		String result = null;
		//
		Workbook book = null;
		try {
			WuxingCollector collector = WuxingCollector.getInstance(false);
			File file = new File(excelName(WuxingCollector.class));
			book = Workbook.getWorkbook(file);
			//
			Sheet sheet = book.getSheet(0);
			int columns = sheet.getColumns();
			int rows = sheet.getRows();
			//
			String fieldName = null;
			String className = null;
			String fieldValue = null;

			// 說明
			fieldName = sheet.getCell(0, 0).getContents();
			className = sheet.getCell(0, 1).getContents();
			NamesBean descriptions = ClassHelper.newInstance(className);
			collector.setDescriptions(descriptions);
			//
			String namesClassName = sheet.getCell(0, 3).getContents();
			Set<LocaleNameBean> names = ClassHelper.newInstance(namesClassName);
			descriptions.setNames(names);
			//
			String localeNameClassName = sheet.getCell(1, 4).getContents();
			String localeClassName = sheet.getCell(1, 6).getContents();// locale
																		// class
			String nameClassName = sheet.getCell(2, 6).getContents();// name
																		// class
			//
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				LocaleNameBean localeNameBean = ClassHelper
						.newInstance(localeNameClassName);
				Locale locale = toObject(sheet.getCell(1, i).getContents(),
						localeClassName);
				if (locale == null) {
					continue;
				}
				//
				localeNameBean.setLocale(locale);
				//
				String name = toObject(sheet.getCell(2, i).getContents(),
						nameClassName);
				localeNameBean.setName(name);
				names.add(localeNameBean);
			}

			// 等級限制
			fieldName = sheet.getCell(3, 0).getContents();
			className = sheet.getCell(3, 1).getContents();
			fieldValue = sheet.getCell(3, BEG_DATA_ROW).getContents();
			ClassHelper.setDeclaredFieldValue(collector, fieldName,
					toObject(fieldValue, className));

			// 玩一次所花費的金幣
			fieldName = sheet.getCell(4, 0).getContents();
			className = sheet.getCell(4, 1).getContents();
			fieldValue = sheet.getCell(4, BEG_DATA_ROW).getContents();
			ClassHelper.setDeclaredFieldValue(collector, fieldName,
					toObject(fieldValue, className));

			// 花費金幣,每日可玩的次數
			fieldName = sheet.getCell(5, 0).getContents();
			className = sheet.getCell(5, 1).getContents();
			fieldValue = sheet.getCell(5, BEG_DATA_ROW).getContents();
			ClassHelper.setDeclaredFieldValue(collector, fieldName,
					toObject(fieldValue, className));

			// 玩一次所花費的道具
			fieldName = sheet.getCell(6, 0).getContents();
			className = sheet.getCell(6, 1).getContents();
			fieldValue = sheet.getCell(6, BEG_DATA_ROW).getContents();
			ClassHelper.setDeclaredFieldValue(collector, fieldName,
					toObject(fieldValue, className));

			// 玩一次所花費的儲值幣
			fieldName = sheet.getCell(7, 0).getContents();
			className = sheet.getCell(7, 1).getContents();
			fieldValue = sheet.getCell(7, BEG_DATA_ROW).getContents();
			ClassHelper.setDeclaredFieldValue(collector, fieldName,
					toObject(fieldValue, className));

			// 勝負獎
			fieldName = sheet.getCell(8, 0).getContents();
			className = sheet.getCell(8, 1).getContents();
			//
			Map<String, Prize> prizes = ClassHelper.newInstance(className);
			collector.setFinalPrizes(prizes);
			//
			String keyClassName = sheet.getCell(8, 2).getContents();// key
			String valueClassName = sheet.getCell(9, 2).getContents();// value
			//
			String levelFieldName = sheet.getCell(9, 3).getContents();
			String levelClassName = sheet.getCell(9, 4).getContents();
			//
			String famousFieldName = sheet.getCell(10, 3).getContents();
			String famousClassName = sheet.getCell(10, 4).getContents();
			//
			String awardsClassName = sheet.getCell(11, 4).getContents();
			String awardsKeyClassName = sheet.getCell(11, 5).getContents();// key
			String awardsValueClassName = sheet.getCell(12, 5).getContents();// value
			//
			Prize prize = null;
			Map<String, Integer> awards = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				// prize id
				Cell cell = sheet.getCell(8, i);
				String contents = cell.getContents();
				if (StringHelper.notBlank(contents)) {
					// 開出的獎
					prize = ClassHelper.newInstance(valueClassName);
					prize.setId(contents);

					// 獎勵等級
					String level = sheet.getCell(9, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, levelFieldName,
							toObject(level, levelClassName));

					// 是否成名
					String famous = sheet.getCell(10, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, famousFieldName,
							toObject(famous, famousClassName));

					// 獎勵
					awards = ClassHelper.newInstance(awardsClassName);
					prize.setAwards(awards);
					//
					String itemId = toObject(
							sheet.getCell(11, i).getContents(),
							awardsKeyClassName);
					Integer amount = toObject(sheet.getCell(12, i)
							.getContents(), awardsValueClassName);
					awards.put(itemId, amount);
					//
					prizes.put(prize.getId(), prize);
				} else {
					if (prize != null) {
						// 獎勵
						String itemId = toObject(sheet.getCell(11, i)
								.getContents(), awardsKeyClassName);
						if (itemId != null) {
							Integer amount = toObject(sheet.getCell(12, i)
									.getContents(), awardsValueClassName);
							awards.put(itemId, amount);
						}
					}
				}
			}

			// 生獎
			fieldName = sheet.getCell(13, 0).getContents();
			className = sheet.getCell(13, 1).getContents();
			//
			prizes = ClassHelper.newInstance(className);
			collector.setBirthPrizes(prizes);
			//
			keyClassName = sheet.getCell(13, 2).getContents();// key
			valueClassName = sheet.getCell(14, 2).getContents();// value
			//
			levelFieldName = sheet.getCell(14, 3).getContents();
			levelClassName = sheet.getCell(14, 4).getContents();
			//
			famousFieldName = sheet.getCell(15, 3).getContents();
			famousClassName = sheet.getCell(15, 4).getContents();
			//
			awardsClassName = sheet.getCell(16, 4).getContents();
			awardsKeyClassName = sheet.getCell(16, 5).getContents();// key
			awardsValueClassName = sheet.getCell(17, 5).getContents();// value
			//
			prize = null;
			awards = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				// prize id
				Cell cell = sheet.getCell(13, i);
				String contents = cell.getContents();
				if (StringHelper.notBlank(contents)) {
					// 開出的獎
					prize = ClassHelper.newInstance(valueClassName);
					prize.setId(contents);

					// 獎勵等級
					String level = sheet.getCell(14, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, levelFieldName,
							toObject(level, levelClassName));

					// 是否成名
					String famous = sheet.getCell(15, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, famousFieldName,
							toObject(famous, famousClassName));

					// 獎勵
					awards = ClassHelper.newInstance(awardsClassName);
					prize.setAwards(awards);
					//
					String itemId = toObject(
							sheet.getCell(16, i).getContents(),
							awardsKeyClassName);
					Integer amount = toObject(sheet.getCell(17, i)
							.getContents(), awardsValueClassName);
					awards.put(itemId, amount);
					//
					prizes.put(prize.getId(), prize);
				} else {
					if (prize != null) {
						// 獎勵
						String itemId = toObject(sheet.getCell(16, i)
								.getContents(), awardsKeyClassName);
						if (itemId != null) {
							Integer amount = toObject(sheet.getCell(17, i)
									.getContents(), awardsValueClassName);
							awards.put(itemId, amount);
						}
					}
				}
			}

			// 和獎
			fieldName = sheet.getCell(18, 0).getContents();
			className = sheet.getCell(18, 1).getContents();
			fieldValue = sheet.getCell(18, BEG_DATA_ROW).getContents();
			//
			prizes = ClassHelper.newInstance(className);
			collector.setTiePrizes(prizes);
			//
			keyClassName = sheet.getCell(18, 2).getContents();// key
			valueClassName = sheet.getCell(19, 2).getContents();// value
			//
			levelFieldName = sheet.getCell(19, 3).getContents();
			levelClassName = sheet.getCell(19, 4).getContents();
			//
			famousFieldName = sheet.getCell(20, 3).getContents();
			famousClassName = sheet.getCell(20, 4).getContents();
			//
			awardsClassName = sheet.getCell(21, 4).getContents();
			awardsKeyClassName = sheet.getCell(21, 5).getContents();// key
			awardsValueClassName = sheet.getCell(22, 5).getContents();// value
			//
			prize = null;
			awards = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				// prize id
				Cell cell = sheet.getCell(18, i);
				String contents = cell.getContents();
				if (StringHelper.notBlank(contents)) {
					// 開出的獎
					prize = ClassHelper.newInstance(valueClassName);
					prize.setId(contents);

					// 獎勵等級
					String level = sheet.getCell(19, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, levelFieldName,
							toObject(level, levelClassName));

					// 是否成名
					String famous = sheet.getCell(20, i).getContents();
					ClassHelper.setDeclaredFieldValue(prize, famousFieldName,
							toObject(famous, famousClassName));

					// 獎勵
					awards = ClassHelper.newInstance(awardsClassName);
					prize.setAwards(awards);
					//
					String itemId = toObject(
							sheet.getCell(21, i).getContents(),
							awardsKeyClassName);
					Integer amount = toObject(sheet.getCell(22, i)
							.getContents(), awardsValueClassName);
					awards.put(itemId, amount);
					//
					prizes.put(prize.getId(), prize);
				} else {
					if (prize != null) {
						// 獎勵
						String itemId = toObject(sheet.getCell(21, i)
								.getContents(), awardsKeyClassName);
						if (itemId != null) {
							Integer amount = toObject(sheet.getCell(22, i)
									.getContents(), awardsValueClassName);
							awards.put(itemId, amount);
						}
					}
				}
			}
			//
			result = beanCollector.writeToXml(WuxingCollector.class, collector);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (book != null) {
				book.close();
			}
		}
		//
		return result;
	}
}
