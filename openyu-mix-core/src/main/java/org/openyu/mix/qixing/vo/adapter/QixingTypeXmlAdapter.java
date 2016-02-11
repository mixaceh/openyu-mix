package org.openyu.mix.qixing.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;

import org.openyu.mix.qixing.vo.QixingType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<qixingTypes key="TIANSHU">1</qixingTypes>
//--------------------------------------------------
public class QixingTypeXmlAdapter extends
		BaseXmlAdapterSupporter<QixingTypeXmlAdapter.QixingTypeEntry, QixingType>
{

	public QixingTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class QixingTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public QixingTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public QixingTypeEntry()
		{}
	}

	// --------------------------------------------------
	public QixingType unmarshal(QixingTypeEntry value) throws Exception
	{
		QixingType result = null;
		//
		if (value != null)
		{
			result = QixingType.valueOf(value.key);
		}
		return result;
	}

	public QixingTypeEntry marshal(QixingType value) throws Exception
	{
		QixingTypeEntry result = null;
		//
		if (value != null)
		{
			result = new QixingTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
