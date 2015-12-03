package org.openyu.mix.sasang.service;

import java.util.List;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.commons.service.BaseService;

/**
 * 四象機器
 */
public interface SasangMachine extends BaseService
{
	/**
	 * 有幾轉輪
	 */
	int ROUND = 3;

	/**
	 * 建構開出的結果
	 * 
	 * @return
	 */
	Outcome createOutcome();

	/**
	 * 建構開出的結果
	 * 
	 * @param outcomeId
	 * @return
	 */
	Outcome createOutcome(String outcomeId);

	/**
	 * 啟動
	 * 
	 * @return
	 */
	Outcome play();

	/**
	 * 啟動
	 * 
	 * @param times
	 * @return
	 */
	List<Outcome> play(int times);

}
