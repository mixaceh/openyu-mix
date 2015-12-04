package org.openyu.mix.treasure.vo;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.treasure.vo.impl.TreasureImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.mix.treasure.vo.impl.StockImpl;

public class TreasureCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		TreasureCollector collector = TreasureCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
		// 說明
		collector.setDescription("每隔一段時間就會隨機刷新寶物庫，出現各種不同的祕寶，有提升經驗，技魂，金幣，聲望等提昇實力道具。");
		collector.getDescriptions().addName(Locale.SIMPLIFIED_CHINESE,
				"每隔一段时间就会随机刷新宝物库，出现各种不同的秘宝，有提升经验，技魂，金币，声望等提升实力道具。");

		// 等級限制
		collector.setLevelLimit(20);
		// 刷新的道具,數量為1
		collector.setRefreshItem("T_TREASURE_REFRESH_G001");
		// 刷新的儲值幣
		collector.setRefreshCoin(50);
		// 刷新毫秒
		collector.setRefreshMills(6 * 60 * 60 * 1000L);// 6hr

		// 所有祕寶庫
		// 祕寶庫
		Stock stock = new StockImpl(ThingType.ROLE_EXP + "_001");
		// 祕寶
		Treasure treasure = new TreasureImpl("T_ROLE_EXP_G001");
		treasure.setStockId(stock.getId());
		treasure.setAmount(3);
		treasure.setFamous(false);
		treasure.setWeight(4);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_EXP_G002");
		treasure.setStockId(stock.getId());
		treasure.setAmount(2);
		treasure.setFamous(false);
		treasure.setWeight(2);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_EXP_G003");
		treasure.setStockId(stock.getId());
		treasure.setAmount(1);
		treasure.setFamous(true);
		treasure.setWeight(1);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		// 祕寶庫
		collector.getStocks().put(stock.getId(), stock);
		// 所有上架祕寶庫
		collector.getProducts().put(0, stock.getId());

		// 祕寶庫
		stock = new StockImpl(ThingType.ROLE_GOLD + "_001");
		treasure = new TreasureImpl("T_ROLE_GOLD_G001");
		treasure.setStockId(stock.getId());
		treasure.setAmount(3);
		treasure.setFamous(false);
		treasure.setWeight(4);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_GOLD_G002");
		treasure.setStockId(stock.getId());
		treasure.setAmount(2);
		treasure.setFamous(false);
		treasure.setWeight(2);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_GOLD_G003");
		treasure.setStockId(stock.getId());
		treasure.setAmount(1);
		treasure.setFamous(true);
		treasure.setWeight(1);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		// 祕寶庫
		collector.getStocks().put(stock.getId(), stock);
		// 所有上架祕寶庫
		collector.getProducts().put(1, stock.getId());

		// 祕寶庫
		stock = new StockImpl(ThingType.ROLE_FAME + "_001");
		// 祕寶
		treasure = new TreasureImpl("T_ROLE_FAME_G001");
		treasure.setStockId(stock.getId());
		treasure.setAmount(3);
		treasure.setFamous(false);
		treasure.setWeight(4);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_FAME_G002");
		treasure.setStockId(stock.getId());
		treasure.setAmount(2);
		treasure.setFamous(false);
		treasure.setWeight(2);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_FAME_G003");
		treasure.setStockId(stock.getId());
		treasure.setAmount(1);
		treasure.setFamous(true);
		treasure.setWeight(1);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		// 祕寶庫
		collector.getStocks().put(stock.getId(), stock);
		// 所有上架祕寶
		collector.getProducts().put(2, stock.getId());

		// 祕寶庫
		stock = new StockImpl(ThingType.ROLE_SP + "_001");
		// 祕寶
		treasure = new TreasureImpl("T_ROLE_SP_G001");
		treasure.setStockId(stock.getId());
		treasure.setAmount(3);
		treasure.setFamous(false);
		treasure.setWeight(4);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_SP_G002");
		treasure.setStockId(stock.getId());
		treasure.setAmount(2);
		treasure.setFamous(false);
		treasure.setWeight(2);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		//
		treasure = new TreasureImpl("T_ROLE_SP_G003");
		treasure.setStockId(stock.getId());
		treasure.setAmount(1);
		treasure.setFamous(true);
		treasure.setWeight(1);// 權重
		stock.getTreasures().put(treasure.getId(), treasure);
		// 祕寶庫
		collector.getStocks().put(stock.getId(), stock);
		// 所有上架祕寶
		collector.getProducts().put(3, stock.getId());
		//
		result = collector.writeToXml(TreasureCollector.class, collector);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		TreasureCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(TreasureCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		long beg = System.currentTimeMillis();
		//
		result = beanCollector.writeToSerFromXml(TreasureCollector.class);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		TreasureCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(TreasureCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 399 mills.
	// 1000000 times: 398 mills.
	// 1000000 times: 401 mills.
	public void initialize() {
		boolean result = false;
		//
		TreasureCollector collector = TreasureCollector.getInstance();
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		result = collector.isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(collector.getStocks());
		System.out.println(collector.getProducts());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		TreasureCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = TreasureCollector.getInstance();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 10000 times: 1587 mills.
	// 10000 times: 1583 mills.
	// 10000 times: 1683 mills.
	public void reset() {
		TreasureCollector collector = TreasureCollector.getInstance();
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
		//
		assertEquals(0, collector.getStocks().size());
		assertEquals(0, collector.getProducts().size());
	}

	@Test
	/**
	 * 依照權重隨機從祕寶庫中取得祕寶
	 */
	// 1000000 times: 561 mills.
	// 1000000 times: 575 mills.
	// 1000000 times: 558 mills.
	public void randomTreasure() {
		TreasureCollector collector = TreasureCollector.getInstance();
		//
		Treasure result = null;
		//
		int count = 1000000;// 100w
		long beg = System.currentTimeMillis();
		// 祕寶庫id
		String stockId = "ROLE_EXP_001";
		// 輸出結果<treasuerId,times>
		Map<String, Integer> outputs = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < count; i++) {
			result = collector.randomTreasure(stockId);
			if (result != null) {
				Integer times = outputs.get(result.getId());
				if (times == null) {
					times = 0;
				}
				times += 1;
				outputs.put(result.getId(), times);
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);

		// 祕寶庫
		Stock stock = collector.getStocks().get(stockId);
		// 輸出結果
		int total = 0;
		for (Map.Entry<String, Integer> entry : outputs.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();
			Treasure treasure = stock.getTreasures().get(key);
			System.out.println(key + ", " + treasure.getProbability() + ", " + value);
			total += value;
		}
		System.out.println(total);
		assertEquals(count, total);
	}

	@Test
	// 1000000 times: 2563 mills.
	// 1000000 times: 2501 mills.
	// 1000000 times: 2606 mills.
	public void randomTreasures() {
		TreasureCollector collector = TreasureCollector.getInstance();
		//
		Map<Integer, Treasure> result = null;
		//
		int count = 1000000;// 100w
		long beg = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = collector.randomTreasures();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertTrue(result.size() > 0);
	}

}
