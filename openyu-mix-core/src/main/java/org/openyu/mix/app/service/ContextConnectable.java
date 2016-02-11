package org.openyu.mix.app.service;

import org.openyu.mix.system.vo.Context;
import org.openyu.socklet.message.vo.Message;

/**
 * 本文連線
 */
public interface ContextConnectable {

	/**
	 * 本文連線
	 * 
	 * @param contextId
	 * @param attatch
	 * @return
	 */
	<T> Context contextConnect(String contextId, T attatch);

	/**
	 * 發送本文連線
	 * 
	 * @param context
	 * @param attatch
	 * @return
	 */
	<T> Message sendContextConnect(Context context, T attatch);

	/**
	 * 本文斷線
	 * 
	 * @param contextId
	 * @param attatch
	 * @return
	 */
	<T> Context contextDisconnect(String contextId, T attatch);

	/**
	 * 發送本文斷線
	 * 
	 * @param context
	 * @param attatch
	 * @return
	 */
	<T> Message sendContextDisconnect(Context context, T attatch);
}
