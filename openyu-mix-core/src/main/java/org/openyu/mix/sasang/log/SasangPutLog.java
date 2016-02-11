package org.openyu.mix.sasang.log;

import java.util.Map;
import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.sasang.service.SasangService.PutType;

/**
 * 四象放入的log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface SasangPutLog extends AppLogEntity
{
	String KEY = SasangPutLog.class.getName();

	/**
	 * 放入類型
	 * 
	 * @return
	 */
	PutType getPutType();

	void setPutType(PutType putType);

	/**
	 * 已放入包包的道具
	 * 
	 * @return
	 */
	Map<String, Integer> getAwards();

	void setAwards(Map<String, Integer> awards);

}
