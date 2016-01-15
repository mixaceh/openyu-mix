package org.openyu.mix.item.log.impl;

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

import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.supporter.ItemLogSupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "item_decrease_log", indexes = {
		@Index(name = "item_decrease_log_role_id_log_date_action_type", columnList = "role_id,log_date,action_type") })
@SequenceGenerator(name = "sg_item_decrease_log", sequenceName = "seq_item_decrease_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.item.log.impl.ItemDecreaseLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class ItemDecreaseLogImpl extends ItemLogSupporter implements ItemDecreaseLog {

	private static final long serialVersionUID = -4013413072845884166L;

	private Long seq;

	public ItemDecreaseLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_item_decrease_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone() {
		ItemDecreaseLogImpl copy = null;
		copy = (ItemDecreaseLogImpl) super.clone();
		return copy;
	}
}
