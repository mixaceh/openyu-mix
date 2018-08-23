package org.openyu.mix.system.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.system.service.RelationRepository;
import org.openyu.mix.system.vo.Relation;
import org.openyu.commons.util.concurrent.NullValueMap;
import org.openyu.commons.util.concurrent.impl.NullValueMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 伺服器關連儲存庫
 */
public class RelationRepositoryImpl extends AppServiceSupporter implements RelationRepository {

	private static final long serialVersionUID = -1599710087845689459L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RelationRepositoryImpl.class);

	/**
	 * 所有註冊的伺服器關連, connected=是否已連線
	 */
	protected NullValueMap<String, Relation> registerRelations = new NullValueMapImpl<String, Relation>();

	public RelationRepositoryImpl() {
	}

	@Override
	protected void checkConfig() throws Exception {

	}
	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 加入伺服器關連
	 *
	 * @param relation
	 * @return
	 */
	public Relation addRelation(Relation relation) {
		Relation result = null;
		if (relation != null) {
			result = (Relation) beans.put(relation.getId(), relation);
		}
		return result;
	}

	/**
	 * 取得伺服器關連
	 *
	 * @param relationId
	 * @return
	 */
	public Relation getRelation(String relationId) {
		Relation result = null;
		if (relationId != null) {
			result = (Relation) beans.get(relationId);
		}
		return result;
	}

	/**
	 * 取得所有已上線伺服器關連
	 *
	 * @return
	 */
	public Map<String, Relation> getRelations() {
		Map<String, Relation> result = new LinkedHashMap<String, Relation>();
		for (Object entry : beans.getValues()) {
			if (entry instanceof Relation) {
				Relation value = (Relation) entry;
				result.put(value.getId(), value);
			}
		}
		return result;
	}

	/**
	 * 取得所有已上線伺服器關連的id
	 *
	 * @return
	 */
	public List<String> getRelationIds() {
		List<String> result = new LinkedList<String>();
		for (String relationId : beans.getKeys()) {
			result.add(relationId);
		}
		return result;
	}

	/**
	 * 移除伺服器關連
	 *
	 * @param relationId
	 * @return
	 */
	public Relation removeRelation(String relationId) {
		Relation result = null;
		if (relationId != null) {
			result = (Relation) beans.remove(relationId);
		}
		return result;
	}

	/**
	 * 移除伺服器關連
	 *
	 * @param relation
	 * @return
	 */
	public Relation removeRelation(Relation relation) {
		Relation result = null;
		if (relation != null) {
			result = removeRelation(relation.getId());
		}
		return result;
	}

	/**
	 * 伺服器關連是否存在
	 *
	 * @param relationId
	 * @return
	 */
	public boolean containRelation(String relationId) {
		boolean result = false;
		if (relationId != null) {
			result = beans.contains(relationId);
		}
		return result;
	}

	/**
	 * 伺服器關連是否存在
	 *
	 * @param relation
	 * @return
	 */
	public boolean containRelation(Relation relation) {
		boolean result = false;
		if (relation != null) {
			result = containRelation(relation.getId());
		}
		return result;
	}

	// --------------------------------------------------

	// /**
	// * 初始所有伺服器關連
	// */
	// protected void initAllRelations() {
	// if (registerRelations == null) {
	// registerRelations = getAcceptorService().getRelations();
	// for (String rationId : registerRelations) {
	// Relation relation = new RelationImpl();
	// relation.setId(rationId);
	// allRelations.put(rationId, relation);
	// }
	// }
	// }

	/**
	 * 加入註冊的伺服器關連
	 *
	 * @param relation
	 * @return
	 */
	public Relation addRegisterRelation(Relation relation) {
		Relation result = null;
		if (relation != null) {
			result = (Relation) registerRelations.put(relation.getId(), relation);
		}
		return result;
	}

	/**
	 * 取得伺服器關連
	 *
	 * @param relationId
	 * @return
	 */
	public Relation getRegisterRelation(String relationId) {
		Relation result = null;
		if (relationId != null) {
			result = (Relation) registerRelations.get(relationId);
		}
		return result;
	}

	/**
	 * 取得所有註冊的伺服器關連
	 *
	 * @return
	 */
	public Map<String, Relation> getRegisterRelations() {
		Map<String, Relation> result = new LinkedHashMap<String, Relation>();
		for (Object entry : registerRelations.getValues()) {
			if (entry instanceof Relation) {
				Relation value = (Relation) entry;
				result.put(value.getId(), value);
			}
		}
		return result;
	}

	/**
	 * 取得所有註冊的伺服器關連的id
	 *
	 * @return
	 */
	public List<String> getRegisterRelationIds() {
		List<String> result = new LinkedList<String>();
		for (String relationId : registerRelations.getKeys()) {
			result.add(relationId);
		}
		return result;
	}

}
