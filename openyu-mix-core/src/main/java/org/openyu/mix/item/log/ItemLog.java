package org.openyu.mix.item.log;

import java.util.List;
import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;

/**
 * 道具log,不做bean,直接用entity處理掉
 */
public interface ItemLog extends AppLogEntity
{
	String KEY = ItemLog.class.getName();

	/**
	 * 操作類別
	 * 
	 * @return
	 */
	ActionType getActionType();

	void setActionType(ActionType actionType);

	/**
	 * 多個道具
	 * 
	 * @return
	 */
	List<Item> getItems();

	void setItems(List<Item> items);

}
