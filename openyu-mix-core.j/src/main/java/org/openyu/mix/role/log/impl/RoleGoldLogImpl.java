package org.openyu.mix.role.log.impl;

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
import org.openyu.mix.role.log.RoleGoldLog;
import org.openyu.mix.role.po.bridge.ActionTypeBridge;
import org.openyu.mix.role.po.bridge.GoldTypeBridge;
import org.openyu.mix.role.service.RoleService.ActionType;
import org.openyu.mix.role.service.RoleService.GoldType;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "role_gold_log", indexes = {
		@Index(name = "role_gold_log_role_id_log_date_action_type", columnList = "role_id,log_date,action_type") })
@SequenceGenerator(name = "sg_role_gold_log", sequenceName = "seq_role_gold_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.role.log.impl.RoleGoldLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class RoleGoldLogImpl extends AppLogEntitySupporter implements RoleGoldLog {
	private static final long serialVersionUID = -29012245968332149L;

	private Long seq;

	private ActionType actionType;

	private GoldType goldType;

	private Long gold;

	private Long beforeGold;

	private Long afterGold;

	public RoleGoldLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_role_gold_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.role.po.userType.ActionTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "gold_type", length = 13)
	@Type(type = "org.openyu.mix.role.po.userType.GoldTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = GoldTypeBridge.class)
	public GoldType getGoldType() {
		return goldType;
	}

	public void setGoldType(GoldType goldType) {
		this.goldType = goldType;
	}

	@Column(name = "gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	@Column(name = "before_gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getBeforeGold() {
		return beforeGold;
	}

	public void setBeforeGold(Long beforeGold) {
		this.beforeGold = beforeGold;
	}

	@Column(name = "after_gold")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getAfterGold() {
		return afterGold;
	}

	public void setAfterGold(Long afterGold) {
		this.afterGold = afterGold;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("actionType", actionType);
		builder.append("goldType", goldType);
		builder.append("gold", gold);
		builder.append("beforeGold", beforeGold);
		builder.append("afterGold", afterGold);
		return builder.toString();
	}

	public Object clone() {
		RoleGoldLogImpl copy = null;
		copy = (RoleGoldLogImpl) super.clone();
		return copy;
	}
}
