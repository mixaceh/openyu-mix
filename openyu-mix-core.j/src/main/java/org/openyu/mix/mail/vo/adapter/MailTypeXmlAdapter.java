package org.openyu.mix.mail.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.mail.vo.MailType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<mailTypes key="SYSTEM">1</mailTypes>
//--------------------------------------------------
public class MailTypeXmlAdapter extends
		BaseXmlAdapterSupporter<MailTypeXmlAdapter.MailTypeEntry, MailType> {

	public MailTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class MailTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public MailTypeEntry(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public MailTypeEntry() {
		}
	}

	// --------------------------------------------------
	public MailType unmarshal(MailTypeEntry value) throws Exception {
		MailType result = null;
		//
		if (value != null) {
			result = MailType.valueOf(value.key);
		}
		return result;
	}

	public MailTypeEntry marshal(MailType value) throws Exception {
		MailTypeEntry result = null;
		//
		if (value != null) {
			result = new MailTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
