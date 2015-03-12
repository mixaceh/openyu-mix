package org.openyu.mix.train.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.train.log.TrainLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface TrainLogDao extends AppLogDao
{
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
	
}
