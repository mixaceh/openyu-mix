package org.openyu.mix.core.log.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.supporter.AppLogEntitySupporter;
import org.openyu.mix.core.log.CoreConnectLog;
import org.openyu.mix.core.po.bridge.ConnectActionBridge;
import org.openyu.mix.core.vo.Core.ConnectAction;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@Table(name = "mix_core_connect_log")
@SequenceGenerator(name = "mix_core_connect_log_g", sequenceName = "mix_core_connect_log_s", allocationSize = 1)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.core.log.impl.CoreConnectLogImpl")
@Proxy(lazy = false)
@org.hibernate.annotations.Table(appliesTo = "mix_core_connect_log", indexes = {
		@org.hibernate.annotations.Index(name = "idx_mix_core_connect_log_1", columnNames = { "role_id", "log_date" }),
		@org.hibernate.annotations.Index(name = "idx_mix_core_connect_log_2", columnNames = { "connect_action",
				"role_id", "enter_time" }) })
// --------------------------------------------------
// hibernate search
// --------------------------------------------------
// @Indexed
public class CoreConnectLogImpl extends AppLogEntitySupporter implements CoreConnectLog {

	private static final long serialVersionUID = 651695262563051995L;

	private Long seq;

	private ConnectAction connectAction;

	/**
	 * 客戶端ip
	 */
	private String clientIp;

	/**
	 * 客戶端port
	 */
	private Integer clientPort;

	private Long enterTime;

	private Long leaveTime;

	private Long onlineMills;

	public CoreConnectLogImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mix_core_connect_log_g")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "connect_action", length = 13)
	@Type(type = "org.openyu.mix.core.po.userType.ConnectActionUserType")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ConnectActionBridge.class)
	public ConnectAction getConnectAction() {
		return connectAction;
	}

	public void setConnectAction(ConnectAction connectAction) {
		this.connectAction = connectAction;
	}

	@Column(name = "client_ip", length = 16)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Column(name = "client_port")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getClientPort() {
		return clientPort;
	}

	public void setClientPort(Integer clientPort) {
		this.clientPort = clientPort;
	}

	@Column(name = "enter_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	@Column(name = "leave_time")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Long leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Column(name = "online_mills")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Long getOnlineMills() {
		return onlineMills;
	}

	public void setOnlineMills(Long onlineMills) {
		this.onlineMills = onlineMills;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connectAction", connectAction);
		builder.append("clientIp", clientIp);
		builder.append("clientPort", clientPort);
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		builder.append("onlineMills", onlineMills);
		return builder.toString();
	}

	public Object clone() {
		CoreConnectLogImpl copy = null;
		copy = (CoreConnectLogImpl) super.clone();
		return copy;
	}

}
