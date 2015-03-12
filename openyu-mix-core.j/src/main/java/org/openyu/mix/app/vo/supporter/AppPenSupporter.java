package org.openyu.mix.app.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.app.vo.AppPen;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

@XmlRootElement(name = "appPen")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppPenSupporter extends BaseBeanSupporter implements AppPen
{

	private static final long serialVersionUID = -7650275922973124038L;

	/**
	 * 是否已連線
	 */
	@XmlTransient
	private boolean connected;

	public AppPenSupporter()
	{}

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	public boolean isConnected()
	{
		return connected;
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connected", connected);
		return builder.toString();
	}

	public Object clone()
	{
		AppPenSupporter copy = null;
		copy = (AppPenSupporter) super.clone();
		return copy;
	}
}
