package org.openyu.mix.manor.service.socklet;

import java.util.List;

import org.openyu.mix.app.socklet.supporter.AppSockletServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.IncreaseItemResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.manor.service.ManorService.CultureResult;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.manor.service.ManorService.ErrorType;
import org.openyu.mix.manor.service.impl.ManorServiceImpl.CultureResultImpl;
import org.openyu.mix.manor.vo.ManorCollector;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ManorSocklet extends AppSockletServiceSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorSocklet.class);

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("manorService")
	protected transient ManorService manorService;

	private transient ManorCollector manorCollector = ManorCollector
			.getInstance();

	public ManorSocklet() {
	}

	public void service(Message message) {
		super.service(message);

		// 訊息
		CoreMessageType messageType = (CoreMessageType) message
				.getMessageType();

		switch (messageType) {
		case MANOR_RECLAIM_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			String landUniqueId = message.getString(2);
			//
			Role role = checkRole(roleId);
			manorService.reclaim(true, role, farmIndex, landUniqueId);
			break;
		}
		case MANOR_DISUSE_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			//
			Role role = checkRole(roleId);
			manorService.disuse(true, role, farmIndex);
			break;
		}
		case MANOR_CULTURE_REQUEST: {
			String roleId = message.getString(0);
			int cultureValue = message.getInt(1);
			int farmIndex = message.getInt(2);
			int gridIndex = message.getInt(3);
			String seedUniqueId = message.getString(4);
			//
			Role role = checkRole(roleId);
			manorService.culture(true, role, cultureValue, farmIndex,
					gridIndex, seedUniqueId);
			break;
		}

		// --------------------------------------------------
		// 祕技
		// --------------------------------------------------
		// 祕技莊園開墾
		case MANOR_DEBUG_RECLAIM_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			String itemId = message.getString(2);
			//
			Role role = checkRole(roleId);
			DEBUG_reclaim(role, farmIndex, itemId);
			break;
		}
		// 祕技莊園休耕
		case MANOR_DEBUG_DISUSE_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			//
			Role role = checkRole(roleId);
			DEBUG_disuse(role, farmIndex);
			break;
		}
		// 祕技莊園種植
		case MANOR_DEBUG_PLANT_REQUEST: {
			String roleId = message.getString(0);
			String itemId = message.getString(1);
			//
			Role role = checkRole(roleId);
			DEBUG_plant(role, itemId);
			break;
		}
		// 祕技莊園澆水
		case MANOR_DEBUG_WATER_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_water(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園祈禱
		case MANOR_DEBUG_PRAY_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_pray(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園加速
		case MANOR_DEBUG_SPEED_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_speed(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園收割
		case MANOR_DEBUG_HARVEST_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_harvest(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園復活
		case MANOR_DEBUG_REVIVE_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_revive(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園清除
		case MANOR_DEBUG_CLEAR_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_clear(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園成熟
		case MANOR_DEBUG_MATURE_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_mature(role, farmIndex, gridIndex);
			break;
		}
		// 祕技莊園枯萎
		case MANOR_DEBUG_WITHER_REQUEST: {
			String roleId = message.getString(0);
			int farmIndex = message.getInt(1);
			int gridIndex = message.getInt(2);
			//
			Role role = checkRole(roleId);
			DEBUG_wither(role, farmIndex, gridIndex);
			break;
		}
		default: {
			LOGGER.error("Can't resolve: " + message);
			break;
		}
		}
	}

	// --------------------------------------------------
	// 祕技
	// --------------------------------------------------
	/**
	 * 祕技莊園開墾
	 * 
	 * @param role
	 * @param farmIndex
	 * @param itemId
	 */
	protected void DEBUG_reclaim(Role role, int farmIndex, String itemId) {
		// 不是土地
		boolean contains = manorCollector.containLand(itemId);
		if (!contains) {
			return;
		}

		// 從包包拿道具,若沒有道具,就塞1個道具到包包
		Item item = doGetItem(role, itemId);
		if (item != null) {
			manorService.reclaim(true, role, farmIndex, item.getUniqueId());
		}
	}

	/**
	 * 祕技莊園休耕
	 * 
	 * @param role
	 * @param farmIndex
	 */
	protected void DEBUG_disuse(Role role, int farmIndex) {
		// 所有
		if (farmIndex == -1) {
			for (Integer index : role.getManorPen().getFarmIndexs()) {
				manorService.disuse(true, role, index);
			}
		} else {
			manorService.disuse(true, role, farmIndex);
		}
	}

	/**
	 * 祕技莊園種植
	 * 
	 * @param role
	 * @param itemId
	 */
	protected void DEBUG_plant(Role role, String itemId) {
		// 不是種子
		boolean contains = manorCollector.containSeed(itemId);
		if (!contains) {
			return;
		}

		// 莊園
		ManorPen manorPen = role.getManorPen();
		// 取得一個空格子的index,無放入任何種子
		int[] emptyIndex = manorPen.getEmptyIndex();
		// 拿不到空格,就是都種滿了
		if (emptyIndex == null) {
			return;
		}

		// 從包包拿道具,若沒有道具,就塞1個道具到包包
		Item item = doGetItem(role, itemId);
		if (item != null) {
			manorService.culture(true, role, CultureType.PLANT.getValue(),
					emptyIndex[0], emptyIndex[1], item.getUniqueId());
		}
	}

	/**
	 * 祕技莊園澆水
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_water(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			manorService.cultureAll(true, role, CultureType.WATER.getValue());
		} else {
			manorService.culture(true, role, CultureType.WATER.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園祈禱
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_pray(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			manorService.cultureAll(true, role, CultureType.PRAY.getValue());
		} else {
			manorService.culture(true, role, CultureType.PRAY.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園加速
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_speed(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : role.getManorPen().getIndexs()) {
				manorService.culture(true, role, CultureType.SPEED.getValue(),
						index[0], index[1], null);
			}
		} else {
			manorService.culture(true, role, CultureType.SPEED.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園收割
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_harvest(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : role.getManorPen().getIndexs()) {
				manorService.culture(true, role,
						CultureType.HARVEST.getValue(), index[0], index[1],
						null);
			}
		} else {
			manorService.culture(true, role, CultureType.HARVEST.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園復活
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_revive(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : role.getManorPen().getIndexs()) {
				manorService.culture(true, role, CultureType.REVIVE.getValue(),
						index[0], index[1], null);
			}
		} else {
			manorService.culture(true, role, CultureType.REVIVE.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園清除
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_clear(Role role, int farmIndex, int gridIndex) {
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : role.getManorPen().getIndexs()) {
				manorService.culture(true, role, CultureType.CLEAR.getValue(),
						index[0], index[1], null);
			}
		} else {
			manorService.culture(true, role, CultureType.CLEAR.getValue(),
					farmIndex, gridIndex, null);
		}
	}

	/**
	 * 祕技莊園成熟
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_mature(Role role, int farmIndex, int gridIndex) {
		ManorPen manorPen = role.getManorPen();
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : manorPen.getIndexs()) {
				Seed seed = manorPen.getSeed(index[0], index[1]);
				doMature(role, index[0], index[1], seed);
			}
		} else {
			Seed seed = manorPen.getSeed(farmIndex, gridIndex);
			doMature(role, farmIndex, gridIndex, seed);
		}
	}

	/**
	 * 祕技搞成成熟
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 */
	protected void doMature(Role role, int farmIndex, int gridIndex, Seed seed) {
		if (seed == null || !MatureType.GROWING.equals(seed.getMatureType())) {
			return;
		}
		// 成熟
		seed.setMatureType(MatureType.MATURE);
		seed.setMatureTime(System.currentTimeMillis());

		// 發訊息
		CultureResult cultureResult = new CultureResultImpl(CultureType.PLANT,
				farmIndex, gridIndex, seed, 0);
		manorService.sendCulture(ErrorType.NO_ERROR, role, cultureResult);
	}

	/**
	 * 祕技莊園枯萎
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 */
	protected void DEBUG_wither(Role role, int farmIndex, int gridIndex) {
		ManorPen manorPen = role.getManorPen();
		// 所有
		if (farmIndex == -1 || gridIndex == -1) {
			for (int[] index : manorPen.getIndexs()) {
				Seed seed = manorPen.getSeed(index[0], index[1]);
				doWither(role, index[0], index[1], seed);
			}
		} else {
			Seed seed = manorPen.getSeed(farmIndex, gridIndex);
			doWither(role, farmIndex, gridIndex, seed);
		}
	}

	/**
	 * 祕技搞成枯萎
	 * 
	 * @param role
	 * @param farmIndex
	 * @param gridIndex
	 * @param seed
	 */
	protected void doWither(Role role, int farmIndex, int gridIndex, Seed seed) {
		if (seed == null
				|| !(MatureType.GROWING.equals(seed.getMatureType()) || MatureType.MATURE
						.equals(seed.getMatureType()))) {
			return;
		}
		// 枯萎
		seed.setMatureType(MatureType.WITHER);
		seed.setMatureTime(System.currentTimeMillis());

		// 發訊息
		CultureResult cultureResult = new CultureResultImpl(CultureType.PLANT,
				farmIndex, gridIndex, seed, 0);
		manorService.sendCulture(ErrorType.NO_ERROR, role, cultureResult);
	}

	/**
	 * 從包包拿道具,若沒有道具,就塞1個道具到包包
	 * 
	 * @param role
	 * @param itemId
	 * @return
	 */
	protected Item doGetItem(Role role, String itemId) {
		Item result = null;
		// 包包
		BagPen bagPen = role.getBagPen();
		// 此道具id的所有道具
		List<Item> items = bagPen.getItems(itemId);
		// 包包有道具,拿第1個來用
		if (items.size() > 0) {
			result = items.get(0);
		}
		// 若沒有道具,塞1個道具到包包
		else {
			int amount = 1;// 數量
			BagPen.ErrorType bagError = itemService.checkIncreaseItem(role,
					itemId, amount);
			if (bagError != BagPen.ErrorType.NO_ERROR) {
				return result;
			}
			//
			List<IncreaseItemResult> increaseResults = itemService
					.increaseItem(false, role, itemId, amount);
			if (increaseResults.size() > 0) {
				IncreaseItemResult increaseResult = increaseResults.get(0);
				int tabIndex = increaseResult.getTabIndex();
				int gridIndex = increaseResult.getGridIndex();
				//
				result = bagPen.getItem(tabIndex, gridIndex);
			}
		}
		return result;
	}

}
