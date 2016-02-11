package org.openyu.mix.app.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openyu.mix.app.vo.RoleResult;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.role.vo.Role;

@XmlRootElement(name = "roleResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleResultImpl extends AppResultSupporter implements RoleResult
{

	private static final long serialVersionUID = -541581150095137730L;

	/**
	 * 角色
	 */
	private Role role;

	public RoleResultImpl()
	{

	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.appendSuper(super.toString());
		builder.append("role", (role != null ? role.getId() : null));
		return builder.toString();
	}

	public Object clone()
	{
		RoleResultImpl copy = null;
		copy = (RoleResultImpl) super.clone();
		copy.role = clone(role);
		return copy;
	}
}
