package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.Equipment;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 裝備結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface EquipmentResult extends AppResult
{

	String KEY = EquipmentResult.class.getName();

	/**
	 * 裝備
	 * 
	 * @return
	 */
	Equipment getEquipment();

	void setEquipment(Equipment equipment);
}
