package org.openyu.mix.role.vo;

import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 裝備欄
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface EquipmentInfo extends BaseBean
{
	String KEY = EquipmentInfo.class.getName();

	// --------------------------------------------------
	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum
	{
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		/**
		 * 裝備不存在
		 */
		EQUIPMENT_NOT_EXIST(11),

		;

		private final int intValue;

		private ErrorType(int intValue)
		{
			this.intValue = intValue;
		}

		public int getValue()
		{
			return intValue;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("name", "(" + intValue + ") " + super.toString());
			return builder.toString();
		}
	}

	// --------------------------------------------------

	/**
	 * 最大武器數量
	 */
	int MAX_WEAPON_SIZE = 2;

	/**
	 * 防具
	 * 
	 * @return
	 */
	Map<PositionType, Armor> getArmors();

	void setArmors(Map<PositionType, Armor> armors);

	/**
	 * 裝備
	 * 
	 * @return
	 */
	Map<PositionType, Weapon> getWeapons();

	void setWeapons(Map<PositionType, Weapon> weapons);

	/**
	 * 增加裝備,若已有此道具,會置換掉
	 * 
	 * @param equipment
	 * @return
	 */
	ErrorType addEquipment(Equipment equipment);

	/**
	 * 移除裝備
	 * 
	 * @param equipment
	 * @return
	 */
	ErrorType removeEquipment(Equipment equipment);

	/**
	 * 是否有此裝備
	 * 
	 * @param equipment
	 * @return
	 */
	Boolean containEquipment(Equipment equipment);

}
