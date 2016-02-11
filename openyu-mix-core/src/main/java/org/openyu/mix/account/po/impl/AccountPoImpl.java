package org.openyu.mix.account.po.impl;

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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.account.po.AccountPo;
import org.openyu.commons.entity.supporter.SeqIdAuditNamesEntitySupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "account", indexes = { @Index(name = "idx_account_valid_id", columnList = "valid,id") })
@SequenceGenerator(name = "sg_account", sequenceName = "seq_account", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.account.po.impl.AccountPoImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class AccountPoImpl extends SeqIdAuditNamesEntitySupporter implements AccountPo {

	private static final long serialVersionUID = 105575403681454770L;

	private Long seq;

	/**
	 * 是否有效
	 */
	private Boolean valid;

	// /**
	// * 角色,改做單向,不做雙向了
	// */
	// private Set<RolePo> roles;

	/**
	 * 儲值總額
	 */
	private Integer coin;

	/**
	 * 累計儲值總額
	 */
	private Integer accuCoin;

	/**
	 * 密碼
	 */
	private String password;

	public AccountPoImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_account")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "valid")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	// --------------------------------------------------
	// deprecated
	// --------------------------------------------------
	// @LazyCollection(LazyCollectionOption.FALSE)
	// @OneToMany(targetEntity = RolePoImpl.class, mappedBy = "account", cascade
	// = CascadeType.ALL)
	// @IndexedEmbedded(targetElement = RolePoImpl.class, depth = 1)
	// public Set<RolePo> getRoles()
	// {
	// return roles;
	// }
	//
	// public void setRoles(Set<RolePo> roles)
	// {
	// this.roles = roles;
	// }

	// --------------------------------------------------
	// use this
	// --------------------------------------------------
	// @LazyCollection(LazyCollectionOption.FALSE)
	// @OneToMany(targetEntity = RolePoImpl.class, cascade = CascadeType.ALL)
	// @JoinColumn(name = "account_seq")
	// @OrderBy("seq")
	// @IndexedEmbedded(targetElement = RolePoImpl.class, depth = 1)
	// public Set<RolePo> getRoles()
	// {
	// return roles;
	// }
	//
	// public void setRoles(Set<RolePo> roles)
	// {
	// this.roles = roles;
	// }

	@Column(name = "coin")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	@Column(name = "accu_coin")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getAccuCoin() {
		return accuCoin;
	}

	public void setAccuCoin(Integer accuCoin) {
		this.accuCoin = accuCoin;
	}

	@Column(name = "pass_word", length = 32)
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("valid", valid);
		builder.append("coin", coin);
		builder.append("accuCoin", accuCoin);
		builder.append("password", password);
		return builder.toString();
	}

	public Object clone() {
		AccountPoImpl copy = null;
		copy = (AccountPoImpl) super.clone();
		return copy;
	}

}
