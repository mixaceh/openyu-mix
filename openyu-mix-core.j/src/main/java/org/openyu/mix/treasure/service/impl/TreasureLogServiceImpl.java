package org.openyu.mix.treasure.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppLogServiceSupporter;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.treasure.dao.TreasureLogDao;
import org.openyu.mix.treasure.log.TreasureBuyLog;
import org.openyu.mix.treasure.log.TreasureRefreshLog;
import org.openyu.mix.treasure.log.impl.TreasureBuyLogImpl;
import org.openyu.mix.treasure.log.impl.TreasureRefreshLogImpl;
import org.openyu.mix.treasure.service.TreasureLogService;
import org.openyu.mix.treasure.service.TreasureService.BuyType;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

public class TreasureLogServiceImpl extends AppLogServiceSupporter implements
		TreasureLogService {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(TreasureLogServiceImpl.class);

	@Autowired
	@Qualifier("treasureLogDao")
	protected transient TreasureLogDao treasureLogDao;

	public TreasureLogServiceImpl() {
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------

	public List<TreasureRefreshLog> findTreasureRefreshLog(String roleId) {
		return treasureLogDao.findTreasureRefreshLog(roleId);
	}

	public List<TreasureRefreshLog> findTreasureRefreshLog(Inquiry inquiry,
			String roleId) {
		return treasureLogDao.findTreasureRefreshLog(inquiry, roleId);
	}

	public int deleteTreasureRefreshLog(String roleId) {
		return treasureLogDao.deleteTreasureRefreshLog(roleId);
	}

	public List<TreasureBuyLog> findTreasureBuyLog(String roleId) {
		return treasureLogDao.findTreasureBuyLog(roleId);
	}

	public List<TreasureBuyLog> findTreasureBuyLog(Inquiry inquiry,
			String roleId) {
		return treasureLogDao.findTreasureBuyLog(inquiry, roleId);
	}

	public int deleteTreasureBuyLog(String roleId) {
		return treasureLogDao.deleteTreasureBuyLog(roleId);
	}

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
	public void recordRefresh(Role role, long refreshTime,
			Map<Integer, Treasure> treasures, List<Item> spendItems,
			int spendCoin) {
		TreasureRefreshLog log = new TreasureRefreshLogImpl();
		recordRole(role, log);
		//
		log.setRefreshTime(refreshTime);
		// clone
		Map<Integer, Treasure> cloneTreasures = clone(treasures);
		log.setTreasures(cloneTreasures);
		//
		log.setSpendItems(spendItems);
		log.setSpendCoin(spendCoin);
		offerInsert(log);
	}

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
	public void recordBuy(Role role, BuyType buyType, int index,
			Treasure treasure, Item item, long spendGold, int spendCoin) {
		TreasureBuyLog log = new TreasureBuyLogImpl();
		recordRole(role, log);
		//
		log.setBuyType(buyType);
		log.setGridIndex(index);
		//
		log.setAmount(treasure.getAmount());
		log.setPrice(item.getPrice());
		log.setCoin(item.getCoin());
		log.setSpendGold(spendGold);
		log.setSpendCoin(spendCoin);
		// clone
		Treasure cloneTreasure = clone(treasure);
		log.setTreasure(cloneTreasure);
		Item cloneItem = clone(item);
		log.setItem(cloneItem);
		//
		offerInsert(log);
	}
}