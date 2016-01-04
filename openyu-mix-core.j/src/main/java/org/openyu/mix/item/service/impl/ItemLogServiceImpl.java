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
import org.openyu.commons.dao.anno.LogTx;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

/**
 * 道具日誌服務
 */
public class ItemLogServiceImpl extends AppLogServiceSupporter implements ItemLogService {

	private static final long serialVersionUID = 6787119250468284108L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ItemLogServiceImpl.class);

	public ItemLogServiceImpl() {
	}

	public ItemLogDao getItemLogDao() {
		return (ItemLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("itemLogDao")
	public void setItemLogDao(ItemLogDao itemLogDao) {
		setCommonDao(itemLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The ItemLogDao is required");
	}
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------
	public List<ItemIncreaseLog> findItemIncreaseLog(String roleId) {
		return getItemLogDao().findItemIncreaseLog(roleId);
	}

	public List<ItemIncreaseLog> findItemIncreaseLog(Inquiry inquiry, String roleId) {
		return getItemLogDao().findItemIncreaseLog(inquiry, roleId);
	}

	public int deleteItemIncreaseLog(String roleId) {
		return getItemLogDao().deleteItemIncreaseLog(roleId);
	}

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------
	public List<ItemDecreaseLog> findItemDecreaseLog(String roleId) {
		return getItemLogDao().findItemDecreaseLog(roleId);
	}

	public List<ItemDecreaseLog> findItemDecreaseLog(Inquiry inquiry, String roleId) {
		return getItemLogDao().findItemDecreaseLog(inquiry, roleId);
	}

	public int deleteItemDecreaseLog(String roleId) {
		return getItemLogDao().deleteItemDecreaseLog(roleId);
	}

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------
	public List<ItemEnhanceLog> findItemEnhanceLog(String roleId) {
		return getItemLogDao().findItemEnhanceLog(roleId);
	}

	public List<ItemEnhanceLog> findItemEnhanceLog(Inquiry inquiry, String roleId) {
		return getItemLogDao().findItemEnhanceLog(inquiry, roleId);
	}

	public int deleteItemEnhanceLog(String roleId) {
		return getItemLogDao().deleteItemEnhanceLog(roleId);
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
	@LogTx
	public void recordIncreaseItem(Role role, ActionType actionType, List<IncreaseItemResult> increaseItemResults) {
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
	@LogTx
	public void recordDecreaseItem(Role role, ActionType actionType, List<DecreaseItemResult> decreaseItemResults) {
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
	@LogTx
	public void recordDecreaseItem(Role role, ActionType actionType, DecreaseItemResult decreaseItemResult) {
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
	@LogTx
	public void recordChangeEnhance(Role role, ActionType actionType, Item beforeItem, Item afterItem, Item spendItem) {
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
