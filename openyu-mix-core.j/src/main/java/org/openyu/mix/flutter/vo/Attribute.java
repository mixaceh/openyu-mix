package org.openyu.mix.flutter.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 屬性
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Attribute extends BaseBean {
	String KEY = Attribute.class.getName();

	/**
	 * 屬性操作類別
	 */
	public enum AttributeAction implements IntEnum {
		/**
		 * 增加,+
		 */
		INCREASE(1),

		/**
		 * 減少,-
		 */
		DECREASE(2),

		;
		private final int intValue;

		private AttributeAction(int intValue) {
			this.intValue = intValue;
		}

		public int getValue() {
			return intValue;
		}
	}

	/**
	 * 屬性類別,key
	 * 
	 * @return
	 */
	AttributeType getId();

	void setId(AttributeType id);

	/**
	 * 屬性值
	 * 
	 * @return
	 */
	int getPoint();

	void setPoint(int point);

	/**
	 * 增減屬性值
	 * 
	 * @param point
	 * @return
	 */
	boolean changePoint(int point);

	/**
	 * 增加(成長)的屬性值
	 * 
	 * @return
	 */
	int getAddPoint();

	void setAddPoint(int addPoint);

	/**
	 * 增減增加(成長)的屬性值
	 * 
	 * @param addPoint
	 * @return
	 */
	boolean changeAddPoint(int addPoint);

	/**
	 * 增加(成長)的比率值,萬分位(2000)
	 * 
	 * @return
	 */
	int getAddRate();

	void setAddRate(int addRate);

	/**
	 * 增減增加(成長)的比率值,萬分位(2000)
	 * 
	 * @param addRate
	 * @return
	 */
	boolean changeAddRate(int addRate);

	/**
	 * 最後屬性值=(point + addPoint) * (addRate/10000)
	 * 
	 * @return
	 */
	int getFinal();

}
