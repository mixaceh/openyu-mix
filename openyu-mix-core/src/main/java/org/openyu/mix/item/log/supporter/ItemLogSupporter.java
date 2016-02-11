package org.openyu.mix.item.log.supporter;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.item.log.ItemLog;
import org.openyu.mix.item.po.bridge.ActionTypeBridge;
import org.openyu.mix.item.po.bridge.ItemListBridge;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;

@MappedSuperclass
public abstract class ItemLogSupporter extends AppLogEntitySupporter implements
		ItemLog {

	private static final long serialVersionUID = -6948361394258081530L;

	/**
	 * 道具操作類別
	 */
	private ActionType actionType;

	/**
	 * 多個道具
	 */
	private List<Item> items = new LinkedList<Item>();

	public ItemLogSupporter() {
	}

	@Column(name = "action_type", length = 13)
	@Type(type = "org.openyu.mix.item.po.userType.ActionTypeUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ActionTypeBridge.class)
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Column(name = "items", length = 1024)
	@Type(type = "org.openyu.mix.item.po.userType.ItemListUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ItemListBridge.class)
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("actionType", actionType);
		builder.append("items", items);
		return builder.toString();
	}

	public Object clone() {
		ItemLogSupporter copy = null;
		copy = (ItemLogSupporter) super.clone();
		copy.items = clone(items);
		return copy;
	}
}
