package org.openyu.mix.item.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 材料
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Material extends Item {
	String KEY = Material.class.getName();

	/**
	 * 唯一碼首碼
	 */
	String UNIQUE_ID_PREFIX = "M_";

	/**
	 * 材料類別
	 * 
	 * @return
	 */
	MaterialType getMaterialType();

	void setMaterialType(MaterialType materialType);
}
