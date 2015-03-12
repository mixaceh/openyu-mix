package org.openyu.mix.core.log;

import org.openyu.mix.app.log.AppLogEntity;
import org.openyu.mix.core.vo.Core.ConnectAction;

/**
 * 角色連線log,不做bean,直接用entity處理掉
 */
public interface CoreConnectLog extends AppLogEntity
{

	String KEY = CoreConnectLog.class.getName();

	/**
	 * 操作類別
	 * 
	 * @return
	 */
	ConnectAction getConnectAction();

	void setConnectAction(ConnectAction connectAction);

	/**
	 * client ip
	 * 
	 * @return
	 */
	String getClientIp();

	void setClientIp(String clientIp);

	/**
	 * 上線時間
	 * 
	 * @return
	 */
	Long getEnterTime();

	void setEnterTime(Long enterTime);

	/**
	 * 離線時間
	 * 
	 * @return
	 */
	Long getLeaveTime();

	void setLeaveTime(Long leaveTime);
	
	/**
	 * 在線毫秒
	 * 
	 * @return
	 */
	Long getOnlineMills();

	void setOnlineMills(Long onlineMills);

}
