package org.openyu.mix.item.vo;

import org.openyu.mix.item.vo.thing.RoleExpThing;
import org.openyu.mix.item.vo.thing.RoleFameThing;
import org.openyu.mix.item.vo.thing.RoleGoldThing;
import org.openyu.commons.enumz.IntEnum;

/**
 * 物品類別
 */
public enum ThingType implements IntEnum {

	// ---------------------------------------------------
	// POTION-藥水類 紅水1-5,藍水6-10
	// ---------------------------------------------------
	/**
	 * 治癒藥水(紅水),回復hp
	 * 
	 * @see PotionHpThing
	 */
	POTION_HP(1),

	/**
	 * 立即治癒藥水(紅水),回復hp
	 * 
	 * @see PotionInstantHpThing
	 */
	POTION_INSTANT_HP(2),

	/**
	 * 魔法藥水(藍水),回復mp
	 * 
	 * @see PotionMpThing
	 */
	POTION_MP(6),

	/**
	 * 立即魔法藥水(藍水),回復mp
	 * 
	 * @see PotionInstantMpThing
	 */
	POTION_INSTANT_MP(7),

	// ---------------------------------------------------
	// BACK-返回類 ,回捲11-15
	// ---------------------------------------------------
	/**
	 * 返回城鎮捲
	 */
	BACK_TOWN(11),
	/**
	 * 立即返回城鎮捲
	 */
	BACK_INSTANT_TOWN(12),

	// ---------------------------------------------------
	// ENHANCE-強化類,防捲101-105,武捲106-110,土捲111-115
	// ---------------------------------------------------
	/**
	 * 強化防具捲(防捲)
	 * 
	 * @see EnhanceArmorThing
	 */
	ENHANCE_ARMOR(101),

	/**
	 * 強化武器捲(武捲)
	 * 
	 * @see EnhanceWeaponThing
	 */
	ENHANCE_WEAPON(106),

	/**
	 * 強化土地捲(土捲)
	 * 
	 * @see EnhanceLandThing
	 */
	ENHANCE_LAND(111),

	// ---------------------------------------------------
	// ROLE-角色類,201-220
	// ---------------------------------------------------
	/**
	 * 角色經驗道具
	 * 
	 * @see RoleExpThing
	 */
	ROLE_EXP(201),

	/**
	 * 角色技魂(sp)道具
	 * 
	 * @see RoleSpThing
	 */
	ROLE_SP(202),

	/**
	 * 角色金幣道具
	 * 
	 * @see RoleGoldThing
	 */
	ROLE_GOLD(203),

	/**
	 * 角色聲望道具
	 * 
	 * @see RoleFameThing
	 */
	ROLE_FAME(204),

	// //---------------------------------------------------
	// //各模組定義的道具,應該是不需要,不然每次都要在ThingType新增類別
	// //---------------------------------------------------
	//
	// //---------------------------------------------------
	// // TREASURE-秘寶類
	// //---------------------------------------------------
	// TREASURE(214000),

	;
	private final int value;

	private ThingType(int value) {
		this.value = value;

	}

	public int getValue() {
		return value;
	}

}