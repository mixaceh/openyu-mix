package org.openyu.mix.manor.vo;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.editor.ex.EditorException;
import org.openyu.commons.editor.supporter.BaseEditorSupporter;

public class ManorEditor extends BaseEditorSupporter {

	private static final long serialVersionUID = -2518918020537864166L;

	private static ManorEditor manorEditor;

	/**
	 * 起始標題列號
	 */
	private static final int BEG_HEADER_ROW = 5;

	/**
	 * 起始資料列號
	 */
	private static final int BEG_DATA_ROW = BEG_HEADER_ROW + 1;

	public ManorEditor() {
	}

	// --------------------------------------------------
	public synchronized static ManorEditor getInstance() {
		return getInstance(true);
	}

	public synchronized static ManorEditor getInstance(boolean initial) {
		if (manorEditor == null) {
			manorEditor = new ManorEditor();
		}
		return manorEditor;
	}

	public String writeToExcel() {
		String result = null;
		//
		WritableWorkbook workBook = null;
		try {
			String excelName = excelName(ManorCollector.class);
			File file = new File(excelName);
			workBook = Workbook.createWorkbook(file);
			// 資料
			writeToSheet_0(workBook);
			// 常數
			writeToSheet_1(workBook);
			// 種子
			writeToSheet_2(workBook);
			// 土地
			writeToSheet_3(workBook);
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
			ManorCollector collector = beanCollector
					.readFromXml(ManorCollector.class);
			//
			result = workbook.createSheet("資料", 0);
			int dataRow = BEG_DATA_ROW;

			// 說明
			int col = 0;
			addDef(result, col, 0, "descriptions");
			addDef(result, col, 1, "idx");
			result.setColumnView(0, 5);
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
			addDef(result, col, 0, "reclaimGold");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "開墾的金幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getReclaimGold());
			//
			col = 4;
			addDef(result, col, 0, "disuseGold");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "休耕的金幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getDisuseGold());
			//
			col = 5;
			addDef(result, col, 0, "waterItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "澆水的道具");
			addString(result, col, BEG_DATA_ROW, collector.getWaterItem());
			//
			col = 6;
			addDef(result, col, 0, "waterCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "澆水的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getWaterCoin());
			//
			col = 7;
			addDef(result, col, 0, "waterRate");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "澆水效果,增加成長速度(萬分位)");
			addNumber(result, col, BEG_DATA_ROW, collector.getWaterRate());
			//
			col = 8;
			addDef(result, col, 0, "prayItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "祈禱的道具");
			addString(result, col, BEG_DATA_ROW, collector.getPrayItem());
			//
			col = 9;
			addDef(result, col, 0, "prayCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "祈禱的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getPrayCoin());
			//
			col = 10;
			addDef(result, col, 0, "prayRate");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "祈禱效果,減少成長毫秒(萬分位)");
			addNumber(result, col, BEG_DATA_ROW, collector.getPrayRate());
			//
			col = 11;
			addDef(result, col, 0, "speedItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "加速的道具");
			addString(result, col, BEG_DATA_ROW, collector.getSpeedItem());
			//
			col = 12;
			addDef(result, col, 0, "speedCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "加速的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getSpeedCoin());
			//
			col = 13;
			addDef(result, col, 0, "reviveItem");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "復活的道具");
			addString(result, col, BEG_DATA_ROW, collector.getReviveItem());
			//
			col = 14;
			addDef(result, col, 0, "reviveCoin");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "復活的儲值幣");
			addNumber(result, col, BEG_DATA_ROW, collector.getReviveCoin());
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
		} catch (Exception ex) {
			throw new EditorException(ex);
		}
		//
		return result;
	}

	/**
	 * 工作表 2
	 * 
	 * @param workbook
	 * @return
	 */
	protected WritableSheet writeToSheet_2(WritableWorkbook workbook) {
		WritableSheet result = null;
		try {
			ManorCollector collector = ManorCollector.getInstance(false);
			collector = collector.readFromXml(ManorCollector.class);
			//
			result = workbook.createSheet("種子", 2);
			int dataRow = BEG_DATA_ROW;

			// 種子
			int col = 0;
			addDef(result, col, 0, "seeds");
			addDef(result, col, 1, "key");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "種子ID");

			// 名稱
			col = 1;
			addDef(result, col, 0, "names");
			addDef(result, col, 1, "idx");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "索引");
			//
			col = 2;
			addDef(result, col, 1, "locale");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "語系");
			//
			col = 3;
			addDef(result, col, 1, "name");
			result.setColumnView(col, 25);
			addHeader(result, col, BEG_HEADER_ROW, "名稱");

			// 說明
			col = 4;
			addDef(result, col, 0, "descriptions");
			addDef(result, col, 1, "idx");
			result.setColumnView(col, 5);
			addHeader(result, col, BEG_HEADER_ROW, "索引");
			//
			col = 5;
			addDef(result, col, 1, "locale");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "語系");
			//
			col = 6;
			addDef(result, col, 1, "name");
			result.setColumnView(col, 25);
			addHeader(result, col, BEG_HEADER_ROW, "說明");

			//
			col = 7;
			addDef(result, col, 0, "itemType");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "道具類別");
			//
			col = 8;
			addDef(result, col, 0, "maxAmount");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "最大堆疊數量,0=無限");
			//
			col = 9;
			addDef(result, col, 0, "price");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "金幣價格");
			//
			col = 10;
			addDef(result, col, 0, "coin");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "儲值幣價格");
			//
			col = 11;
			addDef(result, col, 0, "tied");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "是否綁定(束缚)");
			//
			col = 12;
			addDef(result, col, 0, "iconId");
			result.setColumnView(col, 20);
			addHeader(result, col, BEG_HEADER_ROW, "圖標ID");
			//
			col = 13;
			addDef(result, col, 0, "levelLimit");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "等級限制");
			//
			col = 14;
			addDef(result, col, 0, "growMills");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "成長毫秒,未足時間無法收割");
			//
			col = 15;
			addDef(result, col, 0, "reapMills");
			result.setColumnView(col, 10);
			addHeader(result, col, BEG_HEADER_ROW, "收割毫秒,超過時間會枯萎");
			//
			Map<String, Seed> seeds = collector.getSeeds();
			dataRow = BEG_DATA_ROW;
			for (Map.Entry<String, Seed> entry : seeds.entrySet()) {
				String seedId = entry.getKey();
				Seed seed = entry.getValue();
				//
				addString(result, 0, dataRow, seedId);// 種子ID
				//
				addString(result, 7, dataRow, seed.getItemType());// 道具類別
				addNumber(result, 8, dataRow, seed.getMaxAmount());// 最大堆疊數量
				addNumber(result, 9, dataRow, seed.getPrice());// 金幣價格
				addNumber(result, 10, dataRow, seed.getCoin());// 儲值幣價格
				//
				addBoolean(result, 11, dataRow, seed.isTied());// 是否綁定(束缚)
				addString(result, 12, dataRow, seed.getIconId());// 圖標ID
				addNumber(result, 13, dataRow, seed.getLevelLimit());// 等級限制
				addNumber(result, 14, dataRow, seed.getGrowMills());// 成長毫秒,未足時間無法收割
				addNumber(result, 15, dataRow, seed.getReapMills());// 收割毫秒,超過時間會枯萎
				// 名稱
				int idx = 0;
				Set<LocaleNameBean> names = seed.getNames();
				for (LocaleNameBean e : names) {
					Locale locale = e.getLocale();
					String name = e.getName();
					addNumber(result, 1, dataRow, idx++);// 索引
					addString(result, 2, dataRow, locale);// 語系
					addString(result, 3, dataRow, name);// 名稱
					//
					++dataRow;
				}
				//
				if (names.size() == 0) {
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
	 * 工作表 3
	 * 
	 * @param workbook
	 * @return
	 */
	protected WritableSheet writeToSheet_3(WritableWorkbook workbook) {
		WritableSheet result = null;
		try {
			ManorCollector collector = ManorCollector.getInstance(false);
			collector = collector.readFromXml(ManorCollector.class);
			//
			result = workbook.createSheet("土地", 3);
			int dataRow = BEG_DATA_ROW;

			int col = 0;
			addDef(result, col, 0, "maxEnhanceLevel");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "最高強化等級");
			addNumber(result, col, BEG_DATA_ROW, collector.getMaxEnhanceLevel()
					.getValue());
			//
			col = 1;
			addDef(result, col, 0, "safeEnhanceLevel");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "安全強化等級");
			addNumber(result, col, BEG_DATA_ROW, collector
					.getSafeEnhanceLevel().getValue());
			//
			col = 2;
			addDef(result, col, 0, "fantasyEnhanceLevel");
			result.setColumnView(col, 15);
			addHeader(result, col, BEG_HEADER_ROW, "幻想強化等級");
			addNumber(result, col, BEG_DATA_ROW, collector
					.getFantasyEnhanceLevel().getValue());
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
			// TODO Auto-generated method stub
		} catch (Exception ex) {
			throw new EditorException(ex);
		} finally {
			close(workBook);
		}
		return result;
	}
}
