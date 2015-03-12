package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.item.vo.SeriesType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<seriesTypes key="MARS">4</seriesTypes>
//--------------------------------------------------
public class EquipmentSeriesTypeXmlAdapter
		extends
		BaseXmlAdapterSupporter<EquipmentSeriesTypeXmlAdapter.EquipmentSeriesTypeEntry, SeriesType> {

	public EquipmentSeriesTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class EquipmentSeriesTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public EquipmentSeriesTypeEntry(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public EquipmentSeriesTypeEntry() {
		}
	}

	// --------------------------------------------------
	public SeriesType unmarshal(EquipmentSeriesTypeEntry value)
			throws Exception {
		SeriesType result = null;
		//
		if (value != null) {
			result = SeriesType.valueOf(value.key);
		}
		return result;
	}

	public EquipmentSeriesTypeEntry marshal(SeriesType value) throws Exception {
		EquipmentSeriesTypeEntry result = null;
		//
		if (value != null) {
			result = new EquipmentSeriesTypeEntry(value.name(),
					value.getValue());
		}
		return result;
	}
}
