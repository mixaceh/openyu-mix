package org.openyu.mix.item.vo.thing;

import org.openyu.mix.item.vo.EnhanceType;
import org.openyu.mix.item.vo.Thing;

/**
 * 強化道具,防具/武器/土地
 * 
 * 機率/強化因子,不在這設定,而是在各collector的enhanceFactors
 * 
 * @see ArmorCollector.enhanceFactors
 * @see WeaponCollector.enhanceFactors
 * @see TrainCollector.enhanceFactors
 */
public interface EnhanceThing extends Thing
{

	/**
	 * 強化類型
	 * 
	 * @return
	 */
	EnhanceType getEnhanceType();

	void setEnhanceType(EnhanceType enhanceType);

}
