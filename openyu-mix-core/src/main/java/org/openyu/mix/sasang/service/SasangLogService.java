package org.openyu.mix.sasang.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.log.SasangPutLog;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.service.SasangService.PutType;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface SasangLogService extends AppLogService
{
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// SasangPlayLog
	// --------------------------------------------------
	/**
	 * 查詢四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangPlayLog> findSasangPlayLog(String roleId);

	/**
	 * 分頁查詢四象玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangPlayLog> findSasangPlayLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangPlayLog(String roleId);

	// --------------------------------------------------
	// SasangPutLog
	// --------------------------------------------------
	/**
	 * 查詢四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangPutLog> findSasangPutLog(String roleId);

	/**
	 * 分頁查詢四象放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangPutLog> findSasangPutLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangPutLog(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 記錄玩的
	 * 
	 * @param role
	 * @param playType
	 * @param playTime
	 * @param outcome
	 * @param totalTimes
	 * @param spendGold
	 * @param spendItems
	 * @param spendCoin
	 */
	void recordPlay(Role role, PlayType playType, long playTime, Outcome outcome,
					int totalTimes, long spendGold, List<Item> spendItems, int spendCoin);

	/**
	 * 記錄放入
	 * 
	 * @param role
	 * @param putType
	 * @param awards
	 */
	void recordPut(Role role, PutType putType, Map<String, Integer> awards);

	/**
	 * 記錄開出的結果,有成名的
	 * 
	 * @param role
	 * @param playType
	 * @param playTime
	 * @param outcomes
	 */
	void recordFamous(Role role, PlayType playType, long playTime, List<Outcome> outcomes);

	// --------------------------------------------------
	// SasangFamousLog
	// --------------------------------------------------
	/**
	 * 查詢四象成名的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangFamousLog> findSasangFamousLog(String roleId);

	/**
	 * 分頁查詢四象成名的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangFamousLog> findSasangFamousLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象成名的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangFamousLog(String roleId);

}
