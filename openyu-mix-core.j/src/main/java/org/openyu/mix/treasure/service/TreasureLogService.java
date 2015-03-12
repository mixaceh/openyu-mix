package org.openyu.mix.treasure.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface TreasureLogService extends AppLogService
{
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// TreasureRefreshLog
	// --------------------------------------------------
	/**
	 * 查詢祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	List<TreasureRefreshLog> findTreasureRefreshLog(String roleId);

	/**
	 * 分頁查詢祕寶刷新log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<TreasureRefreshLog> findTreasureRefreshLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除祕寶刷新log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteTreasureRefreshLog(String roleId);

	// --------------------------------------------------
	// TreasureBuyLog
	// --------------------------------------------------
	/**
	 * 查詢祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	List<TreasureBuyLog> findTreasureBuyLog(String roleId);

	/**
	 * 分頁查詢祕寶購買log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<TreasureBuyLog> findTreasureBuyLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除祕寶購買log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteTreasureBuyLog(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 記錄刷新
	 * 
	 * @param role
	 * @param refreshTime
	 * @param treasures
	 * @param spendItems
	 * @param spendCoin
	 */
	void recordRefresh(Role role, long refreshTime, Map<Integer, Treasure> treasures,
	                   List<Item> spendItems, int spendCoin);

	/**
	 * 記錄購買
	 * 
	 * @param role
	 * @param buyType
	 * @param index
	 * @param treasure
	 * @param item
	 * @param spendGold
	 * @param spendCoin
	 */
	void recordBuy(Role role, BuyType buyType, int index, Treasure treasure, Item item,
					long spendGold, int spendCoin);

}
