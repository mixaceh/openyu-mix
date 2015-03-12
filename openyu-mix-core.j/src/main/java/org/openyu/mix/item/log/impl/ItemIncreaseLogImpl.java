package org.openyu.mix.item.log.impl;

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

import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.mix.item.log.supporter.ItemLogSupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "mix_item_increase_log")
@SequenceGenerator(name = "mix_item_increase_log_g", sequenceName = "mix_item_increase_log_s", allocationSize = 1)
//when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.item.log.impl.ItemIncreaseLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_item_increase_log", indexes = { @org.hibernate.annotations.Index(name = "idx_mix_item_increase_log_1", columnNames = {
		"role_id", "log_date", "action_type" }) })
//--------------------------------------------------
//search
//--------------------------------------------------
//@Indexed
public class ItemIncreaseLogImpl extends ItemLogSupporter implements ItemIncreaseLog
{

	private static final long serialVersionUID = -5744333536619578534L;

	private Long seq;

	public ItemIncreaseLogImpl()
	{}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_item_increase_log_g")
	public Long getSeq()
	{
		return seq;
	}

	public void setSeq(Long seq)
	{
		this.seq = seq;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone()
	{
		ItemIncreaseLogImpl copy = null;
		copy = (ItemIncreaseLogImpl) super.clone();
		return copy;
	}
}
