package org.openyu.mix.manor.log.impl;

import java.util.LinkedList;
import java.util.List;

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
import org.openyu.mix.item.po.bridge.ItemListBridge;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.mix.manor.po.bridge.CultureTypeBridge;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Seed;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "manor_seed_log", indexes = {
		@Index(name = "manor_seed_log_role_id_log_date_culture_type", columnList = "role_id,log_date,culture_type") })
@SequenceGenerator(name = "sg_manor_seed_log", sequenceName = "seq_manor_seed_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.manor.log.impl.ManorSeedLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class ManorSeedLogImpl extends AppLogEntitySupporter implements ManorSeedLog {
	private static final long serialVersionUID = -8609168204892772151L;

	private Long seq;

	/**
	 * 耕種類別
	 */
	private CultureType cultureType;

	private Integer farmIndex;

	private Integer gridIndex;

	/**
	 * 耕種的種子
	 */
	private Seed seed;

	/**
	 * 消耗的道具
	 */
	private List<Item> spendItems = new LinkedList<Item>();

	/**
	 * 消耗的儲值幣
	 */
	private Integer spendCoin;

	public ManorSeedLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_manor_seed_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "culture_type", length = 13)
	@Type(type = "org.openyu.mix.manor.po.userType.CultureTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = CultureTypeBridge.class)
	public CultureType getCultureType() {
		return cultureType;
	}

	public void setCultureType(CultureType cultureType) {
		this.cultureType = cultureType;
	}

	@Column(name = "farm_index")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getFarmIndex() {
		return farmIndex;
	}

	public void setFarmIndex(Integer farmIndex) {
		this.farmIndex = farmIndex;
	}

	@Column(name = "grid_index")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getGridIndex() {
		return gridIndex;
	}

	public void setGridIndex(Integer gridIndex) {
		this.gridIndex = gridIndex;
	}

	@Column(name = "seed", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemBridge.class)
	public Seed getSeed() {
		return seed;
	}

	public void setSeed(Seed seed) {
		this.seed = seed;
	}

	@Column(name = "spend_items", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemListUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemListBridge.class)
	public List<Item> getSpendItems() {
		return spendItems;
	}

	public void setSpendItems(List<Item> spendItems) {
		this.spendItems = spendItems;
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
		builder.append("cultureType", cultureType);
		builder.append("farmIndex", farmIndex);
		builder.append("gridIndex", gridIndex);
		builder.append("seed", seed);
		builder.append("spendItems", spendItems);
		builder.append("spendCoin", spendCoin);
		return builder.toString();
	}

	public Object clone() {
		ManorSeedLogImpl copy = null;
		copy = (ManorSeedLogImpl) super.clone();
		copy.seed = clone(seed);
		copy.spendItems = clone(spendItems);
		return copy;
	}
}
