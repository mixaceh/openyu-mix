package org.openyu.mix.app.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.app.vo.AppInfo;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

/**
 * 欄位資訊
 */
@XmlRootElement(name = "appInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppInfoSupporter extends BaseBeanSupporter implements AppInfo {

	private static final long serialVersionUID = -7650275922973124038L;

	/**
	 * 是否已連線
	 */
	@XmlTransient
	private boolean connected;

	public AppInfoSupporter() {
	}

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connected", connected);
		return builder.toString();
	}

	public Object clone() {
		AppInfoSupporter copy = null;
		copy = (AppInfoSupporter) super.clone();
		return copy;
	}
}
