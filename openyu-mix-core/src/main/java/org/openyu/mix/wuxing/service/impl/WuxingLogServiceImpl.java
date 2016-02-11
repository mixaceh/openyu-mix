package org.openyu.mix.wuxing.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.wuxing.dao.WuxingLogDao;
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.log.WuxingPlayLog;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.mix.wuxing.log.impl.WuxingFamousLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPlayLogImpl;
import org.openyu.mix.wuxing.log.impl.WuxingPutLogImpl;
import org.openyu.mix.wuxing.service.WuxingLogService;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.service.WuxingService.PutType;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

public class WuxingLogServiceImpl extends AppLogServiceSupporter implements WuxingLogService {

	private static final long serialVersionUID = -4552052837216867641L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(WuxingLogServiceImpl.class);

	public WuxingLogServiceImpl() {
	}

	public WuxingLogDao getWuxingLogDao() {
		return (WuxingLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("wuxingLogDao")
	public void setWuxingLogDao(WuxingLogDao wuxingLogDao) {
		setCommonDao(wuxingLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The WuxingLogDao is required");
	}
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	public List<WuxingPlayLog> findWuxingPlayLog(String roleId) {
		return getWuxingLogDao().findWuxingPlayLog(roleId);
	}

	public List<WuxingPlayLog> findWuxingPlayLog(Inquiry inquiry, String roleId) {
		return getWuxingLogDao().findWuxingPlayLog(inquiry, roleId);
	}

	public int deleteWuxingPlayLog(String roleId) {
		return getWuxingLogDao().deleteWuxingPlayLog(roleId);
	}

	public List<WuxingPutLog> findWuxingPutLog(String roleId) {
		return getWuxingLogDao().findWuxingPutLog(roleId);
	}

	public List<WuxingPutLog> findWuxingPutLog(Inquiry inquiry, String roleId) {
		return getWuxingLogDao().findWuxingPutLog(inquiry, roleId);
	}

	public int deleteWuxingPutLog(String roleId) {
		return getWuxingLogDao().deleteWuxingPutLog(roleId);
	}

	public List<WuxingFamousLog> findWuxingFamousLog(String roleId) {
		return getWuxingLogDao().findWuxingFamousLog(roleId);
	}

	public List<WuxingFamousLog> findWuxingFamousLog(Inquiry inquiry, String roleId) {
		return getWuxingLogDao().findWuxingFamousLog(inquiry, roleId);
	}

	public int deleteWuxingFamousLog(String roleId) {
		return getWuxingLogDao().deleteWuxingFamousLog(roleId);
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
		WuxingPlayLog log = new WuxingPlayLogImpl();
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
		WuxingPutLog log = new WuxingPutLogImpl();
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
			List<Prize> prizes = outcome.getPrizes();
			for (Prize prize : prizes) {
				// 有成名的才記錄
				if (prize == null || !prize.isFamous()) {
					continue;
				}
				//
				WuxingFamousLog log = new WuxingFamousLogImpl();
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
}
