package org.openyu.mix.treasure.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.treasure.vo.Treasure;
import org.openyu.mix.treasure.vo.Stock;
import org.openyu.mix.treasure.vo.adapter.StringTreasureXmlAdapter;
import org.openyu.commons.bean.supporter.IdBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "stock")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockImpl extends IdBeanSupporter implements Stock
{

	private static final long serialVersionUID = 7649879813236304134L;

	/**
	 * 祕寶
	 * 
	 * <祕寶id,祕寶>
	 */
	@XmlJavaTypeAdapter(StringTreasureXmlAdapter.class)
	private Map<String, Treasure> treasures = new LinkedHashMap<String, Treasure>();

	public StockImpl(String id)
	{
		setId(id);
	}

	public StockImpl()
	{
		this(null);
	}

	public Map<String, Treasure> getTreasures()
	{
		return treasures;
	}

	public void setTreasures(Map<String, Treasure> treasures)
	{
		this.treasures = treasures;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("treasures", treasures);
		return builder.toString();
	}

	public Object clone()
	{
		StockImpl copy = null;
		copy = (StockImpl) super.clone();
		copy.treasures = clone(treasures);
		return copy;
	}

}
