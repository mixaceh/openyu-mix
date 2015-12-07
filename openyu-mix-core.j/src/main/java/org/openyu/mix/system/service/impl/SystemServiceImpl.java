package org.openyu.mix.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.system.service.SystemService;
import org.openyu.mix.system.service.RelationSetService;
import org.openyu.mix.system.vo.Context;
import org.openyu.mix.system.vo.Relation;
import org.openyu.mix.system.vo.impl.ContextImpl;
import org.openyu.mix.system.vo.impl.RelationImpl;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 系統服務
 */
public class SystemServiceImpl extends AppServiceSupporter implements SystemService {

	private static final long serialVersionUID = 4448768239462292756L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SystemServiceImpl.class);

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	@Autowired
	@Qualifier("relationSetService")
	protected transient RelationSetService relationSetService;

	/**
	 * 本文
	 * 
	 * 當CoreContextAdapter無註冊到acceptor時, context會是null, 參考setContext()所呼叫的代碼
	 */
	private Context context;

	public SystemServiceImpl() {
	}

	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return null;
		}

		// 發送連線
		sendRoleConnect(result, attatch);

		return result;
	}

	/**
	 * 發送角色連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleConnect(Role role, T attatch) {
		Message message = sendInitialize(role);
		return message;
	}

	/**
	 * 發送初始
	 * 
	 * @param role
	 * @return
	 */
	protected Message sendInitialize(Role role) {
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_INITIALIZE_RESPONSE, role.getId());

		// 當CoreContextAdapter無註冊到acceptor時, context會是null, 參考setContext()所呼叫的代碼
		// 0, String, contextId 本文id
		message.addString((context != null ? this.context.getId() : ""));

		// 取得所有註冊的伺服器關連
		Map<String, Relation> registerRelations = relationSetService.getRegisterRelations();
		int size = registerRelations.size();
		message.addInt(size);// 1, int, size
		//
		for (Relation registerRelation : registerRelations.values()) {
			message.addString(registerRelation.getId());// 2, String, relationId
														// 伺服器關連id
			message.addBoolean(registerRelation.isConnected());// 3, boolean,
																// 是否連線
			message.addLong(registerRelation.getEnterTime());// 4, long, 上線時間
			message.addLong(registerRelation.getLeaveTime());// 5, long, 離線時間
		}

		messageService.addMessage(message);

		return message;
	}

	/**
	 * 角色斷線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleDisconnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return null;
		}

		// 發送斷線
		sendRoleDisconnect(result, attatch);
		return result;
	}

	/**
	 * 發送角色斷線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleDisconnect(Role role, T attatch) {
		return null;
	}

	/**
	 * 本文連線
	 * 
	 * @param contextId
	 * @param attatch
	 * @return
	 */
	public <T> Context contextConnect(String contextId, T attatch) {
		Context result = getContext();
		if (contextId == null || result == null || !contextId.equals(result.getId())) {
			return null;
		}
		// acceptor
		result.setAcceptor(getAcceptor());
		// 上線時間
		result.setEnterTime(System.currentTimeMillis());
		// 已連線
		result.setConnected(true);

		// 載入欲加入的伺服器關連
		Set<String> relations = getAcceptorService().getRelations().keySet();
		for (String rationId : relations) {
			Relation relation = new RelationImpl();
			relation.setId(rationId);
			// 加入註冊的伺服器關連
			relationSetService.addRegisterRelation(relation);
		}
		//
		// System.out.println("getRegisterRelationIds: "
		// + relationSetService.getRegisterRelationIds());

		// 發送連線
		sendContextConnect(result, attatch);

		return result;
	}

	/**
	 * 發送本文連線
	 * 
	 * @param context
	 * @param attatch
	 * @return
	 */
	public <T> Message sendContextConnect(Context context, T attatch) {
		// 發送給所有client
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_CONTEXT_CONNECT_RESPONSE, receivers);

		message.addString(context.getId());// 0, String, contextId 本文id
		message.addLong(context.getEnterTime());// 1, long, 上線時間

		messageService.addMessage(message);

		return message;
	}

	/**
	 * 本文斷線
	 * 
	 * @param contextId
	 * @param attatch
	 * @return
	 */
	public <T> Context contextDisconnect(String contextId, T attatch) {
		Context result = getContext();
		if (contextId == null || result == null || !contextId.equals(result.getId())) {
			return null;
		}
		// 離線時間
		result.setLeaveTime(System.currentTimeMillis());
		// 已斷線
		result.setConnected(false);

		// 發送斷線
		sendContextDisconnect(result, attatch);

		return result;
	}

	/**
	 * 發送本文斷線
	 * 
	 * @param context
	 * @param attatch
	 * @return
	 */
	public <T> Message sendContextDisconnect(Context context, T attatch) {
		// 發送給所有client
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_CONTEXT_DISCONNECT_RESPONSE, receivers);

		message.addString(context.getId());// 0, String, contextId 本文id
		message.addLong(context.getLeaveTime());// 1, long, 離線時間

		messageService.addMessage(message);

		return message;
	}

	/**
	 * 伺服器關連連線
	 *
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationConnect(String relationId, T attatch) {
		Relation result = relationSetService.getRelation(relationId);
		if (result == null) {
			return null;
		}
		// acceptor
		result.setAcceptor(getAcceptor());
		// 上線時間
		result.setEnterTime(System.currentTimeMillis());
		// 已連線
		result.setConnected(true);
		//
		Relation registerRelation = relationSetService.getRegisterRelation(relationId);
		if (registerRelation != null) {
			registerRelation.setEnterTime(result.getEnterTime());
			registerRelation.setConnected(result.isConnected());
		}

		// 發送連線
		sendRelationConnect(result, attatch);

		return result;
	}

	/**
	 * 發送伺服器關連連線
	 *
	 * @param relation
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRelationConnect(Relation relation, T attatch) {
		// 發送給所有client
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_RELATION_CONNECT_RESPONSE, receivers);

		message.addString(relation.getId());// 0, String, relationId 伺服器關連id
		message.addLong(relation.getEnterTime());// 1, long, 上線時間

		messageService.addMessage(message);

		return message;
	}

	/**
	 * 伺服器關連斷線
	 *
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationDisconnect(String relationId, T attatch) {
		Relation result = relationSetService.getRelation(relationId);
		if (result == null) {
			return null;
		}
		// 離線時間
		result.setLeaveTime(System.currentTimeMillis());
		// 已斷線
		result.setConnected(false);
		//
		Relation registerRelation = relationSetService.getRegisterRelation(relationId);
		if (registerRelation != null) {
			registerRelation.setLeaveTime(result.getLeaveTime());
			registerRelation.setConnected(result.isConnected());
		}

		// 發送斷線
		sendRelationDisconnect(result, attatch);

		return result;
	}

	/**
	 * 發送伺服器關連斷線
	 * 
	 * @param relation
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRelationDisconnect(Relation relation, T attatch) {
		// 發送給所有client
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_RELATION_DISCONNECT_RESPONSE, receivers);

		message.addString(relation.getId());// 0, String, relationId 伺服器關連id
		message.addLong(relation.getLeaveTime());// 1, long, 離線時間

		messageService.addMessage(message);

		return message;
	}

	/**
	 * 伺服器關連無法連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationRefused(String relationId, T attatch) {
		Relation result = createRelation(relationId, null);

		// 發送無法連線
		sendRelationRefused(result, attatch);

		return result;
	}

	/**
	 * 發送伺服器關連無法連線
	 * 
	 * @param relation
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRelationRefused(Relation relation, T attatch) {
		// 發送給所有client
		List<String> receivers = roleSetService.getRoleIds();
		//
		Message message = messageService.createMessage(CoreModuleType.SYSTEM, CoreModuleType.CLIENT,
				CoreMessageType.SYSTEM_RELATION_REFUSED_RESPONSE, receivers);

		message.addString(relation.getId());// 0, String, relationId 伺服器關連id

		messageService.addMessage(message);

		return message;
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------

	/**
	 * 取得本文
	 * 
	 * @return
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * 設定本文
	 * 
	 * @param context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * 建構本文
	 *
	 * @param contextId
	 * @param name
	 * @return
	 */
	public Context createContext(String contextId, String name) {
		Context result = null;
		// relation
		result = new ContextImpl();
		result.setId(contextId);
		result.setName(name);
		result.setValid(true);
		//
		return result;
	}

	/**
	 * 建構伺服器關連
	 *
	 * @param relationId
	 * @param name
	 * @return
	 */
	public Relation createRelation(String relationId, String name) {
		Relation result = null;
		// relation
		result = new RelationImpl();
		result.setId(relationId);
		result.setName(name);
		result.setValid(true);
		//
		return result;
	}
}
