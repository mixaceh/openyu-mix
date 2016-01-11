package org.openyu.mix.sasang.service;

import java.util.List;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.commons.service.BaseService;

/**
 * 四象機器
 */
public interface SasangMachine extends BaseService {
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
	 * 玩四象
	 * 
	 * @return
	 */
	Outcome play();

	/**
	 * 玩幾次四象
	 * 
	 * @param times
	 * @return
	 */
	List<Outcome> play(int times);

}
