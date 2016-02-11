package org.openyu.mix.system.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.system.vo.Context;
import org.openyu.commons.bean.supporter.IdNamesBeanSupporter;

/**
 * 本文
 */
@XmlRootElement(name = "context")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextImpl extends IdNamesBeanSupporter implements Context {

	private static final long serialVersionUID = -6317268162989120886L;

	/**
	 * 是否已連線
	 */
	@XmlTransient
	private boolean connected;

	/**
	 * 是否有效
	 */
	private boolean valid;

	/**
	 * 上線時間
	 */
	private long enterTime;

	/**
	 * 離線時間
	 */
	private long leaveTime;

	/**
	 * acceptor
	 */
	private String acceptor;

	public ContextImpl() {
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

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}

	public long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connected", connected);
		builder.append("valid", valid);
		//
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		builder.append("acceptor", acceptor);
		return builder.toString();
	}
}
