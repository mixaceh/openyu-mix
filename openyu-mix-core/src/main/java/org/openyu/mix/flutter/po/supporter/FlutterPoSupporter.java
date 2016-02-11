package org.openyu.mix.flutter.po.supporter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.flutter.po.FlutterPo;
import org.openyu.mix.flutter.po.bridge.AttributeGroupBridge;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.commons.entity.supporter.SeqIdAuditNamesEntitySupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@MappedSuperclass
public abstract class FlutterPoSupporter extends SeqIdAuditNamesEntitySupporter implements FlutterPo {

	private static final long serialVersionUID = 4534438889810389870L;

	// /**
	// * 種族類別
	// */
	// private RaceType raceType;
	//
	// /**
	// * 職業類別
	// */
	// private CareerType careerType;
	/**
	 * 行業=種族+職業
	 */
	private String industryId;

	/**
	 * 經驗
	 */
	private Long exp = 0L;

	/**
	 * 等級
	 */
	private Integer level = 0;

	/**
	 * 技能經驗
	 */
	private Long sp = 0L;

	/**
	 * 金幣餘額
	 */
	private Long gold = 0L;

	/**
	 * 聲望
	 */
	private Integer fame = 0;

	/**
	 * 屬性群
	 */
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	public FlutterPoSupporter() {
	}

	@Column(name = "industry_id", length = 30)
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	@Column(name = "exp")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	@Column(name = "level")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "sp")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getSp() {
		return sp;
	}

	public void setSp(Long sp) {
		this.sp = sp;
	}

	@Column(name = "gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	@Column(name = "fame")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getFame() {
		return fame;
	}

	public void setFame(Integer fame) {
		this.fame = fame;
	}

	@Column(name = "attribute_group", length = 1024)
	@Type(type = "org.openyu.mix.flutter.po.usertype.AttributeGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = AttributeGroupBridge.class)
	public AttributeGroup getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup) {
		this.attributeGroup = (AttributeGroupImpl) attributeGroup;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		// builder.append("raceType", raceType);
		// builder.append("careerType", careerType);
		builder.append("industryId", industryId);
		builder.append("exp", exp);
		builder.append("level", level);
		builder.append("gold", gold);
		builder.append("fame", fame);
		builder.append("attributeGroup", attributeGroup);
		return builder.toString();
	}

	public Object clone() {
		FlutterPoSupporter copy = null;
		copy = (FlutterPoSupporter) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		return copy;
	}

}
