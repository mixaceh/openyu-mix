package org.openyu.mix.train.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.train.dao.TrainLogDao;
import org.openyu.mix.train.log.TrainLog;
import org.openyu.mix.train.log.impl.TrainLogImpl;
import org.openyu.mix.train.service.TrainLogService;
import org.openyu.mix.train.service.TrainService.ActionType;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

public class TrainLogServiceImpl extends AppLogServiceSupporter implements TrainLogService {

	private static final long serialVersionUID = 1077467409712083702L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(TrainLogServiceImpl.class);

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	public TrainLogServiceImpl() {
	}

	public TrainLogDao getTrainLogDao() {
		return (TrainLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("trainLogDao")
	public void setTrainLogDao(TrainLogDao trainLogDao) {
		setCommonDao(trainLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The TrainLogDao is required");
	}
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	public List<TrainLog> findTrainLog(String roleId) {
		return getTrainLogDao().findTrainLog(roleId);
	}

	public List<TrainLog> findTrainLog(Inquiry inquiry, String roleId) {
		return getTrainLogDao().findTrainLog(inquiry, roleId);
	}

	public int deleteTrainLog(String roleId) {
		return getTrainLogDao().deleteTrainLog(roleId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 記錄鼓舞
	 * 
	 * @param inspireTime
	 * @param spendItems
	 * @param spendCoin
	 */
	public void recordInspire(Role role, long inspireTime, List<Item> spendItems, int spendCoin) {
		TrainLog log = new TrainLogImpl();
		recordRole(role, log);
		//
		log.setActionType(ActionType.INSPIRE);
		log.setInspireTime(inspireTime);
		//
		log.setSpendItems(spendItems);
		log.setSpendCoin(spendCoin);
		offerInsert(log);
	}

}
