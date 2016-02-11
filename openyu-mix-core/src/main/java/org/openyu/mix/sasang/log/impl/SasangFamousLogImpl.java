package org.openyu.mix.sasang.log.impl;

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
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.po.bridge.OutcomeBridge;
import org.openyu.mix.sasang.po.bridge.PlayTypeBridge;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.vo.Outcome;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sasang_famous_log", indexes = {
		@Index(name = "sasang_famous_log_role_id_log_date_play_type", columnList = "role_id,log_date,play_type") })
@SequenceGenerator(name = "sg_sasang_famous_log", sequenceName = "seq_sasang_famous_log", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.sasang.log.impl.SasangFamousLogImpl")
@Proxy(lazy = false)
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class SasangFamousLogImpl extends AppLogEntitySupporter implements SasangFamousLog
{

	private static final long serialVersionUID = 3756281645321688719L;

	private Long seq;

	private PlayType playType;

	private Long playTime;

	private Outcome outcome;

	public SasangFamousLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_sasang_famous_log")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "play_type", length = 13)
	@Type(type = "org.openyu.mix.sasang.po.usertype.PlayTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = PlayTypeBridge.class)
	public PlayType getPlayType()
	{
		return playType;
	}

	public void setPlayType(PlayType playType)
	{
		this.playType = playType;
	}

	@Column(name = "play_time")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Long getPlayTime()
	{
		return playTime;
	}

	public void setPlayTime(Long playTime)
	{
		this.playTime = playTime;
	}

	@Column(name = "outcome", length = 255)
	@Type(type = "org.openyu.mix.sasang.po.usertype.OutcomeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = OutcomeBridge.class)
	public Outcome getOutcome()
	{
		return outcome;
	}

	public void setOutcome(Outcome outcome)
	{
		this.outcome = outcome;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("playType", playType);
		builder.append("playTime", playTime);
		builder.append("outcome", outcome);
		return builder.toString();
	}

	public Object clone()
	{
		SasangFamousLogImpl copy = null;
		copy = (SasangFamousLogImpl) super.clone();
		copy.outcome = clone(outcome);
		return copy;
	}
}
