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
import org.openyu.mix.role.log.RoleLevelLog;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_role_level_log")
@SequenceGenerator(name = "mix_role_level_log_g", sequenceName = "mix_role_level_log_s", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.role.log.impl.RoleLevelLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_role_level_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_role_level_log_1", columnNames = {
		"role_id", "log_date" }) })
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class RoleLevelLogImpl extends AppLogEntitySupporter implements
		RoleLevelLog {
	private static final long serialVersionUID = 1189720120506822270L;

	private Long seq;

	private Integer level;

	private Integer beforeLevel;

	private Integer afterLevel;

	public RoleLevelLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_role_level_log_g")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "level")
	@Field(store = Store.YES, index = Index.YES)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "before_level")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getBeforeLevel() {
		return beforeLevel;
	}

	public void setBeforeLevel(Integer beforeLevel) {
		this.beforeLevel = beforeLevel;
	}

	@Column(name = "after_level")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getAfterLevel() {
		return afterLevel;
	}

	public void setAfterLevel(Integer afterLevel) {
		this.afterLevel = afterLevel;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("level", level);
		builder.append("beforeLevel", beforeLevel);
		builder.append("afterLevel", afterLevel);
		return builder.toString();
	}

	public Object clone() {
		RoleLevelLogImpl copy = null;
		copy = (RoleLevelLogImpl) super.clone();
		return copy;
	}
}
