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
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.po.bridge.ActionTypeBridge;
import org.openyu.mix.item.po.bridge.ItemBridge;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "item_enhance_log", indexes = {
		@Index(name = "item_enhance_log_role_id_log_date_action_type", columnList = "role_id,log_date,action_type") })
@SequenceGenerator(name = "sg_item_enhance_log", sequenceName = "seq_item_enhance_log", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.item.log.impl.ItemEnhanceLogImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class ItemEnhanceLogImpl extends AppLogEntitySupporter implements ItemEnhanceLog {

	private static final long serialVersionUID = 8670276099681802194L;

	private Long seq;

	/**
	 * 道具操作類別
	 */
	private ActionType actionType;

	private Integer enhance;

	private Integer beforeEnhance;

	private Integer afterEnhance;

	/**
	 * 被強化後的道具
	 */
	private Item item;

	/**
	 * 消耗的道具
	 */
	private Item spendItem;

	public ItemEnhanceLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_item_enhance_log")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.item.po.userType.ActionTypeUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "enhance")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getEnhance() {
		return enhance;
	}

	public void setEnhance(Integer enhance) {
		this.enhance = enhance;
	}

	@Column(name = "before_enhance")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getBeforeEnhance() {
		return beforeEnhance;
	}

	public void setBeforeEnhance(Integer beforeEnhance) {
		this.beforeEnhance = beforeEnhance;
	}

	@Column(name = "after_enhance")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Integer getAfterEnhance() {
		return afterEnhance;
	}

	public void setAfterEnhance(Integer afterEnhance) {
		this.afterEnhance = afterEnhance;
	}

	@Column(name = "item", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemBridge.class)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "spend_item", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemBridge.class)
	public Item getSpendItem() {
		return spendItem;
	}

	public void setSpendItem(Item spendItem) {
		this.spendItem = spendItem;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("actionType", actionType);
		builder.append("enhance", enhance);
		builder.append("beforeEnhance", beforeEnhance);
		builder.append("afterEnhance", afterEnhance);
		builder.append("item", item);
		builder.append("spendItem", spendItem);
		return builder.toString();
	}

	public Object clone() {
		ItemEnhanceLogImpl copy = null;
		copy = (ItemEnhanceLogImpl) super.clone();
		copy.item = clone(item);
		copy.spendItem = clone(spendItem);
		return copy;
	}
}
