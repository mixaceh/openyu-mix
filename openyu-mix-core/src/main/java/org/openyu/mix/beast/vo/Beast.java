package org.openyu.mix.beast.vo;

import org.openyu.mix.flutter.vo.Flutter;

/**
 * 神獸
 */
public interface Beast extends Flutter
{
	
	/**
	 * 神獸類別
	 * 
	 * @return
	 */
	BeastType getBeastType();

	void setBeastType(BeastType beastType);
}
