package org.openyu.mix.sasang.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.Prize;
import org.openyu.commons.bean.IdBean;
import org.openyu.commons.bean.ProbabilityBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 開出的結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Outcome extends IdBean, ProbabilityBean
{
	String KEY = Outcome.class.getName();

	/**
	 * 結果類別
	 * 
	 */
	public enum OutcomeType implements IntEnum
	{
		/**
		 * 都不相同
		 */
		STAND_ALONE(1),
		
		/**
		 * 兩個相同
		 */
		SAME_TWO(2),
		
		/**
		 * 三個相同
		 */
		SAME_THREE(3),
		
		//
		;

		private final int value;

		private OutcomeType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
	}

	/**
	 * 開出的獎
	 * 
	 * @return
	 */
	Prize getPrize();

	void setPrize(Prize prize);

	/**
	 * 結果類別
	 * 
	 * @return
	 */
	OutcomeType getOutcomeType();

	void setOutcomeType(OutcomeType outcomeType);

	/**
	 * 機率
	 * 
	 * @return
	 */
	double getProbability();

	void setProbability(double probability);

}
