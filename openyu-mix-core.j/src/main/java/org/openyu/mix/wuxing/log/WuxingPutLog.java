package org.openyu.mix.wuxing.log;

import java.util.Map;
import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.wuxing.service.WuxingService.PutType;

/**
 * 五行放入的log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface WuxingPutLog extends AppLogEntity
{
	String KEY = WuxingPutLog.class.getName();

	/**
	 * 玩的類型
	 * 
	 * @return
	 */
	PutType getPutType();

	void setPutType(PutType putType);

	/**
	 * 已放入包包的獎勵
	 * 
	 * @return
	 */
	Map<String, Integer> getAwards();

	void setAwards(Map<String, Integer> awards);

}
