package org.openyu.mix.manor.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.item.vo.supporter.ItemSupporter;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.item.vo.ItemType;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "seed")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedImpl extends ItemSupporter implements Seed {

	private static final long serialVersionUID = 3262630268855737748L;

	/**
	 * 等級限制
	 */
	private int levelLimit;

	/**
	 * 成長毫秒,未足時間無法收割
	 */
	private long growMills;

	/**
	 * 種植時間(玩家)
	 */
	@XmlTransient
	private long plantTime;

	/**
	 * 澆水時間(玩家)
	 */
	@XmlTransient
	private long waterTime;

	/**
	 * 祈禱時間(玩家)
	 */
	@XmlTransient
	private long prayTime;

	/**
	 * 收割毫秒,超過時間會枯萎
	 */
	private long reapMills;

	/**
	 * 成熟時間(玩家)
	 */
	@XmlTransient
	private long matureTime;

	/**
	 * 成熟類別(玩家)
	 */
	@XmlTransient
	private MatureType matureType = MatureType.PENDING;

	/**
	 * 產量,產出
	 * 
	 * <道具id,數量>
	 */
	private Map<String, Integer> products = new LinkedHashMap<String, Integer>();

	public SeedImpl(String id) {
		setId(id);
		setItemType(ItemType.SEED);
	}

	public SeedImpl() {
		this(null);
	}

	public int getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public long getGrowMills() {
		return growMills;
	}

	public void setGrowMills(long growMills) {
		this.growMills = growMills;
	}

	public long getPlantTime() {
		return plantTime;
	}

	public void setPlantTime(long plantTime) {
		this.plantTime = plantTime;
	}

	public long getWaterTime() {
		return waterTime;
	}

	public void setWaterTime(long waterTime) {
		this.waterTime = waterTime;
	}

	public long getPrayTime() {
		return prayTime;
	}

	public void setPrayTime(long prayTime) {
		this.prayTime = prayTime;
	}

	public long getReapMills() {
		return reapMills;
	}

	public void setReapMills(long reapMills) {
		this.reapMills = reapMills;
	}

	public long getMatureTime() {
		return matureTime;
	}

	public void setMatureTime(long matureTime) {
		this.matureTime = matureTime;
	}

	public MatureType getMatureType() {
		return matureType;
	}

	public void setMatureType(MatureType matureType) {
		this.matureType = matureType;
	}

	public Map<String, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("levelLimit", levelLimit);
		builder.append("growMills", growMills);
		builder.append("plantTime", plantTime);
		builder.append("waterTime", waterTime);
		builder.append("prayTime", prayTime);
		builder.append("reapMills", reapMills);
		builder.append("matureTime", matureTime);
		builder.append("matureType", matureType);
		builder.append("products", products);
		return builder.toString();
	}

	public Object clone() {
		SeedImpl copy = null;
		copy = (SeedImpl) super.clone();
		copy.products = clone(products);
		return copy;
	}
}
