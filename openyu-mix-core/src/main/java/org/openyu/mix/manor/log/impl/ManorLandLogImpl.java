package org.openyu.mix.manor.log.impl;

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
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.po.bridge.ActionTypeBridge;
import org.openyu.mix.manor.service.ManorService.ActionType;
import org.openyu.mix.manor.vo.Land;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "manor_land_log", indexes = {
		@Index(name = "manor_land_log_role_id_log_date_action_type", columnList = "role_id,log_date,action_type") })
@SequenceGenerator(name = "sg_manor_land_log", sequenceName = "seq_manor_land_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.manor.log.impl.ManorLandLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class ManorLandLogImpl extends AppLogEntitySupporter implements ManorLandLog {
	private static final long serialVersionUID = -8609168204892772151L;

	private Long seq;

	/**
	 * 操作類別
	 */
	private ActionType actionType;

	/**
	 * 農場索引
	 */
	private Integer farmIndex;

	/**
	 * 土地
	 */
	private Land land;

	/**
	 * 花費的金幣
	 */
	private Long spendGold;

	public ManorLandLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_manor_land_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.manor.po.usertype.ActionTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "farm_index")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getFarmIndex() {
		return farmIndex;
	}

	public void setFarmIndex(Integer farmIndex) {
		this.farmIndex = farmIndex;
	}

	@Column(name = "land", length = 1024)
	@Type(type = "org.openyu.mix.item.po.usertype.ItemUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemBridge.class)
	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

	@Column(name = "spend_gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getSpendGold() {
		return spendGold;
	}

	public void setSpendGold(Long spendGold) {
		this.spendGold = spendGold;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("actionType", actionType);
		builder.append("farmIndex", farmIndex);
		builder.append("land", land);
		builder.append("spendGold", spendGold);
		return builder.toString();
	}

	public Object clone() {
		ManorLandLogImpl copy = null;
		copy = (ManorLandLogImpl) super.clone();
		copy.land = clone(land);
		return copy;
	}
}
