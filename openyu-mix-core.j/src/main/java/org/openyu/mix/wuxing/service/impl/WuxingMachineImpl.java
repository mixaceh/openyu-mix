package org.openyu.mix.wuxing.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.wuxing.service.WuxingMachine;
import org.openyu.mix.wuxing.vo.Outcome;
import org.openyu.mix.wuxing.vo.Outcome.OutcomeType;
import org.openyu.mix.wuxing.vo.WuxingType;
import org.openyu.mix.wuxing.vo.WuxingCollector;
import org.openyu.mix.wuxing.vo.impl.OutcomeImpl;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.service.supporter.BaseServiceSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WuxingMachineImpl extends BaseServiceSupporter implements
		WuxingMachine {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingMachineImpl.class);

	private static WuxingMachineImpl wuxingMachineImpl;

	private transient WuxingCollector wuxingCollector = WuxingCollector
			.getInstance();

	/**
	 * 五行勝負結果
	 */
	private transient Map<WuxingType, Map<Integer, OutcomeType>> outcomeTypes = new LinkedHashMap<WuxingType, Map<Integer, OutcomeType>>();

	public WuxingMachineImpl() {
		outcomeTypes = buildOutcomeTypes();
	}

	public synchronized static WuxingMachine getInstance() {
		if (wuxingMachineImpl == null) {
			wuxingMachineImpl = new WuxingMachineImpl();
		}
		return wuxingMachineImpl;
	}

	/**
	 * 建構五行勝負結果
	 * 
	 * --win lose birth tie
	 * 
	 * 金 -1 -4 -3 -2,0
	 * 
	 * 木 -1 1 -3 -2,0
	 * 
	 * 土 -1 1 2 -2,0
	 * 
	 * 水 -1 1 2 3,0
	 * 
	 * 火 4 1 2 3,0
	 * 
	 * @return
	 */
	protected Map<WuxingType, Map<Integer, OutcomeType>> buildOutcomeTypes() {
		Map<WuxingType, Map<Integer, OutcomeType>> result = new LinkedHashMap<WuxingType, Map<Integer, OutcomeType>>();
		Map<Integer, OutcomeType> outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
		//
		outcomeTypes.put(-1, OutcomeType.WIN);
		outcomeTypes.put(-4, OutcomeType.LOSE);
		outcomeTypes.put(-3, OutcomeType.BIRTH);
		outcomeTypes.put(-2, OutcomeType.TIE);
		outcomeTypes.put(0, OutcomeType.TIE);
		result.put(WuxingType.METAL, outcomeTypes);
		//
		outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
		outcomeTypes.put(-1, OutcomeType.WIN);
		outcomeTypes.put(1, OutcomeType.LOSE);
		outcomeTypes.put(-3, OutcomeType.BIRTH);
		outcomeTypes.put(-2, OutcomeType.TIE);
		outcomeTypes.put(0, OutcomeType.TIE);
		result.put(WuxingType.WOOD, outcomeTypes);
		//
		outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
		outcomeTypes.put(-1, OutcomeType.WIN);
		outcomeTypes.put(1, OutcomeType.LOSE);
		outcomeTypes.put(2, OutcomeType.BIRTH);
		outcomeTypes.put(-2, OutcomeType.TIE);
		outcomeTypes.put(0, OutcomeType.TIE);
		result.put(WuxingType.EARTH, outcomeTypes);
		//
		outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
		outcomeTypes.put(-1, OutcomeType.WIN);
		outcomeTypes.put(1, OutcomeType.LOSE);
		outcomeTypes.put(2, OutcomeType.BIRTH);
		outcomeTypes.put(3, OutcomeType.TIE);
		outcomeTypes.put(0, OutcomeType.TIE);
		result.put(WuxingType.WATER, outcomeTypes);
		//
		outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
		outcomeTypes.put(4, OutcomeType.WIN);
		outcomeTypes.put(1, OutcomeType.LOSE);
		outcomeTypes.put(2, OutcomeType.BIRTH);
		outcomeTypes.put(3, OutcomeType.TIE);
		outcomeTypes.put(0, OutcomeType.TIE);
		result.put(WuxingType.FIRE, outcomeTypes);
		//
		return result;
	}

	/**
	 * 建構開出的結果
	 */
	public Outcome createOutcome() {
		return createOutcome(null, null, null);
	}

	/**
	 * 建構開出的結果
	 * 
	 * @param outcomeId
	 *            結果id
	 * @param bankerId
	 * @param playerId
	 * @return
	 */
	public Outcome createOutcome(String outcomeId, String bankerId,
			String playerId) {
		Outcome result = new OutcomeImpl(outcomeId, bankerId, playerId);
		// clone prize
		List<Prize> prizes = clone(calcPrizes(outcomeId));
		result.setPrizes(prizes);
		return result;
	}

	/**
	 * 隨機五行
	 * 
	 * 自然機率
	 * 
	 * @return
	 */
	protected List<WuxingType> randomWuxingTypes() {
		List<WuxingType> wuxings = new LinkedList<WuxingType>();
		//
		int length = WuxingType.values().length;
		for (int i = 0; i < length; i++) {
			int wuxingValue = NumberHelper.randomInt(1, length + 1);
			WuxingType wuxingType = EnumHelper.valueOf(WuxingType.class,
					wuxingValue);
			wuxings.add(wuxingType);
		}
		return wuxings;
	}

	/**
	 * 五行類別 -> id,ex:11315
	 * 
	 * @param wuxingTypes
	 * @return
	 */
	protected String buidWuxingId(List<WuxingType> wuxingTypes) {
		StringBuilder result = new StringBuilder();
		for (WuxingType wuxingType : wuxingTypes) {
			result.append(wuxingType.getValue());
		}

		return (result.length() > 0 ? result.toString() : null);
	}

	/**
	 * 結果類別 -> id,ex:24144
	 * 
	 * @param outcomeTypes
	 * @return
	 */
	protected String buidOutcomeId(List<OutcomeType> outcomeTypes) {
		StringBuilder result = new StringBuilder();
		for (OutcomeType outcomeType : outcomeTypes) {
			result.append(outcomeType.getValue());
		}
		//
		return result.toString();
	}

	/**
	 * 啟動,不clone了,直接拿
	 */
	public Outcome start() {
		Outcome result = null;
		// banker
		List<WuxingType> bankerWuxingTypes = randomWuxingTypes();
		// player
		List<WuxingType> playerWuxingTypes = randomWuxingTypes();
		// 勝負結果
		List<OutcomeType> outcomes = new LinkedList<OutcomeType>();
		//
		for (int i = 0; i < bankerWuxingTypes.size(); i++) {
			WuxingType bankerWuxingType = bankerWuxingTypes.get(i);
			WuxingType playerWuxingType = playerWuxingTypes.get(i);
			//
			int diffValue = bankerWuxingType.getValue()
					- playerWuxingType.getValue();
			OutcomeType outcomeType = outcomeTypes.get(bankerWuxingType).get(
					diffValue);
			outcomes.add(outcomeType);
		}
		//
		// System.out.println(buidWuxingId(bankerWuxingTypes));
		// System.out.println(buidWuxingId(playerWuxingTypes));
		// System.out.println(buidOutcomeId(outcomes));
		// System.out.println(calcPrizes(outcomes));

		String outcomeId = buidOutcomeId(outcomes);
		String bankerId = buidWuxingId(bankerWuxingTypes);
		String playerId = buidWuxingId(playerWuxingTypes);
		List<Prize> prizes = calcPrizes(outcomes);
		//
		result = new OutcomeImpl(outcomeId, bankerId, playerId);
		result.setPrizes(prizes);
		//
		return result;
	}

	/**
	 * 計算獎勵,final,birth,tie
	 * 
	 * @param outcomeTypes
	 * @return
	 */
	protected List<Prize> calcPrizes(List<OutcomeType> outcomeTypes) {
		List<Prize> result = new LinkedList<Prize>();
		// final
		int winTimes = calcTimes(outcomeTypes, OutcomeType.WIN);
		int loseTimes = calcTimes(outcomeTypes, OutcomeType.LOSE);
		int finalTimes = winTimes - loseTimes;
		finalTimes = (finalTimes < 0 ? 0 : finalTimes);
		Prize finalPrize = wuxingCollector.getFinalPrize(String
				.valueOf(finalTimes));
		if (finalPrize != null) {
			result.add(finalPrize);
		}

		// birth
		int birthTimes = calcTimes(outcomeTypes, OutcomeType.BIRTH);
		Prize birthPrize = wuxingCollector.getBirthPrize(String
				.valueOf(birthTimes));
		if (birthPrize != null) {
			result.add(birthPrize);
		}

		// tie
		int tieTimes = calcTimes(outcomeTypes, OutcomeType.TIE);
		Prize tiePrize = wuxingCollector.getTiePrize(String.valueOf(tieTimes));
		if (tiePrize != null) {
			result.add(tiePrize);
		}
		//
		return result;
	}

	/**
	 * 計算獎勵,final,birth,tie
	 * 
	 * @param id
	 *            , 11234
	 * @return
	 */
	protected List<Prize> calcPrizes(String outcomeId) {
		List<Prize> result = new LinkedList<Prize>();
		if (outcomeId == null) {
			return result;
		}
		// final
		int winTimes = calcTimes(outcomeId, OutcomeType.WIN);
		int loseTimes = calcTimes(outcomeId, OutcomeType.LOSE);
		int finalTimes = winTimes - loseTimes;
		finalTimes = (finalTimes < 0 ? 0 : finalTimes);
		Prize finalPrize = wuxingCollector.getFinalPrize(String
				.valueOf(finalTimes));
		if (finalPrize != null) {
			result.add(finalPrize);
		}

		// birth
		int birthTimes = calcTimes(outcomeId, OutcomeType.BIRTH);
		Prize birthPrize = wuxingCollector.getBirthPrize(String
				.valueOf(birthTimes));
		if (birthPrize != null) {
			result.add(birthPrize);
		}

		// tie
		int tieTimes = calcTimes(outcomeId, OutcomeType.TIE);
		Prize tiePrize = wuxingCollector.getTiePrize(String.valueOf(tieTimes));
		if (tiePrize != null) {
			result.add(tiePrize);
		}
		//
		return result;
	}

	/**
	 * 計算結果類別次數
	 * 
	 * @param outcomeTypes
	 * @param outcomeType
	 * @return
	 */
	protected int calcTimes(List<OutcomeType> outcomeTypes,
			OutcomeType outcomeType) {
		int result = 0;
		for (OutcomeType entry : outcomeTypes) {
			if (entry == outcomeType) {
				result += 1;
			}
		}
		return result;
	}

	/**
	 * 計算結果類別次數
	 * 
	 * @param outcomeId
	 * @param outcomeType
	 * @return
	 */
	protected int calcTimes(String outcomeId, OutcomeType outcomeType) {
		int result = 0;
		int length = outcomeId.length();
		for (int i = 0; i < length; i++) {
			char buff = outcomeId.charAt(i);
			if (String.valueOf(buff).equals(
					String.valueOf(outcomeType.getValue()))) {
				result += 1;
			}

		}
		return result;
	}

	/**
	 * 啟動,不clone了,直接拿
	 * 
	 * @param times
	 * @return
	 */
	public List<Outcome> start(int times) {
		List<Outcome> result = new LinkedList<Outcome>();
		for (int i = 0; i < times; i++) {
			Outcome outcome = start();
			if (outcome != null) {
				result.add(outcome);
			}
		}
		return result;
	}

}
