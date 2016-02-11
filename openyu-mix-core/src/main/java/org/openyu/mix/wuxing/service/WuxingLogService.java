package org.openyu.mix.wuxing.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.log.WuxingPlayLog;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface WuxingLogService extends AppLogService {
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// WuxingPlayLog
	// --------------------------------------------------
	/**
	 * 查詢五行玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingPlayLog> findWuxingPlayLog(String roleId);

	/**
	 * 分頁查詢五行玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingPlayLog> findWuxingPlayLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除五行玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingPlayLog(String roleId);

	// --------------------------------------------------
	// WuxingPutLog
	// --------------------------------------------------
	/**
	 * 查詢五行放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingPutLog> findWuxingPutLog(String roleId);

	/**
	 * 分頁查詢五行放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingPutLog> findWuxingPutLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除五行放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingPutLog(String roleId);

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
	void recordPlay(Role role, PlayType playType, long playTime,
			Outcome outcome, int totalTimes, long spendGold,
			List<Item> spendItems, int spendCoin);

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
	void recordFamous(Role role, PlayType playType, long playTime,
			List<Outcome> outcomes);

	// --------------------------------------------------
	// WuxingFamousLog
	// --------------------------------------------------
	/**
	 * 查詢五行成名的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingFamousLog> findWuxingFamousLog(String roleId);

	/**
	 * 分頁查詢五行成名的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingFamousLog> findWuxingFamousLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除五行成名的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingFamousLog(String roleId);

}
