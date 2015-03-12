package org.openyu.mix.qixing.service.impl;

import org.openyu.mix.qixing.service.QixingMachine;
import org.openyu.mix.qixing.vo.QixingCollector;
import org.openyu.commons.service.supporter.BaseServiceSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QixingMachineImpl extends BaseServiceSupporter implements
		QixingMachine {
	
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(QixingMachineImpl.class);

	private static QixingMachineImpl qixingMachineImpl;

	private transient QixingCollector qixingCollector = QixingCollector
			.getInstance();

	// /**
	// * 七星勝負結果
	// */
	// private transient Map<QixingType, Map<Integer, OutcomeType>> outcomeTypes
	// = new LinkedHashMap<QixingType, Map<Integer, OutcomeType>>();
	//
	// public QixingMachineImpl()
	// {
	// outcomeTypes = buildOutcomeTypes();
	// }
	//
	// public synchronized static QixingMachine getInstance()
	// {
	// if (qixingMachineImpl == null)
	// {
	// qixingMachineImpl = new QixingMachineImpl();
	// }
	// return qixingMachineImpl;
	// }
	//
	// /**
	// * 建構七星勝負結果
	// *
	// * --win lose birth tie
	// *
	// * 金 -1 -4 -3 -2,0
	// *
	// * 木 -1 1 -3 -2,0
	// *
	// * 土 -1 1 2 -2,0
	// *
	// * 水 -1 1 2 3,0
	// *
	// * 火 4 1 2 3,0
	// *
	// * @return
	// */
	// protected Map<QixingType, Map<Integer, OutcomeType>> buildOutcomeTypes()
	// {
	// Map<QixingType, Map<Integer, OutcomeType>> result = new
	// LinkedHashMap<QixingType, Map<Integer, OutcomeType>>();
	// Map<Integer, OutcomeType> outcomeTypes = new LinkedHashMap<Integer,
	// OutcomeType>();
	// //
	// outcomeTypes.put(-1, OutcomeType.WIN);
	// outcomeTypes.put(-4, OutcomeType.LOSE);
	// outcomeTypes.put(-3, OutcomeType.BIRTH);
	// outcomeTypes.put(-2, OutcomeType.TIE);
	// outcomeTypes.put(0, OutcomeType.TIE);
	// result.put(QixingType.METAL, outcomeTypes);
	// //
	// outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
	// outcomeTypes.put(-1, OutcomeType.WIN);
	// outcomeTypes.put(1, OutcomeType.LOSE);
	// outcomeTypes.put(-3, OutcomeType.BIRTH);
	// outcomeTypes.put(-2, OutcomeType.TIE);
	// outcomeTypes.put(0, OutcomeType.TIE);
	// result.put(QixingType.WOOD, outcomeTypes);
	// //
	// outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
	// outcomeTypes.put(-1, OutcomeType.WIN);
	// outcomeTypes.put(1, OutcomeType.LOSE);
	// outcomeTypes.put(2, OutcomeType.BIRTH);
	// outcomeTypes.put(-2, OutcomeType.TIE);
	// outcomeTypes.put(0, OutcomeType.TIE);
	// result.put(QixingType.EARTH, outcomeTypes);
	// //
	// outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
	// outcomeTypes.put(-1, OutcomeType.WIN);
	// outcomeTypes.put(1, OutcomeType.LOSE);
	// outcomeTypes.put(2, OutcomeType.BIRTH);
	// outcomeTypes.put(3, OutcomeType.TIE);
	// outcomeTypes.put(0, OutcomeType.TIE);
	// result.put(QixingType.WATER, outcomeTypes);
	// //
	// outcomeTypes = new LinkedHashMap<Integer, OutcomeType>();
	// outcomeTypes.put(4, OutcomeType.WIN);
	// outcomeTypes.put(1, OutcomeType.LOSE);
	// outcomeTypes.put(2, OutcomeType.BIRTH);
	// outcomeTypes.put(3, OutcomeType.TIE);
	// outcomeTypes.put(0, OutcomeType.TIE);
	// result.put(QixingType.FIRE, outcomeTypes);
	// //
	// return result;
	// }
	//
	// /**
	// * 建構開出的結果
	// */
	// public Outcome createOutcome()
	// {
	// return createOutcome(null, null, null);
	// }
	//
	// /**
	// * 建構開出的結果
	// *
	// * @param outcomeId 結果id
	// * @param bankerId
	// * @param playerId
	// * @return
	// */
	// public Outcome createOutcome(String outcomeId, String bankerId, String
	// playerId)
	// {
	// Outcome result = new OutcomeImpl(outcomeId, bankerId, playerId);
	// //clone prize
	// List<Prize> prizes = clone(calcPrizes(outcomeId));
	// result.setPrizes(prizes);
	// return result;
	// }
	//
	// /**
	// * 隨機七星
	// *
	// * 自然機率
	// *
	// * @return
	// */
	// protected List<QixingType> randomQixingTypes()
	// {
	// List<QixingType> qixings = new LinkedList<QixingType>();
	// //
	// int length = QixingType.values().length;
	// for (int i = 0; i < length; i++)
	// {
	// int qixingValue = NumberHelper.randomInt(1, length + 1);
	// QixingType qixingType = EnumHelper.valueOf(QixingType.class,
	// qixingValue);
	// qixings.add(qixingType);
	// }
	// return qixings;
	// }
	//
	// /**
	// * 七星類別 -> id,ex:11315
	// *
	// * @param qixingTypes
	// * @return
	// */
	// protected String buidQixingId(List<QixingType> qixingTypes)
	// {
	// StringBuilder result = new StringBuilder();
	// for (QixingType qixingType : qixingTypes)
	// {
	// result.append(qixingType.intValue());
	// }
	//
	// return (result.length() > 0 ? result.toString() : null);
	// }
	//
	// /**
	// * 結果類別 -> id,ex:24144
	// *
	// * @param outcomeTypes
	// * @return
	// */
	// protected String buidOutcomeId(List<OutcomeType> outcomeTypes)
	// {
	// StringBuilder result = new StringBuilder();
	// for (OutcomeType outcomeType : outcomeTypes)
	// {
	// result.append(outcomeType.intValue());
	// }
	// //
	// return result.toString();
	// }
	//
	// /**
	// * 啟動,不clone了,直接拿
	// */
	// public Outcome start()
	// {
	// Outcome result = null;
	// //banker
	// List<QixingType> bankerQixingTypes = randomQixingTypes();
	// //player
	// List<QixingType> playerQixingTypes = randomQixingTypes();
	// //勝負結果
	// List<OutcomeType> outcomes = new LinkedList<OutcomeType>();
	// //
	// for (int i = 0; i < bankerQixingTypes.size(); i++)
	// {
	// QixingType bankerQixingType = bankerQixingTypes.get(i);
	// QixingType playerQixingType = playerQixingTypes.get(i);
	// //
	// int diffValue = bankerQixingType.intValue() -
	// playerQixingType.intValue();
	// OutcomeType outcomeType =
	// outcomeTypes.get(bankerQixingType).get(diffValue);
	// outcomes.add(outcomeType);
	// }
	// //
	// // System.out.println(buidQixingId(bankerQixingTypes));
	// // System.out.println(buidQixingId(playerQixingTypes));
	// // System.out.println(buidOutcomeId(outcomes));
	// // System.out.println(calcPrizes(outcomes));
	//
	// String outcomeId = buidOutcomeId(outcomes);
	// String bankerId = buidQixingId(bankerQixingTypes);
	// String playerId = buidQixingId(playerQixingTypes);
	// List<Prize> prizes = calcPrizes(outcomes);
	// //
	// result = new OutcomeImpl(outcomeId, bankerId, playerId);
	// result.setPrizes(prizes);
	// //
	// return result;
	// }
	//
	// /**
	// * 計算獎勵,final,birth,tie
	// *
	// * @param outcomeTypes
	// * @return
	// */
	// protected List<Prize> calcPrizes(List<OutcomeType> outcomeTypes)
	// {
	// List<Prize> result = new LinkedList<Prize>();
	// //final
	// int winTimes = calcTimes(outcomeTypes, OutcomeType.WIN);
	// int loseTimes = calcTimes(outcomeTypes, OutcomeType.LOSE);
	// int finalTimes = winTimes - loseTimes;
	// finalTimes = (finalTimes < 0 ? 0 : finalTimes);
	// Prize finalPrize =
	// qixingCollector.getFinalPrize(String.valueOf(finalTimes));
	// if (finalPrize != null)
	// {
	// result.add(finalPrize);
	// }
	//
	// //birth
	// int birthTimes = calcTimes(outcomeTypes, OutcomeType.BIRTH);
	// Prize birthPrize =
	// qixingCollector.getBirthPrize(String.valueOf(birthTimes));
	// if (birthPrize != null)
	// {
	// result.add(birthPrize);
	// }
	//
	// //tie
	// int tieTimes = calcTimes(outcomeTypes, OutcomeType.TIE);
	// Prize tiePrize = qixingCollector.getTiePrize(String.valueOf(tieTimes));
	// if (tiePrize != null)
	// {
	// result.add(tiePrize);
	// }
	// //
	// return result;
	// }
	//
	// /**
	// * 計算獎勵,final,birth,tie
	// *
	// * @param id, 11234
	// * @return
	// */
	// protected List<Prize> calcPrizes(String outcomeId)
	// {
	// List<Prize> result = new LinkedList<Prize>();
	// //final
	// int winTimes = calcTimes(outcomeId, OutcomeType.WIN);
	// int loseTimes = calcTimes(outcomeId, OutcomeType.LOSE);
	// int finalTimes = winTimes - loseTimes;
	// finalTimes = (finalTimes < 0 ? 0 : finalTimes);
	// Prize finalPrize =
	// qixingCollector.getFinalPrize(String.valueOf(finalTimes));
	// if (finalPrize != null)
	// {
	// result.add(finalPrize);
	// }
	//
	// //birth
	// int birthTimes = calcTimes(outcomeId, OutcomeType.BIRTH);
	// Prize birthPrize =
	// qixingCollector.getBirthPrize(String.valueOf(birthTimes));
	// if (birthPrize != null)
	// {
	// result.add(birthPrize);
	// }
	//
	// //tie
	// int tieTimes = calcTimes(outcomeId, OutcomeType.TIE);
	// Prize tiePrize = qixingCollector.getTiePrize(String.valueOf(tieTimes));
	// if (tiePrize != null)
	// {
	// result.add(tiePrize);
	// }
	// //
	// return result;
	// }
	//
	// /**
	// * 計算結果類別次數
	// *
	// * @param outcomeTypes
	// * @param outcomeType
	// * @return
	// */
	// protected int calcTimes(List<OutcomeType> outcomeTypes, OutcomeType
	// outcomeType)
	// {
	// int result = 0;
	// for (OutcomeType entry : outcomeTypes)
	// {
	// if (entry == outcomeType)
	// {
	// result += 1;
	// }
	// }
	// return result;
	// }
	//
	// /**
	// * 計算結果類別次數
	// *
	// * @param outcomeId
	// * @param outcomeType
	// * @return
	// */
	// protected int calcTimes(String outcomeId, OutcomeType outcomeType)
	// {
	// int result = 0;
	// int length = outcomeId.length();
	// for (int i = 0; i < length; i++)
	// {
	// char buff = outcomeId.charAt(i);
	// if (String.valueOf(buff).equals(String.valueOf(outcomeType.intValue())))
	// {
	// result += 1;
	// }
	//
	// }
	// return result;
	// }
	//
	// /**
	// * 啟動,不clone了,直接拿
	// *
	// * @param times
	// * @return
	// */
	// public List<Outcome> start(int times)
	// {
	// List<Outcome> result = new LinkedList<Outcome>();
	// for (int i = 0; i < times; i++)
	// {
	// Outcome outcome = start();
	// if (outcome != null)
	// {
	// result.add(outcome);
	// }
	// }
	// return result;
	// }

}
