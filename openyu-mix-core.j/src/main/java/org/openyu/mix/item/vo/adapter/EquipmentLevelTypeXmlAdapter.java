package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.item.vo.LevelType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<levelTypes key="E">1,21</levelTypes>
//--------------------------------------------------
public class EquipmentLevelTypeXmlAdapter
		extends
		BaseXmlAdapterSupporter<EquipmentLevelTypeXmlAdapter.EquipmentLevelTypeEntry, LevelType> {

	public EquipmentLevelTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class EquipmentLevelTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public String value;

		public EquipmentLevelTypeEntry(String key, int minValue, int maxValue) {
			this.key = key;
			this.value = minValue + "," + maxValue;
		}

		public EquipmentLevelTypeEntry() {
		}
	}

	// --------------------------------------------------
	public LevelType unmarshal(EquipmentLevelTypeEntry value) throws Exception {
		LevelType result = null;
		//
		if (value != null) {
			result = LevelType.valueOf(value.key);
		}
		return result;
	}

	public EquipmentLevelTypeEntry marshal(LevelType value) throws Exception {
		EquipmentLevelTypeEntry result = null;
		//
		if (value != null) {
			result = new EquipmentLevelTypeEntry(value.name(),
					value.minValue(), value.maxValue());
		}
		return result;
	}
}
