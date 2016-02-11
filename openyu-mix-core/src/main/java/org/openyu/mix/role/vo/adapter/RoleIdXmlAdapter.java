package org.openyu.mix.role.vo.adapter;

import javax.xml.bind.annotation.XmlAttribute;

import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.commons.jaxb.adapter.supporter.BaseXmlAdapterSupporter;

// --------------------------------------------------
// reslove: JAXB can’t handle interfaces
// --------------------------------------------------
//<role id="111"/>
//--------------------------------------------------
public class RoleIdXmlAdapter extends
		BaseXmlAdapterSupporter<RoleIdXmlAdapter.RoleIdEntry, Role>
{

	public RoleIdXmlAdapter()
	{}

	// --------------------------------------------------
	public static class RoleIdEntry
	{
		@XmlAttribute
		public String id;

		public RoleIdEntry(String id)
		{
			this.id = id;
		}

		public RoleIdEntry()
		{}
	}

	// --------------------------------------------------
	public Role unmarshal(RoleIdEntry value) throws Exception
	{
		Role result = null;
		//
		if (value != null)
		{
			result = new RoleImpl();
			result.setId(value.id);
		}
		return result;
	}

	public RoleIdEntry marshal(Role value) throws Exception
	{
		RoleIdEntry result = null;
		//
		if (value != null)
		{
			result = new RoleIdEntry(value.getId());
		}
		return result;
	}
}
