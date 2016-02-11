package org.openyu.mix.vip.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<vipTypes key="_1">1</vipTypes>
//--------------------------------------------------
public class VipTypeXmlAdapter extends
		BaseXmlAdapterSupporter<VipTypeXmlAdapter.VipTypeEntry, VipType> {

	public VipTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class VipTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public VipTypeEntry(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public VipTypeEntry() {
		}
	}

	// --------------------------------------------------
	public VipType unmarshal(VipTypeEntry value) throws Exception {
		VipType result = null;
		//
		if (value != null) {
			result = VipType.valueOf(value.key);
		}
		return result;
	}

	public VipTypeEntry marshal(VipType value) throws Exception {
		VipTypeEntry result = null;
		//
		if (value != null) {
			result = new VipTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
