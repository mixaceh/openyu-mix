package org.openyu.mix.activity.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.activity.vo.ActivityType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<activityTypes key="DAILY_COIN">1</activityTypes>
//--------------------------------------------------
public class ActivityTypeXmlAdapter
		extends
		BaseXmlAdapterSupporter<ActivityTypeXmlAdapter.ActivityTypeEntry, ActivityType> {

	public ActivityTypeXmlAdapter() {
	}

	// --------------------------------------------------
	public static class ActivityTypeEntry {
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public ActivityTypeEntry(String key, int value) {
			this.key = key;
			this.value = value;
		}

		public ActivityTypeEntry() {
		}
	}

	// --------------------------------------------------
	public ActivityType unmarshal(ActivityTypeEntry value) throws Exception {
		ActivityType result = null;
		//
		if (value != null) {
			result = ActivityType.valueOf(value.key);
		}
		return result;
	}

	public ActivityTypeEntry marshal(ActivityType value) throws Exception {
		ActivityTypeEntry result = null;
		//
		if (value != null) {
			result = new ActivityTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
