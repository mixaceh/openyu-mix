package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openyu.mix.item.vo.LevelType;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.impl.ThingImpl;
import org.openyu.mix.item.vo.thing.BackInstantTownThing;
import org.openyu.mix.item.vo.thing.BackTownThing;
import org.openyu.mix.item.vo.thing.EnhanceArmorThing;
import org.openyu.mix.item.vo.thing.EnhanceLandThing;
import org.openyu.mix.item.vo.thing.EnhanceWeaponThing;
import org.openyu.mix.item.vo.thing.PotionHpThing;
import org.openyu.mix.item.vo.thing.PotionInstantHpThing;
import org.openyu.mix.item.vo.thing.PotionInstantMpThing;
import org.openyu.mix.item.vo.thing.PotionMpThing;
import org.openyu.mix.item.vo.thing.RoleExpThing;
import org.openyu.mix.item.vo.thing.RoleFameThing;
import org.openyu.mix.item.vo.thing.RoleGoldThing;
import org.openyu.mix.item.vo.thing.RoleSpThing;
import org.openyu.mix.item.vo.thing.impl.BackInstantTownThingImpl;
import org.openyu.mix.item.vo.thing.impl.BackTownThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceArmorThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceLandThingImpl;
import org.openyu.mix.item.vo.thing.impl.EnhanceWeaponThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionHpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionInstantHpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionInstantMpThingImpl;
import org.openyu.mix.item.vo.thing.impl.PotionMpThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleExpThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleFameThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleGoldThingImpl;
import org.openyu.mix.item.vo.thing.impl.RoleSpThingImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class ThingCollectorTest extends BeanTestSupporter{

	/**
	 * 模擬治癒藥水道具
	 * 
	 * @return
	 */
	public static List<PotionHpThing> mockPotionHpThing() {
		List<PotionHpThing> result = new LinkedList<PotionHpThing>();
		//
		PotionHpThing thing = new PotionHpThingImpl("T_POTION_HP_G001");// T_道具類別
		thing.setName("治癒藥水");
		thing.setDescription("可少量恢復體力");
		thing.setRate(100);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(1000L);// 0.1w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionHpThingImpl("T_POTION_HP_G002");// T_道具類別
		thing.setName("強力治癒藥水");
		thing.setDescription("可強力恢復體力");
		thing.setRate(200);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(2000L);// 0.2w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionHpThingImpl("T_POTION_HP_G003");// T_道具類別
		thing.setName("終極治癒藥水");
		thing.setDescription("可大量恢復體力");
		thing.setRate(300);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(3000L);// 0.3w
		thing.setCoin(1);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬魔法藥水道具
	 * 
	 * @return
	 */
	public static List<PotionMpThing> mockPotionMpThing() {
		List<PotionMpThing> result = new LinkedList<PotionMpThing>();
		//
		PotionMpThing thing = new PotionMpThingImpl("T_POTION_MP_G001");// T_道具類別
		thing.setName("魔法藥水");
		thing.setDescription("可少量恢復魔力");
		thing.setRate(50);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(1000L);// 0.1w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionMpThingImpl("T_POTION_MP_G002");// T_道具類別
		thing.setName("強力魔法藥水");
		thing.setDescription("可強力恢復魔力");
		thing.setRate(100);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(2000L);// 0.2w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionMpThingImpl("T_POTION_MP_G003");// T_道具類別
		thing.setName("終極魔法藥水");
		thing.setDescription("可大量恢復魔力");
		thing.setRate(150);// 每秒恢復比率
		thing.setSecond(10);// 持續秒數
		thing.setPrice(3000L);// 0.3w
		thing.setCoin(1);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬立即治癒藥水道具
	 * 
	 * @return
	 */
	public static List<PotionInstantHpThing> mockPotionInstantHpThing() {
		List<PotionInstantHpThing> result = new LinkedList<PotionInstantHpThing>();
		//
		PotionInstantHpThing thing = new PotionInstantHpThingImpl(
				"T_POTION_INSTANT_HP_G001");// T_道具類別
		thing.setName("立即治癒藥水");
		thing.setRate(1000);// 恢復比率
		thing.setPrice(5 * 10000L);// 5w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionInstantHpThingImpl("T_POTION_INSTANT_HP_G002");// T_道具類別
		thing.setName("立即強力治癒藥水");
		thing.setRate(2000);// 恢復比率
		thing.setPrice(10 * 10000L);// 10w
		thing.setCoin(2);
		result.add(thing);
		//
		thing = new PotionInstantHpThingImpl("T_POTION_INSTANT_HP_G003");// T_道具類別
		thing.setName("立即終極治癒藥水");
		thing.setRate(3000);// 恢復比率
		thing.setPrice(30 * 10000L);// 30w
		thing.setCoin(6);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬立即魔法藥水道具
	 * 
	 * @return
	 */
	public static List<PotionInstantMpThing> mockPotionInstantMpThing() {
		List<PotionInstantMpThing> result = new LinkedList<PotionInstantMpThing>();
		//
		PotionInstantMpThing thing = new PotionInstantMpThingImpl(
				"T_POTION_INSTANT_MP_G001");// T_道具類別
		thing.setName("立即魔法藥水");
		thing.setRate(1000);// 恢復比率
		thing.setPrice(5 * 10000L);// 5w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new PotionInstantMpThingImpl("T_POTION_INSTANT_MP_G002");// T_道具類別
		thing.setName("立即強力魔法藥水");
		thing.setRate(2000);// 恢復比率
		thing.setPrice(10 * 10000L);// 10w
		thing.setCoin(2);
		result.add(thing);
		//
		thing = new PotionInstantMpThingImpl("T_POTION_INSTANT_MP_G003");// T_道具類別
		thing.setName("立即終極魔法藥水");
		thing.setRate(3000);// 恢復比率
		thing.setPrice(30 * 10000L);// 30w
		thing.setCoin(6);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬返回城鎮道具
	 * 
	 * @return
	 */
	public static List<BackTownThing> mockBackTownThing() {
		List<BackTownThing> result = new LinkedList<BackTownThing>();
		//
		BackTownThing thing = new BackTownThingImpl("T_BACK_TOWN_G001");// T_道具類別
		thing.setName("返回城鎮捲");
		thing.setSecond(10);// 等待秒數
		thing.setPrice(1000L);// 0.1w
		thing.setCoin(1);
		result.add(thing);
		//
		thing = new BackTownThingImpl("T_BACK_TOWN_G002");// T_道具類別
		thing.setName("返回盟屋捲");
		thing.setSecond(10);// 等待秒數
		thing.setPrice(1000L);// 0.1w
		thing.setCoin(1);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬立即返回城鎮道具
	 * 
	 * @return
	 */
	public static List<BackInstantTownThing> mockBackInstantTownThing() {
		List<BackInstantTownThing> result = new LinkedList<BackInstantTownThing>();
		//
		BackInstantTownThing thing = new BackInstantTownThingImpl(
				"T_BACK_INSTANT_TOWN_G001");// T_道具類別
		thing.setName("立即返回城鎮捲");
		thing.setPrice(30 * 10000L);// 30w
		thing.setCoin(6);
		result.add(thing);
		//
		thing = new BackInstantTownThingImpl("T_BACK_INSTANT_TOWN_G002");// T_道具類別
		thing.setName("立即返回盟屋捲");
		thing.setPrice(30 * 10000L);// 30w
		thing.setCoin(6);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬強化防具道具
	 * 
	 * @return
	 */
	public static List<EnhanceArmorThing> mockEnhanceArmorThing() {
		List<EnhanceArmorThing> result = new LinkedList<EnhanceArmorThing>();

		// e防捲
		EnhanceArmorThing thing = new EnhanceArmorThingImpl(
				"T_ENHANCE_ARMOR_E_G001");// T_道具類別
		thing.setName("E 級強化防具捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(250 * 10000L);// 250w
		thing.setCoin(50);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_E_B001");// T_道具類別
		thing.setName("祝福 E 級強化防具捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(1000 * 10000L);// 1000w
		thing.setCoin(200);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_E_F001");// T_道具類別
		thing.setName("幻想 E 級強化防具捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(2500 * 10000L);// 2500w
		thing.setCoin(500);
		result.add(thing);

		// d防捲
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_D_G001");// T_道具類別
		thing.setName("D 級強化防具捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(500 * 10000L);// 500w
		thing.setCoin(100);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_D_B001");// T_道具類別
		thing.setName("祝福 D 級強化防具捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(2000 * 10000L);// 2000w
		thing.setCoin(400);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_D_F001");// T_道具類別
		thing.setName("幻想 D 級強化防具捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(5000 * 10000L);// 5000w
		thing.setCoin(1000);
		result.add(thing);

		// c防捲
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_C_G001");// T_道具類別
		thing.setName("C 級強化防具捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(1000 * 10000L);// 1000w
		thing.setCoin(200);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_C_B001");// T_道具類別
		thing.setName("祝福 C 級強化防具捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(4000 * 10000L);// 4000w
		thing.setCoin(800);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_C_F001");// T_道具類別
		thing.setName("幻想 C 級強化防具捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(10000 * 10000L);// 1e
		thing.setCoin(2000);
		result.add(thing);

		// b防捲
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_B_G001");// T_道具類別
		thing.setName("B 級強化防具捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(2000 * 10000L);// 2000w
		thing.setCoin(400);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_B_B001");// T_道具類別
		thing.setName("祝福 B 級強化防具捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(8000 * 10000L);// 8000w
		thing.setCoin(1600);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_B_F001");// T_道具類別
		thing.setName("幻想 B 級強化防具捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(20000 * 10000L);// 2e
		thing.setCoin(4000);
		result.add(thing);

		// a防捲
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_A_G001");// T_道具類別
		thing.setName("A 級強化防具捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(4000 * 10000L);// 4000w
		thing.setCoin(800);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_A_B001");// T_道具類別
		thing.setName("祝福 A 級強化防具捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(16000 * 10000L);// 1.6e
		thing.setCoin(3200);
		result.add(thing);
		//
		thing = new EnhanceArmorThingImpl("T_ENHANCE_ARMOR_A_F001");// T_道具類別
		thing.setName("幻想 A 級強化防具捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(32000 * 10000L);// 3.2e
		thing.setCoin(8000);
		result.add(thing);
		//
		return result;
	}

	/**
	 * 模擬強化武器道具
	 * 
	 * @return
	 */
	public static List<EnhanceWeaponThing> mockEnhanceWeaponThing() {
		List<EnhanceWeaponThing> result = new LinkedList<EnhanceWeaponThing>();
		// e武捲
		EnhanceWeaponThing thing = new EnhanceWeaponThingImpl(
				"T_ENHANCE_WEAPON_E_G001");// T_道具類別
		thing.setName("E 級強化武器捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(500 * 10000L);// 500w
		thing.setCoin(100);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_E_B001");// T_道具類別
		thing.setName("祝福 E 級強化武器捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(2000 * 10000L);// 2000w
		thing.setCoin(400);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_E_F001");// T_道具類別
		thing.setName("幻想 E 級強化武器捲");
		thing.setLevelType(LevelType.E);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(5000 * 10000L);// 5000w
		thing.setCoin(1000);
		result.add(thing);

		// d武捲
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_D_G001");// T_道具類別
		thing.setName("D 級強化武器捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(1000 * 10000L);// 1000w
		thing.setCoin(200);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_D_B001");// T_道具類別
		thing.setName("祝福 D 級強化武器捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(4000 * 10000L);// 4000w
		thing.setCoin(800);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_D_F001");// T_道具類別
		thing.setName("幻想 D 級強化武器捲");
		thing.setLevelType(LevelType.D);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(10000 * 10000L);// 1e
		thing.setCoin(2000);
		result.add(thing);
		// c武捲
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_C_G001");// T_道具類別
		thing.setName("C 級強化武器捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(2000 * 10000L);// 2000w
		thing.setCoin(400);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_C_B001");// T_道具類別
		thing.setName("祝福 C 級強化武器捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(8000 * 10000L);// 8000w
		thing.setCoin(1600);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_C_F001");// T_道具類別
		thing.setName("幻想 C 級強化武器捲");
		thing.setLevelType(LevelType.C);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(20000 * 10000L);// 2e
		thing.setCoin(4000);
		result.add(thing);
		// b武捲
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_B_G001");// T_道具類別
		thing.setName("B 級強化武器捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(4000 * 10000L);// 4000w
		thing.setCoin(800);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_B_B001");// T_道具類別
		thing.setName("祝福 B 級強化武器捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(16000 * 10000L);// 1.6e
		thing.setCoin(3200);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_B_F001");// T_道具類別
		thing.setName("幻想 B 級強化武器捲");
		thing.setLevelType(LevelType.B);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(40000 * 10000L);// 4e
		thing.setCoin(8000);
		result.add(thing);
		// a武捲
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_A_G001");// T_道具類別
		thing.setName("A 級強化武器捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(8000 * 10000L);// 8000w
		thing.setCoin(1600);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_A_B001");// T_道具類別
		thing.setName("祝福 A 級強化武器捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(32000 * 10000L);// 3.2e
		thing.setCoin(6400);
		result.add(thing);
		//
		thing = new EnhanceWeaponThingImpl("T_ENHANCE_WEAPON_A_F001");// T_道具類別
		thing.setName("幻想 A 級強化武器捲");
		thing.setLevelType(LevelType.A);// 裝備等級
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(80000 * 10000L);// 8e
		thing.setCoin(16000);
		result.add(thing);
		return result;
	}

	/**
	 * 模擬強化土地道具
	 * 
	 * @return
	 */
	public static List<EnhanceLandThing> mockEnhanceLandThing() {
		List<EnhanceLandThing> result = new LinkedList<EnhanceLandThing>();
		// 土地捲
		EnhanceLandThing thing = new EnhanceLandThingImpl("T_ENHANCE_LAND_G001");// T_道具類別
		thing.setName("強化土地捲");
		thing.setEnhanceType(EnhanceType.GENERAL);// 強化類型
		thing.setPrice(5000 * 10000L);// 5000w
		thing.setCoin(1000);
		result.add(thing);
		//
		thing = new EnhanceLandThingImpl("T_ENHANCE_LAND_B001");// T_道具類別
		thing.setName("祝福強化土地捲");
		thing.setEnhanceType(EnhanceType.BLESS);// 強化類型
		thing.setPrice(20000 * 10000L);// 2e
		thing.setCoin(4000);
		result.add(thing);
		//
		thing = new EnhanceLandThingImpl("T_ENHANCE_LAND_F001");// T_道具類別
		thing.setName("幻想強化土地捲");
		thing.setEnhanceType(EnhanceType.FANTASY);// 強化類型
		thing.setPrice(50000 * 10000L);// 5e
		thing.setCoin(10000);
		result.add(thing);

		return result;
	}

	/**
	 * 模擬角色經驗道具
	 * 
	 * 一般 GENERAL 5*.8=4
	 * 
	 * 想願 WILLING 15*.5=7.5
	 * 
	 * 憧憬 YEARN 50*.3=15
	 * 
	 * @param thingId
	 */
	public static List<RoleExpThing> mockRoleExpThing() {
		List<RoleExpThing> result = new LinkedList<RoleExpThing>();
		//
		RoleExpThing thing = new RoleExpThingImpl("T_ROLE_EXP_G001");// T_道具類別
		thing.setName("經驗之力");
		thing.setExp(6 * 10000L);// 6w
		thing.setProbability(8000);// 成功機率
		thing.setPrice(50 * 10000L);// 50w
		thing.setCoin(10);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleExpThingImpl("T_ROLE_EXP_G002");// T_道具類別
		thing.setName("想願經驗之力");
		thing.setExp(27 * 10000L);// 27w
		thing.setProbability(5000);// 成功機率
		thing.setPrice(150 * 10000L);// 150w
		thing.setCoin(30);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleExpThingImpl("T_ROLE_EXP_G003");// T_道具類別
		thing.setName("憧憬經驗之力");
		thing.setExp(150 * 10000L);// 150w
		thing.setProbability(3000);// 成功機率
		thing.setPrice(500 * 10000L);// 500w
		thing.setCoin(100);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		return result;
	}

	/**
	 * 模擬角色技魂(sp)道具
	 * 
	 * 一般 GENERAL 5*.8=4
	 * 
	 * 想願 WILLING 15*.5=7.5
	 * 
	 * 憧憬 YEARN 50*.3=15
	 * 
	 * @param thingId
	 */
	public static List<RoleSpThing> mockRoleSpThing() {
		List<RoleSpThing> result = new LinkedList<RoleSpThing>();
		//
		RoleSpThing thing = new RoleSpThingImpl("T_ROLE_SP_G001");// T_道具類別
		thing.setName("技魂之力");
		thing.setSp(3 * 10000L);// 3w
		thing.setProbability(8000);// 成功機率
		thing.setPrice(25 * 10000L);// 25w
		thing.setCoin(5);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleSpThingImpl("T_ROLE_SP_G002");// T_道具類別
		thing.setName("想願技魂之力");
		thing.setSp(12 * 10000L);// 12w
		thing.setProbability(5000);// 成功機率
		thing.setPrice(75 * 10000L);// 75w
		thing.setCoin(15);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleSpThingImpl("T_ROLE_SP_G003");// T_道具類別
		thing.setName("憧憬技魂之力");
		thing.setSp(70 * 10000L);// 70w
		thing.setProbability(3000);// 成功機率
		thing.setPrice(250 * 10000L);// 250w
		thing.setCoin(50);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		return result;
	}

	/**
	 * 模擬角色金幣道具
	 * 
	 * 一般 GENERAL 5*.8=4
	 * 
	 * 想願 WILLING 15*.5=7.5
	 * 
	 * 憧憬 YEARN 50*.3=15
	 * 
	 * @param thingId
	 * @return
	 */
	public static List<RoleGoldThing> mockRoleGoldThing() {
		List<RoleGoldThing> result = new LinkedList<RoleGoldThing>();
		//
		RoleGoldThing thing = new RoleGoldThingImpl("T_ROLE_GOLD_G001");// T_道具類別
		thing.setName("黃金之力");
		thing.setGold(60 * 10000L);// 60w
		thing.setProbability(8000);// 成功機率
		thing.setPrice(50 * 10000L);// 50w
		thing.setCoin(10);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleGoldThingImpl("T_ROLE_GOLD_G002");// T_道具類別
		thing.setName("想願黃金之力");
		thing.setGold(270 * 10000L);// 270w
		thing.setProbability(5000);// 成功機率
		thing.setPrice(150 * 10000L);// 150w
		thing.setCoin(30);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleGoldThingImpl("T_ROLE_GOLD_G003");// T_道具類別
		thing.setName("憧憬黃金之力");
		thing.setGold(1500 * 10000L);// 1500w
		thing.setProbability(3000);// 成功機率
		thing.setPrice(500 * 10000L);// 500w
		thing.setCoin(100);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		return result;
	}

	/**
	 * 模擬角色聲望道具
	 * 
	 * 一般 GENERAL 10*.8=8
	 * 
	 * 想願 WILLING 30*.5=15
	 * 
	 * 憧憬 YEARN 100*.3=30
	 * 
	 * @param thingId
	 *            T_ROLE_GOLD_G001
	 * @return
	 */
	public static List<RoleFameThing> mockRoleFameThing() {
		List<RoleFameThing> result = new LinkedList<RoleFameThing>();
		//
		RoleFameThing thing = new RoleFameThingImpl("T_ROLE_FAME_G001");// T_道具類別
		thing.setName("聲望之力");
		thing.setFame(6);
		thing.setProbability(8000);// 成功機率
		thing.setPrice(50 * 10000L);// 50w
		thing.setCoin(10);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleFameThingImpl("T_ROLE_FAME_G002");// T_道具類別
		thing.setName("想願聲望之力");
		thing.setFame(27);
		thing.setProbability(5000);// 成功機率
		thing.setPrice(150 * 10000L);// 150w
		thing.setCoin(30);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		//
		thing = new RoleFameThingImpl("T_ROLE_FAME_G003");// T_道具類別
		thing.setName("憧憬聲望之力");
		thing.setFame(150);
		thing.setProbability(3000);// 成功機率
		thing.setPrice(500 * 10000L);// 500w
		thing.setCoin(100);
		thing.setTied(true);// 綁定(束缚)
		result.add(thing);
		return result;
	}

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		ThingCollector collector = ThingCollector.getInstance(false);
		//
		long beg = System.currentTimeMillis();
		Thing thing = null;

		// 模擬治癒藥水道具
		List<PotionHpThing> potionHpThings = mockPotionHpThing();
		collector.addThings(potionHpThings);

		// 模擬魔法藥水道具
		List<PotionMpThing> potionMpThings = mockPotionMpThing();
		collector.addThings(potionMpThings);

		// 模擬立即治癒藥水道具
		List<PotionInstantHpThing> potionInstantHpThings = mockPotionInstantHpThing();
		collector.addThings(potionInstantHpThings);

		// 模擬立即魔法藥水道具
		List<PotionInstantMpThing> potionInstantMpThings = mockPotionInstantMpThing();
		collector.addThings(potionInstantMpThings);

		// 模擬返回城鎮道具
		List<BackTownThing> backTownThings = mockBackTownThing();
		collector.addThings(backTownThings);

		// 模擬立即返回城鎮道具
		List<BackInstantTownThing> backInstantTownThings = mockBackInstantTownThing();
		collector.addThings(backInstantTownThings);

		// 模擬強化防具道具
		List<EnhanceArmorThing> enhanceArmorThings = mockEnhanceArmorThing();
		collector.addThings(enhanceArmorThings);

		// 模擬強化武器道具
		List<EnhanceWeaponThing> enhanceWeaponThings = mockEnhanceWeaponThing();
		collector.addThings(enhanceWeaponThings);

		// 模擬強化土地道具
		List<EnhanceLandThing> enhanceLandThings = mockEnhanceLandThing();
		collector.addThings(enhanceLandThings);

		// 模擬角色經驗道具
		List<RoleExpThing> roleExpThings = mockRoleExpThing();
		collector.addThings(roleExpThings);

		// 模擬角色技魂(sp)道具
		List<RoleSpThing> roleSpThings = mockRoleSpThing();
		collector.addThings(roleSpThings);

		// 模擬角色金幣道具
		List<RoleGoldThing> roleGoldThings = mockRoleGoldThing();
		collector.addThings(roleGoldThings);

		// 模擬角色聲望道具
		List<RoleFameThing> roleFameThings = mockRoleFameThing();
		collector.addThings(roleFameThings);
		//
		thing = new ThingImpl("T_SASANG_PLAY_G001");
		thing.setName("四象石");
		thing.setDescription("四象消耗的道具");
		thing.setPrice(250 * 10000L);// 250w
		thing.setCoin(50);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_MANOR_WATER_G001");
		thing.setName("莊園澆水石");
		thing.setDescription("莊園澆水消耗的道具");
		thing.setPrice(10 * 10000L);// 10w
		thing.setCoin(2);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_MANOR_PRAY_G001");
		thing.setName("莊園祈禱石");
		thing.setDescription("莊園祈禱消耗的道具");
		thing.setPrice(25 * 10000L);// 25w
		thing.setCoin(5);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_MANOR_SPEED_G001");
		thing.setName("莊園加速石");
		thing.setDescription("莊園加速消耗的道具");
		thing.setPrice(75 * 10000L);// 75w
		thing.setCoin(15);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_MANOR_REVIVE_G001");
		thing.setName("莊園復活石");
		thing.setDescription("莊園復活速消耗的道具");
		thing.setPrice(25 * 10000L);// 25w
		thing.setCoin(5);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_TREASURE_REFRESH_G001");
		thing.setName("祕寶刷新石");
		thing.setDescription("祕寶刷新消耗的道具");
		thing.setPrice(250 * 10000L);// 250w
		thing.setCoin(50);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_TRAIN_INSPIRE_G001");
		thing.setName("訓練鼓舞石");
		thing.setDescription("訓練鼓舞消耗的道具");
		thing.setPrice(1000 * 10000L);// 1000w
		thing.setCoin(200);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_WUXING_PLAY_G001");
		thing.setName("五行石");
		thing.setDescription("五行消耗的道具");
		thing.setPrice(350 * 10000L);// 350w
		thing.setCoin(70);
		collector.getThings().put(thing.getId(), thing);
		//
		thing = new ThingImpl("T_QIXING_PLAY_G001");
		thing.setName("七星石");
		thing.setDescription("七星消耗的道具");
		thing.setPrice(200 * 10000L);// 200w
		thing.setCoin(40);
		collector.getThings().put(thing.getId(), thing);
		//
		result = collector.writeToXml(ThingCollector.class, collector);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		ThingCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(ThingCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		long beg = System.currentTimeMillis();
		result = beanCollector.writeToSerFromXml(ThingCollector.class);
		//
		long end = System.currentTimeMillis();
		System.out.println((end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		ThingCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(ThingCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 399 mills.
	// 1000000 times: 398 mills.
	// 1000000 times: 401 mills.
	public void initialize() {
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			ThingCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = ThingCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(ThingCollector.getInstance().getThingIds());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		ThingCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 10000 times: 1587 mills.
	// 10000 times: 1583 mills.
	// 10000 times: 1683 mills.
	public void reset() {
		ThingCollector collector = ThingCollector.getInstance();
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
		//
		assertEquals(0, collector.getThings().size());
	}

	@Test
	// 1000000 times: 396 mills.
	// 1000000 times: 393 mills.
	// 1000000 times: 434 mills.
	// verified
	public void getThingTypes() {
		Set<ThingType> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().getThingTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getThings() {
		Map<String, Thing> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().getThings();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 2589 mills.
	// 1000000 times: 2585 mills.
	// 1000000 times: 2533 mills.
	// verified
	public void getThing() {
		Thing result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().getThing("T_POTION_HP_G001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ThingType.POTION_HP, result.getThingType());
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void createThing() {
		Thing result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().createThing("T_PH_G001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ThingType.POTION_HP, result.getThingType());
		//
		result = ThingCollector.getInstance().createThing("NOT_EXIST");
		System.out.println(result);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getThingsByThingType() {
		List<Thing> result = null;
		//
		int count = 1; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance()
					.getThings(ThingType.POTION_HP);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void containThing() {
		boolean result = false;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().containThing("T_PH_G001");
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result);
		//
		result = ThingCollector.getInstance().containThing("NOT_EXIST");
		assertFalse(result);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getThingIds() {
		List<String> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = ThingCollector.getInstance().getThingIds();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
