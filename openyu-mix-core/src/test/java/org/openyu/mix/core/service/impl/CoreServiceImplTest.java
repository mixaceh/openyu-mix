package org.openyu.mix.core.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.openyu.mix.chat.ChatTestSupporter;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.core.CoreTestSupporter;
import org.openyu.mix.core.service.CoreService.CheckRoleResult;
import org.openyu.mix.core.service.CoreService.ErrorType;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.manor.vo.ManorInfo.Farm;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.service.ManorService.CultureType;
import org.openyu.mix.role.service.RoleService.GoldType;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.wuxing.vo.WuxingInfo;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import org.openyu.commons.thread.ThreadHelper;

public class CoreServiceImplTest extends CoreTestSupporter {

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void connect() {
		final String ROLE_ID = "TEST_ROLE";
		coreService.roleConnect(ROLE_ID, null);
		// 改為CoreConnectInterceptor,用NonBlockingThreadInterceptor的方式處理
		// 會有可能拿不到傳回值,但效率增快
		// 拿傳回值也只是為了給log處理,已包含在CoreConnectInterceptor中,所以就用吧
		// System.out.println(role.getId());
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void roleConnect() {
		final String ROLE_ID = "TEST_ROLE";
		coreService.roleConnect(ROLE_ID, null);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	public void checkRole() {
		final String ROLE_ID = "TEST_ROLE";
		//
		CheckRoleResult result = coreService.checkRole(ROLE_ID);
		//
		System.out.println(result);
		assertEquals(ErrorType.NO_ERROR, result.getErrorType());

		result = coreService.checkRole("abc");
		System.out.println(result);
		assertEquals(ErrorType.ROLE_NOT_EXIST, result.getErrorType());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void disconnect() {
		final String ROLE_ID = "TEST_ROLE";
		// 連線
		coreService.roleConnect(ROLE_ID, null);
		// 讓連線的log寫入
		ThreadHelper.sleep(3 * 1000);
		//
		Role role = roleRepository.getRole(ROLE_ID);

		System.out.println(role);
		assertNotNull(role);
		// 再斷線
		coreService.roleDisconnect(ROLE_ID, null);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	@Test
	/**
	 * 模擬帳號,可建一個新測試帳號,並塞你想要的金幣,道具...等,做你愛做的事
	 */
	public void simulate() {
		final String ROLE_ID = "TEST_ROLE";
		// 連線
		coreService.roleConnect(ROLE_ID, null);
		// 讓連線的log寫入
		ThreadHelper.sleep(3 * 1000);
		//
		Role role = roleRepository.getRole(ROLE_ID);

		// 各模組
		simulateRole(role);// 角色
		simulateChat(role);// 聊天
		simulateSasang(role);// 四象
		simulateManor(role);// 莊園
		simulateTreasure(role);// 祕寶
		simulateTrain(role);// 訓練
		simulateWuxing(role);// 五行
		//
		// 再斷線
		coreService.roleDisconnect(ROLE_ID, null);
		//
		ThreadHelper.sleep(3 * 1000);
	}

	/**
	 * 模擬角色
	 * 
	 * @param role
	 */
	public void simulateRole(Role role) {
		// 1.加經驗
		role.setExp(0L);// 清經驗
		roleService.changeExp(true, role, 50000L);

		// 2.加等級
		role.setLevel(0);// 清等級
		roleService.changeLevel(true, role, 20);// lv=20

		// 3.加金幣
		role.setGold(0L);// 清金幣
		roleService.increaseGold(true, role, 50000 * 10000L,
				GoldType.DEBUG_INCREASE);// 5e

		// 4.加聲望
		role.setFame(0);// 清聲望
		roleService.changeFame(true, role, 100);

		// 5.加屬性
		roleService.changeAttribute(true, role, 1, 10, 10, 1000);

		BagInfo bagInfo = role.getBagInfo();
		bagInfo.clearItem(true);// 清包包

		// 6.加治癒藥水到包包
		Item item = itemService.createItem("T_POTION_HP_G001", 10);// 治癒藥水
		item.setUniqueId("T_POTION_HP_G001");
		itemService.increaseItem(true, role, item);

		// 7.加 E 級強化武器捲到包包
		item = itemService.createItem("T_ENHANCE_WEAPON_E_G001", 5);// E 級強化武器捲
		item.setUniqueId("T_ENHANCE_WEAPON_E_G001");
		itemService.increaseItem(true, role, item);

		// 8.加3把武器到包包
		for (int i = 0; i < 3; i++) {
			Equipment equipment = itemService
					.createEquipment("W_MARS_SWORD_E001");// E 級單手劍
			equipment.setUniqueId("W_" + i);
			itemService.increaseItem(true, role, equipment);
		}
	}

	/**
	 * 模擬聊天
	 * 
	 * @param role
	 */
	public void simulateChat(Role role) {
		ChatTestSupporter.setChatService(chatService);
		// 對方2
		Role role2 = mockRole2();
		Chat chat2 = ChatTestSupporter.mockChat2();
		// 對方3
		Role role3 = mockRole3();
		Chat chat3 = ChatTestSupporter.mockChat3();
		// 本身的聊天角色
		Chat chat = chatRepository.getChat(role.getId());

		// 1.加好友列表
		FriendGroup friendGroup = chat.getFriendGroup();
		friendGroup.addFriend(chat2, role2);

		// 2.加隔絕列表
		FriendGroup blockGroup = chat.getBlockGroup();
		blockGroup.addFriend(chat3, role3);
	}

	/**
	 * 模擬四象
	 * 
	 * @param role
	 */
	public void simulateSasang(Role role) {
		SasangInfo sasangInfo = role.getSasangInfo();
		sasangInfo.reset();
		sasangInfo.setOutcome(null);
		sasangInfo.getAwards().clear();// 清獎勵

		// 1.加四象石到包包
		Item item = itemService.createItem("T_SASANG_PLAY_G001", 10);// 四象石
		item.setUniqueId("T_SASANG_PLAY_G001");
		itemService.increaseItem(true, role, item);

		// 2.玩四象
		// @see SasangService.PlayType;

		// 青銅按鈕,可玩1次,消耗金幣,有每日次數限制
		sasangService.play(true, role, 1);

		// 銀按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		sasangService.play(true, role, 2);

	}

	/**
	 * 模擬莊園
	 * 
	 * @param role
	 */
	public void simulateManor(Role role) {
		// 1.加種子到包包
		Seed seed = itemService.createSeed("S_COTTON_G001", 10);// 棉花種子
		seed.setUniqueId("S_COTTON_G001");
		itemService.increaseItem(true, role, seed);

		// 2.加土地到包包
		Land land = itemService.createLand("L_TROPICS_G001");// 熱帶地
		land.setUniqueId("L_TROPICS_G001");
		itemService.increaseItem(true, role, land);

		// 3.加莊園加速石到包包
		itemService.increaseItemWithItemId(true, role, "T_MANOR_SPEED_G001", 1);

		// 莊園
		ManorInfo manorInfo = role.getManorInfo();
		manorInfo.clearSeed(true);// 清莊園
		Farm farm = manorInfo.getFarm(0);// 農場
		farm.setLand(null);// 清土地

		// 開墾
		manorService.reclaim(true, role, 0, land.getUniqueId());

		// 耕種
		// @see ManorService.CultureType
		manorService.culture(true, role, CultureType.PLANT.getValue(), 0, 0,
				seed.getUniqueId());
		// 澆水
		manorService.culture(true, role, CultureType.WATER.getValue(), 0, 0,
				seed.getUniqueId());
		// 祈禱
		manorService.culture(true, role, CultureType.PRAY.getValue(), 0, 0,
				seed.getUniqueId());
		// 加速
		manorService.culture(true, role, CultureType.SPEED.getValue(), 0, 0,
				seed.getUniqueId());
	}

	/**
	 * 模擬祕寶
	 * 
	 * @param role
	 */
	public void simulateTreasure(Role role) {
		// 加道具到包包
		Item item = itemService.createItem("T_TREASURE_REFRESH_G001", 10);// 祕寶刷新石
		item.setUniqueId("T_TREASURE_REFRESH_G001");
		role.getBagInfo().addItem(0, 0, item);

		// 刷新
		treasureService.refresh(true, role);
		// 購買
		treasureService.buy(true, role, 1, 0);
	}

	/**
	 * 模擬訓練
	 * 
	 * @param role
	 */
	public void simulateTrain(Role role) {
		TrainInfo trainInfo = role.getTrainInfo();
		trainInfo.reset();

		// 加道具到包包
		Item item = itemService.createItem("T_TRAIN_INSPIRE_G001", 10);// 訓練鼓舞石
		item.setUniqueId("T_TRAIN_INSPIRE_G001");
		role.getBagInfo().addItem(0, 0, item);

		// 加入
		trainService.join(true, role);
		// 鼓舞
		trainService.inspire(true, role);
		// 離開
		trainService.quit(true, role);
	}

	/**
	 * 模擬五行
	 * 
	 * @param role
	 */
	public void simulateWuxing(Role role) {
		role.setLevel(25);
		//
		WuxingInfo wuxingInfo = role.getWuxingInfo();
		wuxingInfo.reset();
		wuxingInfo.setOutcome(null);
		wuxingInfo.getAwards().clear();// 清獎勵

		// 1.加五行石到包包
		Item item = itemService.createItem("T_WUXING_PLAY_G001", 10);// 五行石
		item.setUniqueId("T_WUXING_PLAY_G001");
		itemService.increaseItem(true, role, item);

		// 2.玩五行
		// @see WuxingService.PlayType;

		// 青銅按鈕,可玩1次,消耗金幣,有每日次數限制
		wuxingService.play(true, role, 1);

		// 銀按鈕,可玩1次,消耗道具或元寶,沒有每日次數限制
		wuxingService.play(true, role, 2);

	}

}
