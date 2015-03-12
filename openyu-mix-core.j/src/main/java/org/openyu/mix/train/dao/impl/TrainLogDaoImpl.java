package org.openyu.mix.train.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppLogDaoSupporter;
import org.openyu.mix.train.dao.TrainLogDao;
import org.openyu.mix.train.log.TrainLog;
import org.openyu.mix.train.log.impl.TrainLogImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainLogDaoImpl extends AppLogDaoSupporter implements TrainLogDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TrainLogDaoImpl.class);

	private static final String TRAIN_LOG_PO_NAME = TrainLogImpl.class
			.getName();

	public TrainLogDaoImpl() {
	}

	/**
	 * 查詢訓練log
	 * 
	 * @param roleId
	 * @return
	 */
	public List<TrainLog> findTrainLog(String roleId) {

		return findTrainLog(null, roleId);
	}

	/**
	 * 分頁查詢訓練log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	public List<TrainLog> findTrainLog(Inquiry inquiry, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(TRAIN_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return findByHql(inquiry, null, hql.toString(), params);
	}

	/**
	 * 刪除訓練log
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteTrainLog(String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ");
		hql.append(TRAIN_LOG_PO_NAME + " ");
		hql.append("where 1=1 ");

		// roleId
		hql.append("and roleId = :roleId ");
		params.put("roleId", roleId);
		//
		return executeByHql(hql, params);
	}

}
