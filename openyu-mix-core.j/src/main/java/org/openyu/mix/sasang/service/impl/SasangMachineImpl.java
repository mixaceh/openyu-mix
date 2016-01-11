package org.openyu.mix.sasang.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.Prize;
import org.openyu.mix.sasang.service.SasangMachine;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.Outcome.OutcomeType;
import org.openyu.mix.sasang.vo.Sasang;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.mix.sasang.vo.SasangCollector;
import org.openyu.mix.sasang.vo.impl.OutcomeImpl;
import org.openyu.commons.bean.BeanHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.util.CollectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SasangMachineImpl extends AppServiceSupporter implements SasangMachine {

	private static final long serialVersionUID = -6319750135831837024L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SasangMachineImpl.class);

	private static SasangMachineImpl instance;

	private transient SasangCollector sasangCollector = SasangCollector.getInstance();

	// 元素類別權重
	// <青龍,<4,3,1>>
	private transient Map<SasangType, List<Integer>> sasangTypeWeights = new LinkedHashMap<SasangType, List<Integer>>();

	// 輪迴權重總和
	// <1,64>
	private transient Map<Integer, Integer> roundWeightSums = new LinkedHashMap<Integer, Integer>();

	// <青龍,0.0045454545>
	private transient Map<SasangType, Double> sameThreeProbs = new LinkedHashMap<SasangType, Double>();

	// <青龍,0.0772727273>
	private transient Map<SasangType, Double> sameTwoProbs = new LinkedHashMap<SasangType, Double>();

	// 3個相同,2個相同,安慰獎機率總計 => 總和=1
	private transient double[] probSums = new double[0];

	// <111,outcome>
	private transient Map<String, Outcome> outcomes = new LinkedHashMap<String, Outcome>();

	// 機率模型機率總和
	private transient double outcomesProbSum;

	public SasangMachineImpl() {

	}

	/**
	 * 單例啟動
	 * 
	 * @return
	 */
	public synchronized static SasangMachine getInstance() {
		try {
			if (instance == null) {
				instance = new SasangMachineImpl();
				instance.setGetInstance(true);
				// 啟動
				instance.start();
			}
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during getInstance()").toString(), e);
			instance = (SasangMachineImpl) shutdownInstance();
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static SasangMachine shutdownInstance() {
		try {
			if (instance != null) {
				SasangMachineImpl oldInstance = instance;
				//
				oldInstance.shutdown();
				instance = null;
			}
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during shutdownInstance()").toString(), e);
		}
		return instance;
	}

	/**
	 * 單例重啟
	 * 
	 * @return
	 */
	public synchronized static SasangMachine restartInstance() {
		try {
			if (instance != null) {
				instance.restart();
			}
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during restartInstance()").toString(), e);
		}
		return instance;
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		// 預先建構機率
		calcSasangTypeWeights();
		calcRoundWeightSums();
		calcSameThreeProbs();
		calcSameTwoProbs();
		calcTotalProbSums();
		//
		this.outcomes = buildOutcomes();// 建構機率模型
		calcOutcomesProbSum();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {

	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
	}

	public Map<SasangType, List<Integer>> getSasangWeights() {
		return sasangTypeWeights;
	}

	public void setSasangWeights(Map<SasangType, List<Integer>> sasangTypeWeights) {
		this.sasangTypeWeights = sasangTypeWeights;
	}

	public Map<Integer, Integer> getRoundWeightSums() {
		return roundWeightSums;
	}

	public void setRoundWeightSums(Map<Integer, Integer> roundWeightSums) {
		this.roundWeightSums = roundWeightSums;
	}

	public Map<SasangType, Double> getSameThreeProbs() {
		return sameThreeProbs;
	}

	public void setSameThreeProbs(Map<SasangType, Double> sameThreeProbs) {
		this.sameThreeProbs = sameThreeProbs;
	}

	public Map<SasangType, Double> getSameTwoProbs() {
		return sameTwoProbs;
	}

	public void setSameTwoProbs(Map<SasangType, Double> sameTwoProbs) {
		this.sameTwoProbs = sameTwoProbs;
	}

	public double[] getProbSums() {
		return probSums;
	}

	public void setProbSums(double[] probSums) {
		this.probSums = probSums;
	}

	public Map<String, Outcome> getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(Map<String, Outcome> outcomes) {
		this.outcomes = outcomes;
	}

	public double getOutcomesProbSum() {
		return outcomesProbSum;
	}

	public void setOutcomesProbSum(double outcomesProbSum) {
		this.outcomesProbSum = outcomesProbSum;
	}

	// ---------------------------------------------------

	/**
	 * 計算元素類別權重
	 * 
	 * @return
	 */
	protected boolean calcSasangTypeWeights() {
		boolean result = false;
		//
		sasangTypeWeights.clear();
		for (SasangType sasangType : SasangType.values()) {
			List<Integer> list = new LinkedList<Integer>();
			int[] weightSum = null;
			for (Sasang sasang : sasangCollector.getSasangs()) {
				if (sasangType != null && sasangType.equals(sasang.getId())) {
					if (weightSum == null) {
						weightSum = new int[ROUND];
					}
					int i = 0;
					for (Integer weight : sasang.getWeights()) {
						weightSum[i++] += weight;
					}
				}
			}
			//
			if (weightSum != null) {
				for (int weight : weightSum) {
					list.add(weight);
				}
			}
			//
			sasangTypeWeights.put(sasangType, list);
			result = true;
		}
		return result;
	}

	/**
	 * 取得第幾輪迴,元素類別權重
	 * 
	 * @param sasangType
	 * @param round
	 * @return
	 */
	protected int getSasangTypeWeight(SasangType sasangType, int round) {
		int result = 0;
		//
		List<Integer> list = sasangTypeWeights.get(sasangType);
		if (CollectionHelper.notEmpty(list) && round < ROUND) {
			result = list.get(round);
		}
		return result;
	}

	/**
	 * 計算輪迴權重總和
	 * 
	 * @return
	 */
	protected boolean calcRoundWeightSums() {
		boolean result = false;
		//
		roundWeightSums.clear();
		for (int i = 0; i < ROUND; i++) {
			int sum = 0;
			for (List<Integer> list : sasangTypeWeights.values()) {
				if (CollectionHelper.notEmpty(list)) {
					int weight = list.get(i);
					sum += weight;
				}
			}
			//
			roundWeightSums.put(i, sum);
		}
		result = true;
		return result;
	}

	/**
	 * 取得輪迴權重總和
	 * 
	 * @param round
	 * @return
	 */
	protected int getRoundWeightSum(int round) {
		int result = 0;
		if (round < ROUND) {
			result = roundWeightSums.get(round);
		}
		return result;
	}

	/**
	 * 計算3個相同的機率
	 * 
	 * 6個
	 * 
	 * @return
	 */
	protected boolean calcSameThreeProbs() {
		boolean result = false;
		//
		sameThreeProbs.clear();
		for (SasangType sasangType : SasangType.values()) {
			double prob = 0d;
			//
			int[] weight = new int[ROUND];
			int[] weightSum = new int[ROUND];
			//
			double weightFactor = 1.0d;
			double weightSumFactor = 1.0d;

			for (int i = 0; i < ROUND; i++) {
				weight[i] = getSasangTypeWeight(sasangType, i);
				weightSum[i] = getRoundWeightSum(i);
				//
				weightFactor = NumberHelper.multiply(weightFactor, weight[i]);
				weightSumFactor = NumberHelper.multiply(weightSumFactor, weightSum[i]);
			}
			prob = NumberHelper.divide(weightFactor, weightSumFactor);
			//
			sameThreeProbs.put(sasangType, prob);
		}
		result = true;
		return result;
	}

	/**
	 * 取得3個相同的機率
	 * 
	 * @param sasangType
	 * @return
	 */
	protected double getSameThreeProb(SasangType sasangType) {
		double result = 0d;
		if (sasangType != null) {
			result = sameThreeProbs.get(sasangType);
		}
		return result;
	}

	/**
	 * 計算2個相同的機率
	 * 
	 * 3*6=18
	 * 
	 * *6=108
	 * 
	 * @return
	 */
	protected boolean calcSameTwoProbs() {
		boolean result = false;
		//
		sameTwoProbs.clear();
		for (SasangType sasangType : SasangType.values()) {
			double prob = 0d;
			//
			int[] weight = new int[ROUND];
			int[] weightSum = new int[ROUND];
			//
			double weightSumFactor = 1.0d;

			for (int i = 0; i < ROUND; i++) {
				weight[i] = getSasangTypeWeight(sasangType, i);
				weightSum[i] = getRoundWeightSum(i);
				//
				weightSumFactor = NumberHelper.multiply(weightSumFactor, weightSum[i]);
			}
			prob = (weight[0] * weight[1] * (NumberHelper.subtract(weightSum[2], weight[2])));
			prob += (weight[0] * (NumberHelper.subtract(weightSum[1], weight[1])) * (weight[2]));
			prob += ((NumberHelper.subtract(weightSum[0], weight[0])) * weight[1] * weight[2]);
			prob = NumberHelper.divide(prob, weightSumFactor);
			//
			sameTwoProbs.put(sasangType, prob);
		}
		result = true;
		return result;
	}

	/**
	 * 取得2個相同的機率
	 * 
	 * @param sasangType
	 * @return
	 */
	protected double getSameTwoProb(SasangType sasangType) {
		double result = 0d;
		if (sasangType != null) {
			result = sameTwoProbs.get(sasangType);
		}
		return result;
	}

	//
	/**
	 * 計算3個相同及2個相同及安慰獎機率
	 * 
	 * 但3個空及2個空,屬安慰獎
	 * 
	 * [0]=三個相同圖案機率總和
	 * 
	 * [1]=兩個相同圖案機率總和
	 * 
	 * [2]=安慰獎的機率總和
	 * 
	 * 
	 * @return
	 */
	protected boolean calcTotalProbSums() {
		boolean result = false;
		//
		probSums = new double[3];
		double same3Sum = 0d;
		double same2Sum = 0d;
		for (SasangType sasangType : SasangType.values()) {
			// 3個空及2個空,不列入3個相同及2個相同機率中
			if (!sasangType.equals(SasangType.NOTHING)) {
				same3Sum = NumberHelper.add(same3Sum, getSameThreeProb(sasangType));
				same2Sum = NumberHelper.add(same2Sum, getSameTwoProb(sasangType));
			}
		}
		probSums[0] = same3Sum;
		probSums[1] = same2Sum;

		// 其他獎的機率= 1 – （三個相同圖案機率+ 兩個相同圖案機率）
		probSums[2] = NumberHelper.subtract(NumberHelper.subtract(1, probSums[0]), probSums[1]);
		probSums[2] = probSums[2] < 0 ? 0 : probSums[2];
		result = true;
		return result;
	}

	/**
	 * 依sasangType,相同次數,判斷是否在區間
	 * 
	 * @param sasangType
	 * @param sameTime
	 * @return
	 */
	protected boolean playByType(SasangType sasangType, int sameTime) {
		boolean result = false;
		// 三個相同
		if (!sasangType.equals(SasangType.NOTHING) && sameTime == 3) {
			result = NumberHelper.randomDouble(0, 1) < getSameThreeProb(sasangType);
		}
		// 兩個相同
		else if (!sasangType.equals(SasangType.NOTHING) && sameTime == 2) {
			result = NumberHelper.randomDouble(0, 1) < getSameTwoProb(sasangType);
		}
		// 其他(安慰獎)
		else {
			result = NumberHelper.randomDouble(0, 1) < probSums[2];
		}
		return result;
	}

	/**
	 * 建構機率模型
	 * 
	 * @return
	 */
	protected Map<String, Outcome> buildOutcomes() {
		Map<String, Outcome> result = new LinkedHashMap<String, Outcome>();
		// 7*7*7=343
		for (SasangType sasangType : SasangType.values()) {
			for (SasangType sasangType2 : SasangType.values()) {
				for (SasangType sasangType3 : SasangType.values()) {
					Outcome outcome = new OutcomeImpl();
					//
					StringBuilder outcomeId = new StringBuilder();
					outcomeId.append(sasangType.getValue());
					outcomeId.append(sasangType2.getValue());
					outcomeId.append(sasangType3.getValue());
					outcome.setId(outcomeId.toString());
					//
					// 3個相同,且非3個空
					if (sasangType.equals(sasangType2) && sasangType2.equals(sasangType3)
							&& !sasangType.equals(SasangType.NOTHING) && !sasangType2.equals(SasangType.NOTHING)
							&& !sasangType3.equals(SasangType.NOTHING)) {
						double prob = getSameThreeProb(sasangType);
						// System.out.println("AAA: " + sasangType + " " +
						// sasangType2 + " " + sasangType3
						// + " " + prob);
						// 獎勵
						Prize prize = sasangCollector.getPrize(sasangType, sasangType2, sasangType3);
						outcome.setPrize(prize);
						outcome.setOutcomeType(OutcomeType.SAME_THREE);
						outcome.setProbability(prob);
					}
					// 2個相同,且非2個空
					else if (sasangType.equals(sasangType2) && !sasangType.equals(SasangType.NOTHING)
							&& !sasangType2.equals(SasangType.NOTHING)) {
						double prob = NumberHelper.divide(getSameTwoProb(sasangType), 18);
						// System.out.println("AAx: " + sasangType + " " +
						// sasangType2 + " " + sasangType3
						// + " " + prob);
						// 獎勵
						Prize prize = sasangCollector.getPrize(sasangType, sasangType2);
						outcome.setPrize(prize);
						outcome.setOutcomeType(OutcomeType.SAME_TWO);
						outcome.setProbability(prob);
					} else if (sasangType2.equals(sasangType3) && !sasangType2.equals(SasangType.NOTHING)
							&& !sasangType3.equals(SasangType.NOTHING)) {
						double prob = NumberHelper.divide(getSameTwoProb(sasangType2), 18);
						// System.out.println("xBB: " + sasangType + " " +
						// sasangType2 + " " + sasangType3
						// + " " + prob);
						// 獎勵
						Prize prize = sasangCollector.getPrize(sasangType2, sasangType3);
						outcome.setPrize(prize);
						outcome.setOutcomeType(OutcomeType.SAME_TWO);
						outcome.setProbability(prob);
					} else if (sasangType.equals(sasangType3) && !sasangType.equals(SasangType.NOTHING)
							&& !sasangType3.equals(SasangType.NOTHING)) {
						double prob = NumberHelper.divide(getSameTwoProb(sasangType), 18);
						// System.out.println("CxC: " + sasangType + " " +
						// sasangType2 + " " + sasangType3
						// + " " + prob);
						// 獎勵
						Prize prize = sasangCollector.getPrize(sasangType, sasangType3);
						outcome.setPrize(prize);
						outcome.setOutcomeType(OutcomeType.SAME_TWO);
						outcome.setProbability(prob);
					}
					// 安慰獎
					else {
						double prob = NumberHelper.divide(probSums[2], 229);
						// 獎勵
						Prize prize = sasangCollector.getPrize("0");// 0=安慰獎
						outcome.setPrize(prize);
						outcome.setOutcomeType(OutcomeType.STAND_ALONE);
						outcome.setProbability(prob);
					}
					result.put(outcome.getId(), outcome);
				}
			}
		}
		return result;
	}

	/**
	 * 計算模型機率總和
	 * 
	 * @return
	 */
	protected boolean calcOutcomesProbSum() {
		boolean result = false;
		outcomesProbSum = 0d;
		for (Outcome entry : outcomes.values()) {
			outcomesProbSum = NumberHelper.add(new Double(outcomesProbSum), new Double(entry.getProbability()));
		}
		result = true;
		return result;
	}

	/**
	 * 取得四象結果,clone
	 */
	protected Outcome getOutcome(String outcomeId) {
		Outcome result = null;
		if (outcomeId != null) {
			result = outcomes.get(outcomeId);
		}
		return (result != null ? (Outcome) result.clone() : result);
	}

	/**
	 * 建構開出的結果
	 */
	public Outcome createOutcome() {
		return createOutcome(null);
	}

	/**
	 * 建構開出的結果
	 * 
	 * 依照靜態資料,建構新的stock,使用clone/或用建構new xxx()
	 * 
	 * @param outcomeId
	 *            結果id
	 * @return
	 */
	public Outcome createOutcome(String outcomeId) {
		Outcome result = null;
		// 來自靜態資料的clone副本
		result = getOutcome(outcomeId);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new OutcomeImpl(outcomeId);
		}
		return result;
	}

	/**
	 * 玩四象,不在這clone了,直接拿,改由使用時才clone
	 */
	public Outcome play() {
		Outcome result = null;
		result = BeanHelper.probRandomOf(outcomes.values(), outcomesProbSum);
		// return (result != null ? (Outcome) result.clone() : result);
		return result;
	}

	/**
	 * 玩幾次四象,不在這clone了,直接拿,改由使用時才clone
	 *
	 * @param times
	 * @return
	 * 
	 * @see org.openyu.mix.sasang.service.impl.SasangServiceImpl.goldPlay()
	 */
	public List<Outcome> play(int times) {
		List<Outcome> result = new LinkedList<Outcome>();
		for (int i = 0; i < times; i++) {
			Outcome outcome = play();
			if (outcome != null) {
				result.add(outcome);
			}
		}
		return result;
	}

}
