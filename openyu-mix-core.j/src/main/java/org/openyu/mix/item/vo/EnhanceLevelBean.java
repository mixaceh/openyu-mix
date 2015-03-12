package org.openyu.mix.item.vo;

import org.openyu.commons.bean.BaseBean;

/**
 * 強化等級
 */
public interface EnhanceLevelBean extends BaseBean
{
	String KEY = EnhanceLevelBean.class.getName();

	/**
	 * 強化等級(玩家)
	 * 
	 * @return
	 */
	EnhanceLevel getEnhanceLevel();

	void setEnhanceLevel(EnhanceLevel enhanceLevel);
}
