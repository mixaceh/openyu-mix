package org.openyu.mix.app.service;

import org.openyu.mix.system.vo.Relation;
import org.openyu.socklet.message.vo.Message;

/**
 * 伺服器關連連線
 */
public interface RelationConnectable {

	/**
	 * 伺服器關連連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationConnect(String relationId, T attatch);

	/**
	 * 發送伺服器關連連線
	 * 
	 * @param relation
	 * @param attatch
	 * @return
	 */
	<T> Message sendRelationConnect(Relation relation, T attatch);

	/**
	 * 伺服器關連斷線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationDisconnect(String relationId, T attatch);

	/**
	 * 發送伺服器關連斷線
	 * 
	 * @param relation
	 * @param attatch
	 * @return
	 */
	<T> Message sendRelationDisconnect(Relation relation, T attatch);

	/**
	 * 伺服器關連無法連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	<T> Relation relationRefused(String relationId, T attatch);

	/**
	 * 發送伺服器關連無法連線
	 * 
	 * @param relation
	 * @param attatch
	 * @return
	 */
	<T> Message sendRelationRefused(Relation relation, T attatch);
}
