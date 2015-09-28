package org.openyu.mix.treasure.vo;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openyu.mix.treasure.vo.impl.StockImpl;
import org.openyu.mix.treasure.vo.impl.TreasureImpl;
import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.supporter.LocaleNameBeanSupporter;
import org.openyu.commons.editor.ex.EditorException;
import org.openyu.commons.editor.supporter.BaseEditorSupporter;
import org.openyu.commons.lang.BooleanHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.commons.util.LocaleHelper;

public class TreasureEditor extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static TreasureEditor treasureCollector;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 5;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public TreasureEditor() {
	}

	// --------------------------------------------------
	public synchronized static TreasureEditor getInstance() {
		return getInstance(true);
	}

	public synchronized static TreasureEditor getInstance(boolean initial) {
		if (treasureCollector == null) {
			treasureCollector = new TreasureEditor();
		}
		return treasureCollector;
	}

	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(TreasureCollector.class);
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
			TreasureCollector collector = beanCollector
					.readFromXml(TreasureCollector.class);
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
			addDef(result, col, 0, "refreshItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "刷新的道具");
			addString(result, col, BEG_DATA_ROW, collector.getRefreshItem());
			//
			col = 5;
			addDef(result, col, 0, "refreshCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "刷新的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getRefreshCoin());
			//
			col = 6;
			addDef(result, col, 0, "refreshMills");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "刷新毫秒");
			addNumber(result, col, BEG_DATA_ROW, collector.getRefreshMills());

			// 祕寶庫
			col = 7;
			addDef(result, col, 0, "stocks");
			addDef(result, col, 1, "key");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "祕寶庫ID");

			// 祕寶
			col = 8;
			addDef(result, col, 1, "treasures");
			addDef(result, col, 2, "key");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "祕寶ID");
			//
			col = 9;
			addDef(result, col, 2, "amount");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "賣的數量");
			//
			col = 10;
			addDef(result, col, 2, "famous");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "是否成名");
			//
			col = 11;
			addDef(result, col, 2, "weight");
			result.setColumnView(col, 11);
			addHeader(result, col, BEG_HEADER_ROW, "權重");
			//
			dataRow = BEG_DATA_ROW;
			Map<String, Stock> stocks = collector.getStocks();
			for (Map.Entry<String, Stock> entry : stocks.entrySet()) {
				String id = entry.getKey();
				Stock stock = entry.getValue();
				addString(result, 7, dataRow, id);// 祕寶庫ID
				//
				Map<String, Treasure> treasures = stock.getTreasures();
				for (Map.Entry<String, Treasure> e : treasures.entrySet()) {
					String treasureId = e.getKey();
					Treasure treasure = e.getValue();
					addString(result, 8, dataRow, treasureId);// 祕寶ID
					addNumber(result, 9, dataRow, treasure.getAmount());// 賣的數量
					addBoolean(result, 10, dataRow, treasure.isFamous());// 是否成名
					addNumber(result, 11, dataRow, treasure.getWeight());// 權重
					++dataRow;
				}
				//
				if (treasures.size() == 0) {
					++dataRow;
				}
			}

			// 上架祕寶庫
			col = 12;
			addDef(result, col, 0, "products");
			addDef(result, col, 1, "key");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "上架索引");
			//
			col = 13;
			addDef(result, col, 1, "value");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "上架祕寶庫ID");
			//
			dataRow = BEG_DATA_ROW;
			Map<Integer, String> products = collector.getProducts();
			for (Map.Entry<Integer, String> entry : products.entrySet()) {
				Integer index = entry.getKey();
				String stockId = entry.getValue();
				addNumber(result, 12, dataRow, index);// 索引
				addString(result, 13, dataRow, stockId);// 祕寶庫id
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
			TreasureCollector collector = TreasureCollector.getInstance(false);
			File file = new File(excelName(TreasureCollector.class));
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

			// 刷新的道具
			String refreshItem = sheet.getCell(4, BEG_DATA_ROW).getContents();
			collector.setRefreshItem(refreshItem);

			// 刷新的儲值幣
			String refreshCoin = sheet.getCell(5, BEG_DATA_ROW).getContents();
			collector.setRefreshCoin(NumberHelper.toInt(refreshCoin));

			// 刷新毫秒
			String refreshMills = sheet.getCell(6, BEG_DATA_ROW).getContents();
			collector.setRefreshMills(NumberHelper.toLong(refreshMills));

			// 祕寶庫
			Stock stock = null;
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String id = sheet.getCell(7, i).getContents();
				if (StringHelper.notBlank(id)) {
					// 祕寶庫
					stock = new StockImpl(id);
					// 祕寶
					String treasureId = sheet.getCell(8, i).getContents();
					Treasure treasure = new TreasureImpl(treasureId);

					// 賣的數量
					String amount = sheet.getCell(9, i).getContents();
					treasure.setAmount(NumberHelper.toInt(amount));

					// 是否成名
					String famous = sheet.getCell(10, i).getContents();
					treasure.setFamous(BooleanHelper.toBoolean(famous));

					// 權重
					String weight = sheet.getCell(11, i).getContents();
					treasure.setWeight(NumberHelper.toInt(weight));
					//
					stock.getTreasures().put(treasure.getId(), treasure);
					//
					collector.getStocks().put(stock.getId(), stock);
				} else {
					if (stock != null) {
						// 獎勵
						String treasureId = sheet.getCell(8, i).getContents();
						if (StringHelper.notBlank(treasureId)) {
							// 祕寶
							Treasure treasure = new TreasureImpl(treasureId);

							// 賣的數量
							String amount = sheet.getCell(9, i).getContents();
							treasure.setAmount(NumberHelper.toInt(amount));

							// 是否成名
							String famous = sheet.getCell(10, i).getContents();
							treasure.setFamous(BooleanHelper.toBoolean(famous));

							// 權重
							String weight = sheet.getCell(11, i).getContents();
							treasure.setWeight(NumberHelper.toInt(weight));
							//
							stock.getTreasures()
									.put(treasure.getId(), treasure);
						}
					}
				}
			}

			// 上架祕寶庫
			for (int i = BEG_DATA_ROW; i < rows; i++) {
				String index = sheet.getCell(12, i).getContents();
				if (StringHelper.notBlank(index)) {
					String stockId = sheet.getCell(13, i).getContents();
					collector.getProducts().put(NumberHelper.toInt(index),
							stockId);
				}
			}
			//
			result = beanCollector.writeToXml(TreasureCollector.class,
					collector);
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		//
		return result;
	}
}
