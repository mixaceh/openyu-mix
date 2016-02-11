package org.openyu.mix.app.vo.supporter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.mix.app.vo.AppResult;
import org.openyu.mix.item.vo.Item;

import org.openyu.commons.bean.LocaleNameBean;
import org.openyu.commons.bean.NamesBean;
import org.openyu.commons.bean.WeightBean;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

@XmlRootElement(name = "appResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppResultSupporter extends BaseBeanSupporter implements AppResult
{

	private static final long serialVersionUID = -387142316817761028L;

	public AppResultSupporter()
	{

	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
		builder.appendSuper(super.toString());
		return builder.toString();
	}

	public Object clone()
	{
		AppResultSupporter copy = null;
		copy = (AppResultSupporter) super.clone();
		return copy;
	}

	protected void append(ToStringBuilder builder, String fieldName, List<Item> items)
	{
		if (items != null)
		{
			List<String> buff = new LinkedList<String>();
			for (Item entry : items)
			{
				if (entry != null)
				{
					buff.add(entry.getId() + "," + entry.getAmount());
				}
				else
				{
					buff.add(null);
				}

			}
			builder.append(fieldName, buff);
		}
		else
		{
			builder.append(fieldName, (Object) null);
		}
	}
}
