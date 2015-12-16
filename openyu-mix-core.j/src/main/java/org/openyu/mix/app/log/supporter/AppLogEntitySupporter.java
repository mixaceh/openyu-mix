package org.openyu.mix.app.log.supporter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.commons.entity.supporter.SeqLogEntitySupporter;

@MappedSuperclass
public abstract class AppLogEntitySupporter extends SeqLogEntitySupporter implements AppLogEntity {

	private static final long serialVersionUID = -4598280202074750416L;

	/**
	 * 帳號id
	 */
	private String accountId;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 角色名稱
	 */
	private String roleName;

	/**
	 * 取得接收器 id
	 */
	private String acceptorId;

	/**
	 * 伺服器ip
	 */
	private String serverIp;

	/**
	 * 伺服器port
	 */
	private Integer serverPort;

	public AppLogEntitySupporter() {
	}

	@Column(name = "account_id", length = 255)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "role_id", length = 255)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name", length = 255)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "acceptor_id", length = 30)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	@Column(name = "server_ip", length = 15)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Column(name = "server_port")
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("accountId", accountId);
		builder.append("roleId", roleId);
		builder.append("roleName", roleName);
		builder.append("acceptorId", acceptorId);
		builder.append("slaveIp", serverIp);
		builder.append("serverPort", serverPort);
		return builder.toString();
	}

	public Object clone() {
		AppLogEntitySupporter copy = null;
		copy = (AppLogEntitySupporter) super.clone();
		return copy;
	}
}
