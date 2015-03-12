package org.openyu.mix.sasang.log.impl;

import java.util.List;

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
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.item.po.bridge.ItemListBridge;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.po.bridge.OutcomeBridge;
import org.openyu.mix.sasang.po.bridge.PlayTypeBridge;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.vo.Outcome;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_sasang_play_log")
@SequenceGenerator(name = "mix_sasang_play_log_g", sequenceName = "mix_sasang_play_log_s", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.sasang.log.impl.SasangPlayLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_sasang_play_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_sasang_play_log_1", columnNames = {
		"role_id", "log_date", "play_type" }) })
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class SasangPlayLogImpl extends AppLogEntitySupporter implements SasangPlayLog
{

	private static final long serialVersionUID = 3756281645321688719L;

	private Long seq;

	private PlayType playType;

	private Long playTime;

	private Outcome outcome;

	private Integer realTimes;

	private Long spendGold;

	private List<Item> spendItems;

	private Integer spendCoin;

	public SasangPlayLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_sasang_play_log_g")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "play_type", length = 13)
	@Type(type = "org.openyu.mix.sasang.po.userType.PlayTypeUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
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
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getPlayTime()
	{
		return playTime;
	}

	public void setPlayTime(Long playTime)
	{
		this.playTime = playTime;
	}

	@Column(name = "outcome", length = 255)
	@Type(type = "org.openyu.mix.sasang.po.userType.OutcomeUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = OutcomeBridge.class)
	public Outcome getOutcome()
	{
		return outcome;
	}

	public void setOutcome(Outcome outcome)
	{
		this.outcome = outcome;
	}

	@Column(name = "real_times")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getRealTimes()
	{
		return realTimes;
	}

	public void setRealTimes(Integer realTimes)
	{
		this.realTimes = realTimes;
	}

	@Column(name = "spend_gold")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getSpendGold()
	{
		return spendGold;
	}

	public void setSpendGold(Long spendGold)
	{
		this.spendGold = spendGold;
	}

	@Column(name = "spend_items", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemListUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemListBridge.class)
	public List<Item> getSpendItems()
	{
		return spendItems;
	}

	public void setSpendItems(List<Item> spendItems)
	{
		this.spendItems = spendItems;
	}

	@Column(name = "spend_coin")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getSpendCoin()
	{
		return spendCoin;
	}

	public void setSpendCoin(Integer spendCoin)
	{
		this.spendCoin = spendCoin;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("playType", playType);
		builder.append("playTime", playTime);
		builder.append("outcome", outcome);
		builder.append("realTimes", realTimes);
		builder.append("spendGold", spendGold);
		builder.append("spendItems", spendItems);
		builder.append("spendCoin", spendCoin);
		return builder.toString();
	}

	public Object clone()
	{
		SasangPlayLogImpl copy = null;
		copy = (SasangPlayLogImpl) super.clone();
		copy.outcome = clone(outcome);
		copy.spendItems = clone(spendItems);
		return copy;
	}
}
