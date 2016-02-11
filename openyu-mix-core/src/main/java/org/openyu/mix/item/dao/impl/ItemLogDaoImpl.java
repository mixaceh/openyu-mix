package org.openyu.mix.item.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.item.log.ItemDecreaseLog;
import org.openyu.mix.item.log.ItemEnhanceLog;
import org.openyu.mix.item.log.ItemIncreaseLog;
import org.openyu.mix.item.log.impl.ItemDecreaseLogImpl;
import org.openyu.mix.item.log.impl.ItemEnhanceLogImpl;
import org.openyu.mix.item.log.impl.ItemIncreaseLogImpl;
import org.openyu.mix.item.dao.ItemLogDao;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemLogDaoImpl extends AppLogDaoSupporter implements ItemLogDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemLogDaoImpl.class);

	/**
	 * 道具增加log
	 */
	private static final String ITEM_INCREASE_LOG_PO_NAME = ItemIncreaseLogImpl.class
			.getName();

	/**
	 * 道具減少log
	 */
	private static final String ITEM_DECREASE_LOG_PO_NAME = ItemDecreaseLogImpl.class
			.getName();

	/**
	 * 道具強化log
	 */
	private static final String ITEM_ENHANCE_LOG_PO_NAME = ItemEnhanceLogImpl.class
			.getName();

	public ItemLogDaoImpl() {
	}

	// --------------------------------------------------
	// ItemIncreaseLog
	// --------------------------------------------------
	/**
	 * 查詢道具增加log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ItemIncreaseLog> findItemIncreaseLog(String roleId) {

		return findItemIncreaseLog(null, roleId);
	}

	/**
	 * 分頁查詢道具增加log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<ItemIncreaseLog> findItemIncreaseLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ITEM_INCREASE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除道具增加log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteItemIncreaseLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ITEM_INCREASE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	// --------------------------------------------------
	// ItemDecreaseLog
	// --------------------------------------------------
	/**
	 * 查詢道具減少log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ItemDecreaseLog> findItemDecreaseLog(String roleId) {

		return findItemDecreaseLog(null, roleId);
	}

	/**
	 * 分頁查詢道具減少log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<ItemDecreaseLog> findItemDecreaseLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ITEM_DECREASE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除道具減少log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteItemDecreaseLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ITEM_DECREASE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

	// --------------------------------------------------
	// ItemEnhanceLog
	// --------------------------------------------------
	/**
	 * 查詢道具強化log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<ItemEnhanceLog> findItemEnhanceLog(String roleId) {

		return findItemEnhanceLog(null, roleId);
	}

	/**
	 * 分頁查詢道具強化log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<ItemEnhanceLog> findItemEnhanceLog(Inquiry inquiry,
			String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ITEM_ENHANCE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除道具強化log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteItemEnhanceLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(ITEM_ENHANCE_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}
}
