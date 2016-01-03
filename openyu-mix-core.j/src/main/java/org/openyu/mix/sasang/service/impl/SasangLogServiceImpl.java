package org.openyu.mix.sasang.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.sasang.dao.SasangLogDao;
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.log.SasangPutLog;
import org.openyu.mix.sasang.log.impl.SasangFamousLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPlayLogImpl;
import org.openyu.mix.sasang.log.impl.SasangPutLogImpl;
import org.openyu.mix.sasang.service.SasangLogService;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.service.SasangService.PutType;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

public class SasangLogServiceImpl extends AppLogServiceSupporter implements SasangLogService {

	private static final long serialVersionUID = -4829299050153182066L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SasangLogServiceImpl.class);

	public SasangLogServiceImpl() {
	}

	public SasangLogDao getSasangLogDao() {
		return (SasangLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("sasangLogDao")
	public void setSasangLogDao(SasangLogDao sasangLogDao) {
		setCommonDao(sasangLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The SasangLogDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------

	public List<SasangPlayLog> findSasangPlayLog(String roleId) {
		return getSasangLogDao().findSasangPlayLog(roleId);
	}

	public List<SasangPlayLog> findSasangPlayLog(Inquiry inquiry, String roleId) {
		return getSasangLogDao().findSasangPlayLog(inquiry, roleId);
	}

	public int deleteSasangPlayLog(String roleId) {
		return getSasangLogDao().deleteSasangPlayLog(roleId);
	}

	public List<SasangPutLog> findSasangPutLog(String roleId) {
		return getSasangLogDao().findSasangPutLog(roleId);
	}

	public List<SasangPutLog> findSasangPutLog(Inquiry inquiry, String roleId) {
		return getSasangLogDao().findSasangPutLog(inquiry, roleId);
	}

	public int deleteSasangPutLog(String roleId) {
		return getSasangLogDao().deleteSasangPutLog(roleId);
	}

	public List<SasangFamousLog> findSasangFamousLog(String roleId) {
		return getSasangLogDao().findSasangFamousLog(roleId);
	}

	public List<SasangFamousLog> findSasangFamousLog(Inquiry inquiry, String roleId) {
		return getSasangLogDao().findSasangFamousLog(inquiry, roleId);
	}

	public int deleteSasangFamousLog(String roleId) {
		return getSasangLogDao().deleteSasangFamousLog(roleId);
	}

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
	public void recordPlay(Role role, PlayType playType, long playTime, Outcome outcome, int totalTimes, long spendGold,
			List<Item> spendItems, int spendCoin) {
		SasangPlayLog log = new SasangPlayLogImpl();
		recordRole(role, log);
		//
		log.setPlayType(playType);
		log.setPlayTime(playTime);
		// clone
		Outcome cloneOutcome = clone(outcome);
		log.setOutcome(cloneOutcome);
		//
		log.setRealTimes(totalTimes);
		log.setSpendGold(spendGold);
		//
		log.setSpendItems(spendItems);
		log.setSpendCoin(spendCoin);
		//
		offerInsert(log);
	}

	/**
	 * 記錄放入
	 * 
	 * @param role
	 * @param putType
	 * @param awards
	 */
	public void recordPut(Role role, PutType putType, Map<String, Integer> awards) {
		SasangPutLog log = new SasangPutLogImpl();
		recordRole(role, log);
		//
		log.setPutType(putType);
		// clone

		Map<String, Integer> cloneAwards = clone(awards);
		log.setAwards(cloneAwards);
		//
		offerInsert(log);
	}

	/**
	 * 記錄開出的結果,有成名的
	 * 
	 * @param role
	 * @param playType
	 * @param playTime
	 * @param outcomes
	 */
	public void recordFamous(Role role, PlayType playType, long playTime, List<Outcome> outcomes) {
		// clone
		List<Outcome> cloneOutcomes = clone(outcomes);
		for (Outcome outcome : cloneOutcomes) {
			Prize prize = outcome.getPrize();
			// 有成名的才記錄
			if (prize == null || !prize.isFamous()) {
				continue;
			}
			//
			SasangFamousLog log = new SasangFamousLogImpl();
			recordRole(role, log);
			//
			log.setPlayType(playType);
			log.setPlayTime(playTime);
			//
			log.setOutcome(outcome);
			//
			offerInsert(log);
		}

	}
}
