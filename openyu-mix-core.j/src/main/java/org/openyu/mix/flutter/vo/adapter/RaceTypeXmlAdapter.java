package org.openyu.mix.flutter.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<raceTypes key="HUMAN">1</raceTypes>
//--------------------------------------------------
public class RaceTypeXmlAdapter extends
		BaseXmlAdapterSupporter<RaceTypeXmlAdapter.RaceTypeEntry, RaceType> {

	public RaceTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class RaceTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public RaceTypeEntry(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public RaceTypeEntry() {
		}
	}

	// --------------------------------------------------
	public RaceType unmarshal(RaceTypeEntry value) throws Exception {
		RaceType result = null;
		//
		if (value != null) {
			result = RaceType.valueOf(value.key);
		}
		return result;
	}

	public RaceTypeEntry marshal(RaceType value) throws Exception {
		RaceTypeEntry result = null;
		//
		if (value != null) {
			result = new RaceTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
