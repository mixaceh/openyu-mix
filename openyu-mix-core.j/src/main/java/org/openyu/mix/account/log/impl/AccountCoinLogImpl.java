package org.openyu.mix.account.log.impl;

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
import org.openyu.mix.account.log.AccountCoinLog;
import org.openyu.mix.account.po.bridge.ActionTypeBridge;
import org.openyu.mix.account.po.bridge.CoinTypeBridge;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "account_coin_log", indexes = {
		@Index(name = "idx_account_coin_log_account_id_log_date_action_type", columnList = "account_id,log_date,action_type") })
@SequenceGenerator(name = "sg_account_coin_log", sequenceName = "seq_account_coin_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.account.log.impl.AccountCoinLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class AccountCoinLogImpl extends AppLogEntitySupporter implements AccountCoinLog {
	private static final long serialVersionUID = -29012245968332149L;

	/**
	 * 流水號
	 */
	private Long seq;

	/**
	 * 操作類別
	 */
	private ActionType actionType;
	/**
	 * 儲值增減的原因
	 */
	private CoinType coinType;
	/**
	 * 儲值幣
	 */
	private Integer coin;

	public AccountCoinLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_account_coin_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.account.po.userType.ActionTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "coin_type", length = 13)
	@Type(type = "org.openyu.mix.account.po.userType.CoinTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = CoinTypeBridge.class)
	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	@Column(name = "coin")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("actionType", actionType);
		builder.append("coinType", coinType);
		builder.append("coin", coin);
		return builder.toString();
	}

	public Object clone() {
		AccountCoinLogImpl copy = null;
		copy = (AccountCoinLogImpl) super.clone();
		return copy;
	}
}
