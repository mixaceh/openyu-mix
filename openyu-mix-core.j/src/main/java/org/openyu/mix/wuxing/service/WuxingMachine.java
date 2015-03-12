package org.openyu.mix.wuxing.service;

import java.util.List;

import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.commons.service.BaseService;

/**
 * 五行機器
 */
public interface WuxingMachine extends BaseService
{

	/**
	 * 建構開出的結果
	 * 
	 * @return
	 */
	Outcome createOutcome();

	/**
	 * 建構開出的結果
	 * 
	 * @param outcomeId 結果id
	 * @param bankerId
	 * @param playerId
	 * @return
	 */
	Outcome createOutcome(String outcomeId, String bankerId, String playerId);

	/**
	 * 啟動
	 * 
	 * @return
	 */
	Outcome start();

	/**
	 * 啟動
	 * 
	 * @param times
	 * @return
	 */
	List<Outcome> start(int times);

}
