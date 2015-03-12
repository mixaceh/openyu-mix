package org.openyu.mix.train.service;

import java.util.List;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.log.TrainLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface TrainLogService extends AppLogService {
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// TrainLog
	// --------------------------------------------------
	/**
	 * 查詢訓練log
	 * 
	 * @param roleId
	 * @return
	 */
	List<TrainLog> findTrainLog(String roleId);

	/**
	 * 分頁查詢訓練log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<TrainLog> findTrainLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除訓練log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteTrainLog(String roleId);

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 記錄鼓舞
	 * 
	 * @param role
	 * @param inspireTime
	 * @param spendItems
	 * @param spendCoin
	 */
	void recordInspire(Role role, long inspireTime, List<Item> spendItems,
			int spendCoin);

}
