package org.openyu.mix.treasure.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.app.vo.supporter.AppPenSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.adapter.IntegerTreasureXmlAdapter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "treasurePen")
@XmlAccessorType(XmlAccessType.FIELD)
public class TreasurePenImpl extends AppPenSupporter implements TreasurePen
{

	private static final long serialVersionUID = 5073307472276879735L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 刷新時間
	 */
	private long refreshTime;

	/**
	 * 上架祕寶
	 */
	@XmlJavaTypeAdapter(IntegerTreasureXmlAdapter.class)
	private Map<Integer, Treasure> treasures = new LinkedHashMap<Integer, Treasure>();

	public TreasurePenImpl(Role role)
	{
		this.role = role;
	}

	public TreasurePenImpl()
	{
		this(null);
	}

	public long getRefreshTime()
	{
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime)
	{
		this.refreshTime = refreshTime;
	}

	public Map<Integer, Treasure> getTreasures()
	{
		return treasures;
	}

	public void setTreasures(Map<Integer, Treasure> treasures)
	{
		this.treasures = treasures;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("refreshTime", refreshTime);
		builder.append("treasures", treasures);
		return builder.toString();
	}

	public Object clone()
	{
		TreasurePenImpl copy = null;
		copy = (TreasurePenImpl) super.clone();
		copy.treasures = clone(treasures);
		//role不要clone,會造成loop
		return copy;
	}
}
