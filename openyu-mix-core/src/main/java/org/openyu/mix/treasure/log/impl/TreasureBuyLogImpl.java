package org.openyu.mix.treasure.log.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.item.po.bridge.ItemBridge;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.po.bridge.BuyTypeBridge;
import org.openyu.mix.treasure.po.bridge.TreasureBridge;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "treasure_buy_log", indexes = {
		@Index(name = "treasure_buy_log_role_id_log_date_buy_type", columnList = "role_id,log_date,buy_type") })
@SequenceGenerator(name = "sg_treasure_buy_log", sequenceName = "seq_treasure_buy_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.treasure.log.impl.TreasureBuyLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class TreasureBuyLogImpl extends AppLogEntitySupporter implements TreasureBuyLog {

	private static final long serialVersionUID = -4862371320004172091L;

	private Long seq;

	/**
	 * 購買類別
	 */
	private BuyType buyType;

	/**
	 * 上架祕寶索引
	 */
	private Integer gridIndex;

	/**
	 * 上架祕寶
	 */
	private Treasure treasure;

	/**
	 * 購買的道具
	 */
	private Item item;

	/**
	 * 數量
	 */
	private Integer amount;

	/**
	 * 金幣價格
	 */
	private Long price;

	/**
	 * 儲值幣價格
	 */
	private Integer coin;

	/**
	 * 消耗的金幣
	 */
	private Long spendGold;

	/**
	 * 消耗的儲值幣
	 */
	private Integer spendCoin;

	public TreasureBuyLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_treasure_buy_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "buy_type", length = 13)
	@Type(type = "org.openyu.mix.treasure.po.usertype.BuyTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = BuyTypeBridge.class)
	public BuyType getBuyType() {
		return buyType;
	}

	public void setBuyType(BuyType buyType) {
		this.buyType = buyType;
	}

	@Column(name = "grid_index")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getGridIndex() {
		return gridIndex;
	}

	public void setGridIndex(Integer gridIndex) {
		this.gridIndex = gridIndex;
	}

	@Column(name = "treasure", length = 1024)
	@Type(type = "org.openyu.mix.treasure.po.usertype.TreasureUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = TreasureBridge.class)
	public Treasure getTreasure() {
		return treasure;
	}

	public void setTreasure(Treasure treasure) {
		this.treasure = treasure;
	}

	@Column(name = "item", length = 1024)
	@Type(type = "org.openyu.mix.item.po.usertype.ItemUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemBridge.class)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "amount")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "price")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Column(name = "coin")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	@Column(name = "spend_gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getSpendGold() {
		return spendGold;
	}

	public void setSpendGold(Long spendGold) {
		this.spendGold = spendGold;
	}

	@Column(name = "spend_coin")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getSpendCoin() {
		return spendCoin;
	}

	public void setSpendCoin(Integer spendCoin) {
		this.spendCoin = spendCoin;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("buyType", buyType);
		builder.append("gridIndex", gridIndex);
		builder.append("treasure", (treasure != null ? treasure.getId() : null));
		builder.append("item", (item != null ? item.getId() : null));
		builder.append("amount", amount);
		builder.append("price", price);
		builder.append("coin", coin);
		builder.append("spendGold", spendGold);
		builder.append("spendCoin", spendCoin);
		return builder.toString();
	}

	public Object clone() {
		TreasureBuyLogImpl copy = null;
		copy = (TreasureBuyLogImpl) super.clone();
		copy.treasure = clone(treasure);
		copy.item = clone(item);
		return copy;
	}

}
