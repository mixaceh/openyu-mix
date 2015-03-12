package org.openyu.mix.app.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.Notice;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "notice")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoticeImpl extends BaseBeanSupporter implements Notice
{

	private static final long serialVersionUID = 4939315050366678055L;

	private String roleName;

	/**
	 * 獎勵
	 * 
	 * <道具名稱,數量>
	 */
	private Map<String, Integer> awards = new LinkedHashMap<String, Integer>();

	public NoticeImpl()
	{}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Map<String, Integer> getAwards()
	{
		return awards;
	}

	public void setAwards(Map<String, Integer> awards)
	{
		this.awards = awards;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("roleName", roleName);
		builder.append("awards", awards);
		return builder.toString();
	}

	public Object clone()
	{
		NoticeImpl copy = null;
		copy = (NoticeImpl) super.clone();
		copy.awards = clone(awards);
		return copy;
	}

}
