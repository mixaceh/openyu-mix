package org.openyu.mix.wuxing.log;


import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.wuxing.service.WuxingService.PlayType;
import org.openyu.mix.wuxing.vo.Outcome;

/**
 * 五行成名的log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface WuxingFamousLog extends AppLogEntity
{
	String KEY = WuxingFamousLog.class.getName();

	/**
	 * 玩的類型
	 * 
	 * @return
	 */
	PlayType getPlayType();

	void setPlayType(PlayType playType);

	/**
	 * 玩的時間
	 * 
	 * @return
	 */
	Long getPlayTime();

	void setPlayTime(Long playTime);

	/**
	 * 玩的結果
	 * 
	 * @return
	 */
	Outcome getOutcome();

	void setOutcome(Outcome outcome);

}
