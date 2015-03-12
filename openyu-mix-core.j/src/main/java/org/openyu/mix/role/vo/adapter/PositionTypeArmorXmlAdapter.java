package org.openyu.mix.role.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.impl.ArmorImpl;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class PositionTypeArmorXmlAdapter
		extends
		BaseXmlAdapterSupporter<PositionTypeArmorXmlAdapter.PositionTypeArmorList, Map<PositionType, Armor>> {

	public PositionTypeArmorXmlAdapter() {
	}

	// --------------------------------------------------
	public static class PositionTypeArmorList {
		public List<PositionTypeArmorEntry> entry = new LinkedList<PositionTypeArmorEntry>();
	}

	public static class PositionTypeArmorEntry {
		@XmlAttribute
		public PositionType key;

		@XmlElement(type = ArmorImpl.class)
		public Armor value;

		public PositionTypeArmorEntry(PositionType key, Armor value) {
			this.key = key;
			this.value = value;
		}

		public PositionTypeArmorEntry() {
		}

	}

	// --------------------------------------------------
	public Map<PositionType, Armor> unmarshal(PositionTypeArmorList value)
			throws Exception {
		Map<PositionType, Armor> result = new LinkedHashMap<PositionType, Armor>();
		if (value != null) {
			for (PositionTypeArmorEntry entry : value.entry) {
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public PositionTypeArmorList marshal(Map<PositionType, Armor> value)
			throws Exception {
		PositionTypeArmorList result = new PositionTypeArmorList();
		//
		if (value != null) {
			for (Map.Entry<PositionType, Armor> entry : value.entrySet()) {
				result.entry.add(new PositionTypeArmorEntry(entry.getKey(),
						entry.getValue()));
			}
		}
		return result;
	}
}
