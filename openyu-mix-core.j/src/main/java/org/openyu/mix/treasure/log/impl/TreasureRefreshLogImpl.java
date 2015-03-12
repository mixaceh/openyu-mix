package org.openyu.mix.treasure.log.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.item.po.bridge.ItemListBridge;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.mix.treasure.po.bridge.IntegerTreasureBridge;
import org.openyu.mix.treasure.vo.Treasure;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_treasure_refresh_log")
@SequenceGenerator(name = "mix_treasure_refresh_log_g", sequenceName = "mix_treasure_refresh_log_s", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.treasure.log.impl.TreasureRefreshLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_treasure_refresh_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_treasure_refresh_log_1", columnNames = {
		"role_id", "log_date" }) })
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class TreasureRefreshLogImpl extends AppLogEntitySupporter implements TreasureRefreshLog
{

	private static final long serialVersionUID = 2525395382597962530L;

	private Long seq;

	/**
	 * 刷新時間
	 */
	private Long refreshTime;

	/**
	 * 上架祕寶
	 */
	private Map<Integer, Treasure> treasures = new LinkedHashMap<Integer, Treasure>();

	/**
	 * 消耗的道具
	 */
	private List<Item> spendItems = new LinkedList<Item>();

	/**
	 * 消耗的儲值幣
	 */
	private Integer spendCoin;

	public TreasureRefreshLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_treasure_refresh_log_g")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "refresh_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getRefreshTime()
	{
		return refreshTime;
	}

	public void setRefreshTime(Long refreshTime)
	{
		this.refreshTime = refreshTime;
	}

	@Column(name = "treasures", length = 1024)
	@Type(type = "org.openyu.mix.treasure.po.userType.IntegerTreasureUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = IntegerTreasureBridge.class)
	public Map<Integer, Treasure> getTreasures()
	{
		return treasures;
	}

	public void setTreasures(Map<Integer, Treasure> treasures)
	{
		this.treasures = treasures;
	}

	@Column(name = "spend_items", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemListUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemListBridge.class)
	public List<Item> getSpendItems()
	{
		return spendItems;
	}

	public void setSpendItems(List<Item> spendItems)
	{
		this.spendItems = spendItems;
	}

	@Column(name = "spend_coin")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getSpendCoin()
	{
		return spendCoin;
	}

	public void setSpendCoin(Integer spendCoin)
	{
		this.spendCoin = spendCoin;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("refreshTime", refreshTime);
		builder.append("treasures", treasures);
		builder.append("spendItems", spendItems);
		builder.append("spendCoin", spendCoin);
		return builder.toString();
	}

	public Object clone()
	{
		TreasureRefreshLogImpl copy = null;
		copy = (TreasureRefreshLogImpl) super.clone();
		copy.treasures = clone(treasures);
		copy.spendItems = clone(spendItems);
		return copy;
	}
}
