package org.openyu.mix.role.vo.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;
import org.openyu.mix.item.vo.PositionType;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.item.vo.impl.WeaponImpl;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
public class PositionTypeWeaponXmlAdapter
		extends
		BaseXmlAdapterSupporter<PositionTypeWeaponXmlAdapter.PositionTypeWeaponList, Map<PositionType, Weapon>> {

	public PositionTypeWeaponXmlAdapter() {
	}

	// --------------------------------------------------
	public static class PositionTypeWeaponList {
		public List<PositionTypeWeaponEntry> entry = new LinkedList<PositionTypeWeaponEntry>();
	}

	public static class PositionTypeWeaponEntry {
		@XmlAttribute
		public PositionType key;

		@XmlElement(type = WeaponImpl.class)
		public Weapon value;

		public PositionTypeWeaponEntry(PositionType key, Weapon value) {
			this.key = key;
			this.value = value;
		}

		public PositionTypeWeaponEntry() {
		}

	}

	// --------------------------------------------------
	public Map<PositionType, Weapon> unmarshal(PositionTypeWeaponList value)
			throws Exception {
		Map<PositionType, Weapon> result = new LinkedHashMap<PositionType, Weapon>();
		if (value != null) {
			for (PositionTypeWeaponEntry entry : value.entry) {
				result.put(entry.key, entry.value);
			}
		}
		return result;
	}

	public PositionTypeWeaponList marshal(Map<PositionType, Weapon> value)
			throws Exception {
		PositionTypeWeaponList result = new PositionTypeWeaponList();
		//
		if (value != null) {
			for (Map.Entry<PositionType, Weapon> entry : value.entrySet()) {
				result.entry.add(new PositionTypeWeaponEntry(entry.getKey(),
						entry.getValue()));
			}
		}
		return result;
	}
}
