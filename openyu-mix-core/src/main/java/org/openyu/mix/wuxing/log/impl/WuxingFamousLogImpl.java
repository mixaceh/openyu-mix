package org.openyu.mix.wuxing.log.impl;

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
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.po.bridge.OutcomeBridge;
import org.openyu.mix.wuxing.po.bridge.PlayTypeBridge;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.vo.Outcome;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "wuxing_famous_log", indexes = {
		@Index(name = "wuxing_famous_log_role_id_log_date_play_type", columnList = "role_id,log_date,play_type") })
@SequenceGenerator(name = "sg_wuxing_famous_log", sequenceName = "seq_wuxing_famous_log", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.wuxing.log.impl.WuxingFamousLogImpl")
@Proxy(lazy = false)
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class WuxingFamousLogImpl extends AppLogEntitySupporter implements WuxingFamousLog
{

	private static final long serialVersionUID = 3756281645321688719L;

	private Long seq;

	private PlayType playType;

	private Long playTime;

	private Outcome outcome;

	public WuxingFamousLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_wuxing_famous_log")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "play_type", length = 13)
	@Type(type = "org.openyu.mix.wuxing.po.userType.PlayTypeUserType")
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
	@Type(type = "org.openyu.mix.wuxing.po.userType.OutcomeUserType")
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
		WuxingFamousLogImpl copy = null;
		copy = (WuxingFamousLogImpl) super.clone();
		copy.outcome = clone(outcome);
		return copy;
	}
}
