package org.openyu.mix.role.log.impl;

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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.role.log.RoleFameLog;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_role_fame_log")
@SequenceGenerator(name = "mix_role_fame_log_g", sequenceName = "mix_role_fame_log_s", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.role.log.impl.RoleFameLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_role_fame_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_role_fame_log_1", columnNames = {
		"role_id", "log_date" }) })
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class RoleFameLogImpl extends AppLogEntitySupporter implements
		RoleFameLog {
	private static final long serialVersionUID = 1189720120506822270L;

	private Long seq;

	private Integer fame;

	private Integer beforeFame;

	private Integer afterFame;

	public RoleFameLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_role_fame_log_g")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "fame")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getFame() {
		return fame;
	}

	public void setFame(Integer fame) {
		this.fame = fame;
	}

	@Column(name = "before_fame")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getBeforeFame() {
		return beforeFame;
	}

	public void setBeforeFame(Integer beforeFame) {
		this.beforeFame = beforeFame;
	}

	@Column(name = "after_fame")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getAfterFame() {
		return afterFame;
	}

	public void setAfterFame(Integer afterFame) {
		this.afterFame = afterFame;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("fame", fame);
		builder.append("beforeFame", beforeFame);
		builder.append("afterFame", afterFame);
		return builder.toString();
	}

	public Object clone() {
		RoleFameLogImpl copy = null;
		copy = (RoleFameLogImpl) super.clone();
		return copy;
	}
}
