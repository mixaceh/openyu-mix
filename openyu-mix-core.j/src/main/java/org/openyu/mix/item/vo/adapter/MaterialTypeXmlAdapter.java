package org.openyu.mix.item.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlValue;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<materialTypes key="CLOTH_COTTON">1</materialTypes>
//--------------------------------------------------
public class MaterialTypeXmlAdapter extends
		BaseXmlAdapterSupporter<MaterialTypeXmlAdapter.MaterialTypeEntry, MaterialType>
{

	public MaterialTypeXmlAdapter()
	{}

	// --------------------------------------------------
	public static class MaterialTypeEntry
	{
		@XmlAttribute
		public String key;

		@XmlValue
		public int value;

		public MaterialTypeEntry(String key, int value)
		{
			this.key = key;
			this.value = value;
		}

		public MaterialTypeEntry()
		{}
	}

	// --------------------------------------------------
	public MaterialType unmarshal(MaterialTypeEntry value) throws Exception
	{
		MaterialType result = null;
		//
		if (value != null)
		{
			result = MaterialType.valueOf(value.key);
		}
		return result;
	}

	public MaterialTypeEntry marshal(MaterialType value) throws Exception
	{
		MaterialTypeEntry result = null;
		//
		if (value != null)
		{
			result = new MaterialTypeEntry(value.name(), value.getValue());
		}
		return result;
	}
}
