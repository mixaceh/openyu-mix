package org.openyu.mix.system.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.system.vo.Relation;
import org.openyu.commons.bean.supporter.SeqIdAuditNamesBeanSupporter;

/**
 * 伺服器關連
 */
@XmlRootElement(name = "relation")
@XmlAccessorType(XmlAccessType.FIELD)
public class RelationImpl extends SeqIdAuditNamesBeanSupporter implements Relation {

	private static final long serialVersionUID = -4045957311344008929L;

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
	 * 接收器id
	 */
	private String acceptorId;

	public RelationImpl() {
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

	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connected", connected);
		builder.append("valid", valid);
		//
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		builder.append("acceptorId", acceptorId);
		return builder.toString();
	}
}
