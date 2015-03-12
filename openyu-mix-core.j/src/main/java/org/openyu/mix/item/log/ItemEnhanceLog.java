package org.openyu.mix.item.log;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;

/**
 * 道具強化log
 */
public interface ItemEnhanceLog extends AppLogEntity
{
	String KEY = ItemEnhanceLog.class.getName();

	/**
	 * 道具操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 強化
	 * 
	 * @return
	 */
	Integer getEnhance();

	void setEnhance(Integer enhance);

	/**
	 * 強化改變前
	 * 
	 * @return
	 */
	Integer getBeforeEnhance();

	void setBeforeEnhance(Integer beforeEnhance);

	/**
	 * 強化改變後
	 * 
	 * @return
	 */
	Integer getAfterEnhance();

	void setAfterEnhance(Integer afterEnhance);

	/**
	 * 被強化後的道具
	 * 
	 * @return
	 */
	Item getItem();

	void setItem(Item item);

	/**
	 * 消耗的道具
	 * 
	 * @return
	 */
	Item getSpendItem();

	void setSpendItem(Item spendItem);
}
