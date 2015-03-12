package org.openyu.mix.train.log.impl;

import java.util.LinkedList;
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
import org.openyu.mix.train.log.TrainLog;
import org.openyu.mix.train.po.bridge.ActionTypeBridge;
import org.openyu.mix.train.service.TrainService.ActionType;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_train_log")
@SequenceGenerator(name = "mix_train_log_g", sequenceName = "mix_train_log_s", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.train.log.impl.TrainLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_train_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_train_log_1", columnNames = {
		"role_id", "log_date", "action_type" }) })
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class TrainLogImpl extends AppLogEntitySupporter implements TrainLog
{

	private static final long serialVersionUID = 2525395382597962530L;

	private Long seq;

	/**
	 * 操作類別
	 */
	private ActionType actionType;

	/**
	 * 鼓舞時間
	 */
	private Long inspireTime;

	/**
	 * 消耗的道具
	 */
	private List<Item> spendItems = new LinkedList<Item>();

	/**
	 * 消耗的儲值幣
	 */
	private Integer spendCoin;

	public TrainLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_train_log_g")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.train.po.userType.ActionTypeUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType()
	{
		return actionType;
	}

	public void setActionType(ActionType actionType)
	{
		this.actionType = actionType;
	}

	@Column(name = "inspire_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getInspireTime()
	{
		return inspireTime;
	}

	public void setInspireTime(Long inspireTime)
	{
		this.inspireTime = inspireTime;
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
		builder.append("actionType", actionType);
		builder.append("inspireTime", inspireTime);
		builder.append("spendItems", spendItems);
		builder.append("spendCoin", spendCoin);
		return builder.toString();
	}

	public Object clone()
	{
		TrainLogImpl copy = null;
		copy = (TrainLogImpl) super.clone();
		copy.spendItems = clone(spendItems);
		return copy;
	}
}
