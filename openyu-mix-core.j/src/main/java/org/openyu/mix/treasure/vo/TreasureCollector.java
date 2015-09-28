package org.openyu.mix.treasure.vo;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.treasure.vo.adapter.StringStockXmlAdapter;
import org.openyu.mix.treasure.vo.impl.StockImpl;
import org.openyu.mix.treasure.vo.impl.TreasureImpl;
import org.openyu.commons.bean.BeanHelper;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.adapter.NamesBeanXmlAdapter;
import org.openyu.commons.bean.supporter.NamesBeanSupporter;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1.處理靜態資料,放的是vo的資料,並非po資料
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "treasureCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class TreasureCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -7165929460058525006L;

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TreasureCollector.class);

	private static TreasureCollector treasureCollector;

	/**
	 * 說明
	 */
	@XmlJavaTypeAdapter(NamesBeanXmlAdapter.class)
	private NamesBean descriptions = new NamesBeanSupporter();

	/**
	 * 等級限制
	 */
	private int levelLimit = 20;

	// --------------------------------------------------
	// 刷新
	// 先扣道具,若不足再扣儲值幣
	// --------------------------------------------------
	/**
	 * 刷新的道具,數量為1
	 */
	private String refreshItem = "T_TREASURE_REFRESH_G001";// 祕寶刷新石

	/**
	 * 刷新的儲值幣
	 */
	private int refreshCoin = 50;

	/**
	 * 刷新毫秒
	 */
	private long refreshMills = 6 * 60 * 60 * 1000L;// 6hr

	/**
	 * 祕寶庫
	 * 
	 * <祕寶庫id,祕寶庫>
	 */
	@XmlJavaTypeAdapter(StringStockXmlAdapter.class)
	private Map<String, Stock> stocks = new LinkedHashMap<String, Stock>();

	/**
	 * 上架祕寶庫
	 * 
	 * <index,祕寶庫id>
	 */
	private Map<Integer, String> products = new LinkedHashMap<Integer, String>();

	// --------------------------------------------------
	public TreasureCollector() {
		setId(TreasureCollector.class.getName());
	}

	public synchronized static TreasureCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static TreasureCollector getInstance(boolean initial) {
		if (treasureCollector == null) {
			treasureCollector = new TreasureCollector();
			if (initial) {
				treasureCollector.initialize();
			}
			// 此有系統預設值,只是為了轉出xml,並非給企劃編輯用
		}
		return treasureCollector;
	}

	/**
	 * 初始化
	 * 
	 */
	public void initialize() {
		if (!treasureCollector.isInitialized()) {
			treasureCollector = readFromSer(TreasureCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (treasureCollector == null) {
				treasureCollector = new TreasureCollector();
			}

			// 處理treasure設定stockId
			treasureCollector.stocks = buildStocks();
			treasureCollector.setInitialized(true);
		}
	}

	public void clear() {
		stocks.clear();
		products.clear();
		// 設為可初始化
		setInitialized(false);
	}

	// --------------------------------------------------
	/**
	 * 內部建構,處理treasure設定stockId
	 * 
	 * @return
	 */
	protected Map<String, Stock> buildStocks() {
		Map<String, Stock> result = new LinkedHashMap<String, Stock>();
		for (Map.Entry<String, Stock> entry : treasureCollector.stocks
				.entrySet()) {
			String stockId = entry.getKey();
			Stock stock = entry.getValue();
			for (Treasure treasure : stock.getTreasures().values()) {
				treasure.setStockId(stockId);
			}
			//
			result.put(stockId, stock);
		}
		return result;
	}

	/**
	 * 說明
	 * 
	 * @return
	 */
	public String getDescription() {
		return descriptions.getName();
	}

	public void setDescription(String description) {
		this.descriptions.setName(description);
	}

	public NamesBean getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(NamesBean descriptions) {
		this.descriptions = descriptions;
	}

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public String getRefreshItem() {
		return refreshItem;
	}

	public void setRefreshItem(String refreshItem) {
		this.refreshItem = refreshItem;
	}

	public int getRefreshCoin() {
		return refreshCoin;
	}

	public void setRefreshCoin(int refreshCoin) {
		this.refreshCoin = refreshCoin;
	}

	public long getRefreshMills() {
		return refreshMills;
	}

	public void setRefreshMills(long refreshMills) {
		this.refreshMills = refreshMills;
	}

	public Map<String, Stock> getStocks() {
		if (stocks == null) {
			stocks = new LinkedHashMap<String, Stock>();
		}
		return stocks;
	}

	public void setStocks(Map<String, Stock> stocks) {
		this.stocks = stocks;
	}

	public Map<Integer, String> getProducts() {
		if (products == null) {
			products = new LinkedHashMap<Integer, String>();
		}
		return products;
	}

	public void setProducts(Map<Integer, String> products) {
		this.products = products;
	}

	// --------------------------------------------------

	public Stock getStock(String id) {
		Stock result = null;
		if (id != null) {
			result = stocks.get(id);
		}
		return (result != null ? (Stock) result.clone() : null);
	}

	public Stock createStock() {
		return createStock(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的stock,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 *            祕寶庫id
	 * @return
	 */
	public Stock createStock(String id) {
		Stock result = null;
		// 來自靜態資料的clone副本
		result = getStock(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new StockImpl(id);
		}
		return result;
	}

	/**
	 * 祕寶庫是否存在
	 * 
	 * @param id
	 *            祕寶庫id
	 * @return
	 */
	public boolean containStock(String id) {
		boolean result = false;
		if (id != null) {
			result = stocks.containsKey(id);
		}
		return result;
	}

	/**
	 * 祕寶庫是否存在
	 * 
	 * @param stock
	 * @return
	 */
	public boolean containStock(Stock stock) {
		boolean result = false;
		if (stock != null) {
			result = stocks.containsKey(stock.getId());
		}
		return result;
	}

	/**
	 * 取得所有祕寶庫id
	 * 
	 * @return
	 */
	public List<String> getStockIds() {
		List<String> result = new LinkedList<String>();
		for (String id : stocks.keySet()) {
			result.add(id);
		}
		return result;
	}

	// --------------------------------------------------

	public Treasure getTreasure(String stockId, String treasureId) {
		Treasure result = null;
		if (stockId != null) {
			Stock stock = stocks.get(stockId);
			if (stock != null) {
				result = stock.getTreasures().get(treasureId);
			}
		}
		return (result != null ? (Treasure) result.clone() : null);
	}

	public Treasure createTreasure() {
		return createTreasure(null, null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的stock,使用clone/或用建構new xxx()
	 * 
	 * @param stockId
	 *            祕寶庫id
	 * @param treasureId
	 *            祕寶id
	 * @return
	 */
	public Treasure createTreasure(String stockId, String treasureId) {
		Treasure result = null;
		// 來自靜態資料的clone副本
		result = getTreasure(stockId, treasureId);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new TreasureImpl(treasureId);
		}
		return result;
	}

	/**
	 * 祕寶是否存在
	 * 
	 * @param stockId
	 *            祕寶庫id
	 * @param treasureId
	 *            祕寶id
	 * @return
	 */
	public boolean containTreasure(String stockId, String treasureId) {
		boolean result = false;
		if (stockId != null) {
			Stock stock = stocks.get(stockId);
			if (stock != null) {
				result = stock.getTreasures().containsKey(treasureId);
			}
		}
		return result;
	}

	/**
	 * 祕寶是否存在
	 * 
	 * @param treasure
	 * @return
	 */
	public boolean containTreasure(Treasure treasure) {
		boolean result = false;
		if (treasure != null) {
			Stock stock = stocks.get(treasure.getStockId());
			if (stock != null) {
				result = stock.getTreasures().containsKey(treasure.getId());
			}
		}
		return result;
	}

	/**
	 * 取得祕寶庫所有祕寶id
	 * 
	 * @return
	 */
	public List<String> getTreasureIds(String stockId) {
		List<String> result = new LinkedList<String>();
		Stock stock = stocks.get(stockId);
		if (stock != null) {
			for (String id : stock.getTreasures().keySet()) {
				result.add(id);
			}
		}
		return result;
	}

	// --------------------------------------------------
	/**
	 * 依照權重,隨機從祕寶庫中建構祕寶
	 * 
	 * @param stockId
	 * @return
	 */
	public Treasure randomTreasure(String stockId) {
		Treasure result = null;
		if (stockId != null) {
			// 祕寶庫
			Stock stock = stocks.get(stockId);
			if (stock != null) {
				int sum = BeanHelper.sumOf(stock.getTreasures());
				result = BeanHelper.randomOf(stock.getTreasures(), sum);
			}
		}
		//
		return (result != null ? (Treasure) result.clone() : null);
	}

	/**
	 * 隨機上架祕寶
	 * 
	 * @return
	 */
	public Map<Integer, Treasure> randomTreasures() {
		Map<Integer, Treasure> result = new LinkedHashMap<Integer, Treasure>();
		for (Map.Entry<Integer, String> entry : products.entrySet()) {
			Integer index = entry.getKey();
			String stockId = entry.getValue();
			Treasure treasure = randomTreasure(stockId);
			if (treasure != null) {
				result.put(index, treasure);
			}
		}
		//
		return result;
	}
}
