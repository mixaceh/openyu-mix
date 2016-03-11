package org.openyu.mix.system.service;

import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.system.vo.Relation;

/**
 * 服器關連儲存庫, 存放所有本地的關連在mem中
 */
public interface RelationRepository extends AppService {

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 加入已上線伺服器關連
	 * 
	 * @param relation
	 * @return
	 */
	Relation addRelation(Relation relation);

	/**
	 * 取得已上線伺服器關連
	 * 
	 * @param relationId
	 * @return
	 */
	Relation getRelation(String relationId);

	/**
	 * 取得所有已上線伺服器關連
	 * 
	 * @return
	 */
	Map<String, Relation> getRelations();

	/**
	 * 取得所有已上線伺服器關連的id
	 * 
	 * @return
	 */
	List<String> getRelationIds();

	/**
	 * 移除伺服器關連
	 * 
	 * @param relationId
	 * @return
	 */
	Relation removeRelation(String relationId);

	/**
	 * 移除伺服器關連
	 * 
	 * @param relation
	 * @return
	 */
	Relation removeRelation(Relation relation);

	/**
	 * 伺服器關連是否存在
	 * 
	 * @param relationId
	 * @return
	 */
	boolean containRelation(String relationId);

	/**
	 * 伺服器關連是否存在
	 * 
	 * @param relation
	 * @return
	 */
	boolean containRelation(Relation relation);

	// --------------------------------------------------

	/**
	 * 加入註冊的伺服器關連
	 *
	 * @param relation
	 * @return
	 */
	Relation addRegisterRelation(Relation relation);

	/**
	 * 取得註冊的伺服器關連
	 * 
	 * @param relationId
	 * @return
	 */
	Relation getRegisterRelation(String relationId);
	
	/**
	 * 取得所有註冊的伺服器關連
	 *
	 * @return
	 */
	Map<String, Relation> getRegisterRelations();

	/**
	 * 取得所有註冊的伺服器關連的id
	 *
	 * @return
	 */
	List<String> getRegisterRelationIds();
}
