package org.openyu.mix.sasang.log;


import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.sasang.service.SasangService.PlayType;
import org.openyu.mix.sasang.vo.Outcome;

/**
 * 四象成名的log
 * 
 * log不做bean,直接用entity處理掉
 */
public interface SasangFamousLog extends AppLogEntity
{
	String KEY = SasangFamousLog.class.getName();

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
