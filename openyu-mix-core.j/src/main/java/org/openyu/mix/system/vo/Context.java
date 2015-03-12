package org.openyu.mix.system.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.core.vo.Core;
import org.openyu.commons.bean.IdNamesBean;

import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 本文
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Context extends IdNamesBean {

	String KEY = Core.class.getName();

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	boolean isConnected();

	void setConnected(boolean connected);

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	boolean getValid();

	void setValid(boolean valid);

	/**
	 * 上線時間
	 * 
	 * @return
	 */
	long getEnterTime();

	void setEnterTime(long enterTime);

	/**
	 * 離線時間
	 * 
	 * @return
	 */
	long getLeaveTime();

	void setLeaveTime(long leaveTime);

	/**
	 * acceptor
	 * 
	 * @return
	 */
	String getAcceptor();

	void setAcceptor(String acceptor);
}
