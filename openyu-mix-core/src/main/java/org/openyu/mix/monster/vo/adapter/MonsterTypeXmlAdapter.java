package org.openyu.mix.monster.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;
import org.openyu.mix.monster.vo.MonsterType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<monsterTypes key="NORMAL">1</monsterTypes>
//--------------------------------------------------
public class MonsterTypeXmlAdapter extends
		BaseXmlAdapterSupporter<MonsterTypeXmlAdapter.MonsterTypeEntry, MonsterType>
{

	public MonsterTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class MonsterTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public MonsterTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public MonsterTypeEntry()
		{}
	}

	// --------------------------------------------------
	public MonsterType unmarshal(MonsterTypeEntry value) throws Exception
	{
		MonsterType result = null;
		//
		if (value != null)
		{
			result = MonsterType.valueOf(value.key);
		}
		return result;
	}

	public MonsterTypeEntry marshal(MonsterType value) throws Exception
	{
		MonsterTypeEntry result = null;
		//
		if (value != null)
		{
			result = new MonsterTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
