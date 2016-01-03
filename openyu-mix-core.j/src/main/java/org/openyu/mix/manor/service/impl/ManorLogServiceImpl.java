package org.openyu.mix.manor.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.dao.ManorLogDao;
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.mix.manor.log.impl.ManorLandLogImpl;
import org.openyu.mix.manor.log.impl.ManorSeedLogImpl;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService.ActionType;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.openyu.commons.util.AssertHelper;

public class ManorLogServiceImpl extends AppLogServiceSupporter implements ManorLogService {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ManorLogServiceImpl.class);

	public ManorLogServiceImpl() {
	}

	public ManorLogDao getManorLogDao() {
		return (ManorLogDao) getCommonDao();
	}

	@Autowired
	@Qualifier("manorLogDao")
	public void setManorLogDao(ManorLogDao manorLogDao) {
		setCommonDao(manorLogDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The AccountLogDao is required");
	}
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	public List<ManorLandLog> findManorLandLog(String roleId) {
		return getManorLogDao().findManorLandLog(roleId);
	}

	public List<ManorLandLog> findManorLandLog(Inquiry inquiry, String roleId) {
		return getManorLogDao().findManorLandLog(inquiry, roleId);
	}

	public int deleteManorLandLog(String roleId) {
		return getManorLogDao().deleteManorLandLog(roleId);
	}

	public List<ManorSeedLog> findManorSeedLog(String roleId) {
		return getManorLogDao().findManorSeedLog(roleId);
	}

	public List<ManorSeedLog> findManorSeedLog(Inquiry inquiry, String roleId) {
		return getManorLogDao().findManorSeedLog(inquiry, roleId);
	}

	public int deleteManorSeedLog(String roleId) {
		return getManorLogDao().deleteManorSeedLog(roleId);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 記錄開墾
	 * 
	 * @param role
	 * @param farmIndex
	 * @param land
	 * @param spendGold
	 */
	public void recordReclaim(Role role, int farmIndex, Land land, long spendGold) {
		ManorLandLog log = new ManorLandLogImpl();
		recordRole(role, log);
		//
		log.setActionType(ActionType.RECLAIM);
		log.setFarmIndex(farmIndex);
		// clone
		Land cloneLand = clone(land);
		log.setLand(cloneLand);
		//
		log.setSpendGold(spendGold);
		offerInsert(log);
	}

	/**
	 * 記錄休耕
	 * 
	 * @param role
	 * @param farmIndex
	 * @param land
	 * @param spendGold
	 */
	public void recordDisuse(Role role, int farmIndex, Land land, long spendGold) {
		ManorLandLog log = new ManorLandLogImpl();
		recordRole(role, log);
		//
		log.setActionType(ActionType.DISUSE);
		log.setFarmIndex(farmIndex);
		// clone
		Land cloneLand = clone(land);
		log.setLand(cloneLand);
		//
		log.setSpendGold(spendGold);
		offerInsert(log);
	}

	/**
	 * 記錄耕種
	 * 
	 * @param role
	 * @param cultureType
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 * @param spendItems
	 * @param spendCoin
	 */
	public void recordCulture(Role role, CultureType cultureType, int farmIndex, int gridIndex, Seed seed,
			List<Item> spendItems, int spendCoin) {
		ManorSeedLog log = new ManorSeedLogImpl();
		recordRole(role, log);
		//
		log.setCultureType(cultureType);
		log.setFarmIndex(farmIndex);
		log.setGridIndex(gridIndex);
		// clone
		Seed cloneSeed = clone(seed);
		log.setSeed(cloneSeed);
		//
		log.setSpendItems(spendItems);
		log.setSpendCoin(spendCoin);
		//
		offerInsert(log);
	}
}
