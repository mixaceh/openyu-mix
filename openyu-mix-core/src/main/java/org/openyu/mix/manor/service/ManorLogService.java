package org.openyu.mix.manor.service;

import java.util.List;

import org.openyu.mix.app.service.AppLogService;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface ManorLogService extends AppLogService {
	// --------------------------------------------------
	// db
	// --------------------------------------------------

	// --------------------------------------------------
	// ManorLandLog
	// --------------------------------------------------
	/**
	 * 查詢莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ManorLandLog> findManorLandLog(String roleId);

	/**
	 * 分頁查詢莊園土地log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ManorLandLog> findManorLandLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteManorLandLog(String roleId);

	// --------------------------------------------------
	// ManorSeedLog
	// --------------------------------------------------
	/**
	 * 查詢莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ManorSeedLog> findManorSeedLog(String roleId);

	/**
	 * 分頁查詢莊園種子log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ManorSeedLog> findManorSeedLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteManorSeedLog(String roleId);

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
	 * @return
	 */
	void recordReclaim(Role role, int farmIndex, Land land, long spendGold);

	/**
	 * 記錄休耕
	 * 
	 * @param role
	 * @param farmIndex
	 * @param land
	 * @param spendGold
	 * @return
	 */
	void recordDisuse(Role role, int farmIndex, Land land, long spendGold);

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
	 * @return
	 */
	void recordCulture(Role role, CultureType cultureType, int farmIndex,
			int gridIndex, Seed seed, List<Item> spendItems, int spendCoin);

}
