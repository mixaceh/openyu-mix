package org.openyu.mix.item.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.item.dao.ItemLogDao;
import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.mix.item.log.impl.ItemDecreaseLogImpl;
import org.openyu.mix.item.log.impl.ItemEnhanceLogImpl;
import org.openyu.mix.item.log.impl.ItemIncreaseLogImpl;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.EnhanceLevelBean;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

/**
 * 道具日誌服務
 */
public class ItemLogServiceImpl extends AppLogServiceSupporter implements
		ItemLogService {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemLogServiceImpl.class);
	
	@Autowired
	@Qualifier("itemLogDao")
	private transient ItemLogDao itemLogDao;

	public ItemLogServiceImpl() {
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------
	public List<ItemIncreaseLog> findItemIncreaseLog(String roleId) {
		return itemLogDao.findItemIncreaseLog(roleId);
	}

	public List<ItemIncreaseLog> findItemIncreaseLog(Inquiry inquiry,
			String roleId) {
		return itemLogDao.findItemIncreaseLog(inquiry, roleId);
	}

	public int deleteItemIncreaseLog(String roleId) {
		return itemLogDao.deleteItemIncreaseLog(roleId);
	}

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------
	public List<ItemDecreaseLog> findItemDecreaseLog(String roleId) {
		return itemLogDao.findItemDecreaseLog(roleId);
	}

	public List<ItemDecreaseLog> findItemDecreaseLog(Inquiry inquiry,
			String roleId) {
		return itemLogDao.findItemDecreaseLog(inquiry, roleId);
	}

	public int deleteItemDecreaseLog(String roleId) {
		return itemLogDao.deleteItemDecreaseLog(roleId);
	}

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------
	public List<ItemEnhanceLog> findItemEnhanceLog(String roleId) {
		return itemLogDao.findItemEnhanceLog(roleId);
	}

	public List<ItemEnhanceLog> findItemEnhanceLog(Inquiry inquiry,
			String roleId) {
		return itemLogDao.findItemEnhanceLog(inquiry, roleId);
	}

	public int deleteItemEnhanceLog(String roleId) {
		return itemLogDao.deleteItemEnhanceLog(roleId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------
	/**
	 * 紀錄增加道具
	 * 
	 * @param role
	 * @param actionType
	 * @param increaseItemResults
	 */
	public void recordIncreaseItem(Role role, ActionType actionType,
			List<IncreaseItemResult> increaseItemResults) {
		ItemIncreaseLog log = new ItemIncreaseLogImpl();
		recordRole(role, log);

		log.setActionType(actionType);
		//
		List<Item> items = new LinkedList<Item>();
		for (IncreaseItemResult itemResult : increaseItemResults) {
			items.add(itemResult.getItem());
		}
		log.setItems(items);
		//
		offerInsert(log);
	}

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------

	/**
	 * 紀錄減少道具
	 * 
	 * @param role
	 * @param actionType
	 * @param decreaseItemResults
	 */
	public void recordDecreaseItem(Role role, ActionType actionType,
			List<DecreaseItemResult> decreaseItemResults) {
		ItemDecreaseLog log = new ItemDecreaseLogImpl();
		recordRole(role, log);

		log.setActionType(actionType);
		//
		List<Item> items = new LinkedList<Item>();
		for (DecreaseItemResult itemResult : decreaseItemResults) {
			items.add(itemResult.getItem());
		}
		log.setItems(items);
		//
		offerInsert(log);
	}

	/**
	 * 紀錄減少道具,by itemId
	 * 
	 * @param role
	 * @param actionType
	 * @param decreaseItemResult
	 */
	public void recordDecreaseItem(Role role, ActionType actionType,
			DecreaseItemResult decreaseItemResult) {
		ItemDecreaseLog log = new ItemDecreaseLogImpl();
		recordRole(role, log);
		//
		log.setActionType(actionType);
		//
		List<Item> items = new LinkedList<Item>();
		items.add(decreaseItemResult.getItem());
		log.setItems(items);
		//
		offerInsert(log);
	}

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------

	/**
	 * 紀錄道具強化
	 * 
	 * @param role
	 * @param beforeItem
	 *            強化前道具
	 * @param afterItem
	 *            強化後道具
	 * @param spendItem
	 *            消耗的道具
	 * @param actionType
	 */
	public void recordChangeEnhance(Role role, ActionType actionType,
			Item beforeItem, Item afterItem, Item spendItem) {
		ItemEnhanceLog log = new ItemEnhanceLogImpl();
		recordRole(role, log);

		// clone
		Item cloneBeforeItem = beforeItem.clone(beforeItem);// 強化前的道具
		Item cloneAfterItem = afterItem.clone(afterItem);// 強化後的道具
		//
		log.setActionType(actionType);
		log.setItem(cloneAfterItem);// 紀錄強化後的道具
		//
		if (cloneBeforeItem instanceof EnhanceLevelBean) {
			EnhanceLevelBean before = (EnhanceLevelBean) cloneBeforeItem;
			EnhanceLevelBean after = (EnhanceLevelBean) cloneAfterItem;
			//
			int beforeEnhance = before.getEnhanceLevel().getValue();
			int afterEnhance = after.getEnhanceLevel().getValue();
			int enhance = afterEnhance - beforeEnhance;
			//
			log.setEnhance(enhance);
			log.setBeforeEnhance(beforeEnhance);// 強化前的強化等級
			log.setAfterEnhance(afterEnhance);// 強化後的強化等級
		}
		//
		if (spendItem != null) {
			// clone
			Item cloneSpendItem = spendItem.clone(spendItem);
			cloneSpendItem.setAmount(1);// 消耗的道具,消耗1個
			log.setSpendItem(cloneSpendItem);
		}
		//
		offerInsert(log);
	}

}
