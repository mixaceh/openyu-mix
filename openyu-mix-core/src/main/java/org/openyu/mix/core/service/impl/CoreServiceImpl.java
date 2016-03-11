package org.openyu.mix.core.service.impl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.AccountResult;
import org.openyu.mix.app.vo.RoleResult;
import org.openyu.mix.app.vo.impl.AccountResultImpl;
import org.openyu.mix.app.vo.impl.RoleResultImpl;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.chat.service.ChatSetService;
import org.openyu.mix.chat.service.StoreChatService;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.core.service.CoreService;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.manor.service.adapter.ManorChangeAdapter;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleRepository;
import org.openyu.mix.role.service.StoreRoleService;
import org.openyu.mix.role.service.adapter.RoleChangeAdapter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.sasang.service.adapter.SasangChangeAdapter;
import org.openyu.mix.system.service.RelationRepository;
import org.openyu.mix.system.service.SystemService;
import org.openyu.mix.system.vo.Context;
import org.openyu.mix.system.vo.Relation;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.train.service.adapter.TrainChangeAdapter;
import org.openyu.mix.treasure.service.TreasureService;
import org.openyu.mix.treasure.service.adapter.TreasureChangeAdapter;
import org.openyu.mix.wuxing.service.WuxingService;
import org.openyu.mix.wuxing.service.adapter.WuxingChangeAdapter;
import org.openyu.commons.io.FileHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.socklet.message.vo.Message;

public class CoreServiceImpl extends AppServiceSupporter implements CoreService {

	private static final long serialVersionUID = -5090784355697979943L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(CoreServiceImpl.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("roleRepository")
	protected transient RoleRepository roleRepository;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("storeRoleService")
	protected transient StoreRoleService storeRoleService;

	@Autowired
	@Qualifier("roleChangeAdapter")
	protected transient RoleChangeAdapter roleChangeAdapter;

	@Autowired
	@Qualifier("systemService")
	protected transient SystemService systemService;

	@Autowired
	@Qualifier("relationRepository")
	protected transient RelationRepository relationRepository;

	@Autowired
	@Qualifier("chatSetService")
	protected transient ChatSetService chatSetService;

	@Autowired
	@Qualifier("chatService")
	protected transient ChatService chatService;

	@Autowired
	@Qualifier("storeChatService")
	protected transient StoreChatService storeChatService;

	@Autowired
	@Qualifier("sasangService")
	protected transient SasangService sasangService;

	@Autowired
	@Qualifier("sasangChangeAdapter")
	protected transient SasangChangeAdapter sasangChangeAdapter;

	@Autowired
	@Qualifier("manorService")
	protected transient ManorService manorService;

	@Autowired
	@Qualifier("manorChangeAdapter")
	protected transient ManorChangeAdapter manorChangeAdapter;

	@Autowired
	@Qualifier("treasureService")
	protected transient TreasureService treasureService;

	@Autowired
	@Qualifier("treasureChangeAdapter")
	protected transient TreasureChangeAdapter treasureChangeAdapter;

	@Autowired
	@Qualifier("trainService")
	protected transient TrainService trainService;

	@Autowired
	@Qualifier("trainChangeAdapter")
	protected transient TrainChangeAdapter trainChangeAdapter;

	@Autowired
	@Qualifier("wuxingService")
	protected transient WuxingService wuxingService;

	@Autowired
	@Qualifier("wuxingChangeAdapter")
	protected transient WuxingChangeAdapter wuxingChangeAdapter;

	public CoreServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {

	}

	/**
	 * 角色連線,用多緒處理
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 * 
	 * @see CoreRoleConnectInterceptor
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = null;// 角色
		try {
			// --------------------------------------------------
			// 檢查角色
			// --------------------------------------------------
			CheckRoleResult checkRoleResult = checkRole(roleId);
			result = checkRoleResult.getRole();// 角色
			Account account = checkRoleResult.getAccount();// 帳號
			//
			if (ErrorType.NO_ERROR != checkRoleResult.getErrorType()) {
				return result;
			}

			// 加到mem
			roleRepository.addRole(result);

			// --------------------------------------------------
			// 檢查聊天
			// --------------------------------------------------
			Chat chat = checkChat(roleId);
			// 加到mem
			if (chat != null) {
				chatSetService.addChat(chat);
			}

			// --------------------------------------------------
			// 加入監聽
			// --------------------------------------------------
			result.addBeanChangeListener(roleChangeAdapter);// 角色
			result.addBeanChangeListener(sasangChangeAdapter);// 莊園
			result.addBeanChangeListener(manorChangeAdapter);// 莊園
			result.addBeanChangeListener(treasureChangeAdapter);// 祕寶
			result.addBeanChangeListener(trainChangeAdapter);// 訓練
			result.addBeanChangeListener(wuxingChangeAdapter);// 五行

			// 發送連線
			sendRoleConnect(result, account);

			// --------------------------------------------------
			// 注意:模組,請有順序的連線
			// --------------------------------------------------
			// attatch=account
			systemService.roleConnect(roleId, account);// 系統 2014/09/01
			// attatch=account
			roleService.roleConnect(roleId, account);// 角色 2012/06/14
			// attatch=chat
			chatService.roleConnect(roleId, chat);// 聊天 2012/07/14
			// attatch=account
			sasangService.roleConnect(roleId, account);// 四象 2013/03/06
			// attatch=account
			manorService.roleConnect(roleId, account);// 莊園 2013/02/19
			// attatch=account
			treasureService.roleConnect(roleId, account);// 祕寶 2013/02/27
			// attatch=account
			trainService.roleConnect(roleId, account);// 訓練 2013/03/01
			// attatch=account
			wuxingService.roleConnect(roleId, account);// 五行 2013/03/18

		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during roleConnect()").toString(), e);
		}
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
		return null;
	}

	/**
	 * 角色斷線,用多緒處理
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 * 
	 * @see CoreRoleDisconnectInterceptor
	 */
	public <T> Role roleDisconnect(String roleId, T attatch) {
		Role result = null;// 角色
		try {
			result = roleRepository.getRole(roleId);
			if (result == null) {
				return result;
			}

			// --------------------------------------------------
			// 注意:模組,請有順序的斷線
			// --------------------------------------------------
			systemService.roleDisconnect(roleId, attatch);// 系統 2014/09/01
			roleService.roleDisconnect(roleId, attatch); // 角色 2012/06/14
			chatService.roleDisconnect(roleId, attatch);// 聊天 2012/07/14
			sasangService.roleDisconnect(roleId, attatch); // 四象 2013/03/06
			manorService.roleDisconnect(roleId, attatch); // 莊園 2013/02/19
			treasureService.roleDisconnect(roleId, attatch); // 祕寶 2013/02/27
			trainService.roleDisconnect(roleId, attatch); // 訓練 2013/03/01
			wuxingService.roleDisconnect(roleId, attatch); // 五行 2013/03/01

			// --------------------------------------------------
			// 移除監聽
			// --------------------------------------------------
			result.removeBeanChangeListener(roleChangeAdapter);// 角色
			result.removeBeanChangeListener(sasangChangeAdapter);// 四象
			result.removeBeanChangeListener(manorChangeAdapter);// 莊園
			result.removeBeanChangeListener(treasureChangeAdapter);// 祕寶
			result.removeBeanChangeListener(trainChangeAdapter);// 訓練
			result.removeBeanChangeListener(wuxingChangeAdapter);// 五行

			// 當斷線時,聊天角色存檔
			Chat chat = chatSetService.getChat(roleId);
			storeChat(chat);

			// 當斷線時,角色存檔
			storeRole(result);

			// 發送斷線
			sendRoleDisconnect(result, attatch);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during roleDisconnect()").toString(), e);
		}
		//
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
	 * 角色無法連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleRefused(String roleId, T attatch) {
		return null;
	}

	/**
	 * 發送角色無法連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleRefused(Role role, T attatch) {
		return null;
	}

	/**
	 * 檢查角色結果
	 */
	public static class CheckRoleResultImpl extends AppResultSupporter implements CheckRoleResult {

		private static final long serialVersionUID = 56269573789421786L;

		private AccountResult accountResult = new AccountResultImpl();

		private RoleResult roleResult = new RoleResultImpl();

		private ErrorType errorType = ErrorType.NO_ERROR;

		public CheckRoleResultImpl() {
		}

		public Account getAccount() {
			return accountResult.getAccount();
		}

		public void setAccount(Account account) {
			accountResult.setAccount(account);
		}

		public Role getRole() {
			return roleResult.getRole();
		}

		public void setRole(Role role) {
			roleResult.setRole(role);
		}

		public ErrorType getErrorType() {
			return errorType;
		}

		public void setErrorType(ErrorType errorType) {
			this.errorType = errorType;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("account", accountResult);
			builder.append("role", roleResult);
			builder.append("errorType", errorType);
			return builder.toString();
		}
	}

	/**
	 * 檢查連線
	 * 
	 * 1.檢查是否有ser, 若有ser則回存到mem
	 * 
	 * 2.若沒ser, 則從DB回存到mem
	 * 
	 * @param roleId
	 * @return
	 */
	public CheckRoleResult checkRole(String roleId) {
		CheckRoleResult result = new CheckRoleResultImpl();
		//
		Role role = null;// 角色
		Account account = null;// 帳號
		// 從DB讀取角色資料,若無資料,則判斷是否為 TEST_ROLE 開頭
		// 若是TEST_ROLE_*則新增一筆角色,供測試用,如:roleId=TEST_ROLE_mary

		// 測試角色
		final String TEST_ROLE = "TEST_ROLE";
		// 測試帳號
		final String TEST_ACCOUNT = "TEST_ACCOUNT";

		// 1.檢查是否有ser, 若有ser則回存到mem, 2014/10/08
		String serName = storeRoleService.serName(roleId);
		boolean exist = FileHelper.isExist(serName);
		if (exist) {
			role = storeRoleService.deserializeRole(roleId);
			// 在storeRoleService.storeRole中才刪除ser
			LOGGER.info("[" + roleId + "] Restored from ser file [" + serName + "]");
		} else {
			// 2.若沒ser, 則從DB回存到mem
			role = roleService.findRole(roleId);
			if (role != null) {
				LOGGER.info("Role [" + roleId + "] restored from DB");
			}
		}

		// 當無角色時, 依據是否為TEST_ROLE開頭的id, 建立role
		if (role == null) {
			// 測試角色
			if (roleId.startsWith(TEST_ROLE, 0)) {
				// 新增測試角色,西戎戰士,1轉
				// industryId=HUMAN_WARRIOR_1
				String name = roleId.substring(TEST_ROLE.length());// _mary

				role = roleService.createRole(roleId, safeRoleName(name), RaceType.RONG, CareerType.WARRIOR_1,
						GenderType.FEMALE, HairType.SHORT, FaceType.CUTE);

				String accountId = TEST_ACCOUNT + name;
				account = accountService.findAccount(accountId);

				// 無角色,無帳號
				if (account == null) {
					account = accountService.createAccount(accountId, safeAccountName(name));
					// 存到DB
					accountService.insert(account);
				} else {
					// 無角色,有帳號 ok
				}
				//
				role.setAccountId(account.getId());
				role.setAcceptorId(getAcceptorId());
				// 存到DB
				roleService.insert(role);

				// 檢查成功
				result.setAccount(account);
				result.setRole(role);
				result.setErrorType(ErrorType.NO_ERROR);
			}
			// 非測試角色
			else {
				result.setAccount(account);
				result.setRole(role);
				result.setErrorType(ErrorType.ROLE_NOT_EXIST);
				// TODO 把client斷線
				LOGGER.warn("[" + roleId + "] is not exist");
				return result;
			}
		}
		// 有角色時,檢查帳號是否存在
		else {
			// 測試角色
			if (roleId.startsWith(TEST_ROLE, 0)) {
				String name = roleId.substring(TEST_ROLE.length());// _mary
				String accountId = TEST_ACCOUNT + name;
				account = accountService.findAccount(accountId);
				// 有角色,無帳號
				if (account == null) {
					account = accountService.createAccount(accountId, safeAccountName(name));
					accountService.insert(account);
					role.setAccountId(account.getId());
				} else {
					// 有角色,有帳號 ok
				}

				// 檢查成功
				result.setAccount(account);
				result.setRole(role);
				result.setErrorType(ErrorType.NO_ERROR);
			}
			// 正常角色
			else {
				account = accountService.findAccount(role.getAccountId());
				// 有角色,無帳號
				if (account == null) {
					result.setAccount(account);
					result.setRole(role);
					result.setErrorType(ErrorType.ACCOUNT_NOT_EXIST);
					// TODO kick client
					// log.warn(roleId + ", " + role.getName() +
					// " not exist valid account");
					return result;
				} else {
					// 有角色,有帳號 ok
				}

				// 檢查成功
				result.setAccount(account);
				result.setRole(role);
				result.setErrorType(ErrorType.NO_ERROR);
			}
		}
		return result;
	}

	protected String safeRoleName(String name) {
		return (StringHelper.isBlank(name) ? "測試角色" : name);
	}

	protected String safeAccountName(String name) {
		return (StringHelper.isBlank(name) ? "測試帳號" : name);
	}

	/**
	 * 檢查聊天
	 * 
	 * 1.檢查是否有ser, 若有ser則回存到mem
	 * 
	 * 2.若沒ser, 則從DB回存到mem
	 * 
	 * @param roleId
	 * @return
	 */
	public Chat checkChat(String roleId) {
		Chat chat = null;
		// 1.檢查是否有ser, 若有ser則回存到mem, 2014/10/08
		String serName = storeChatService.serName(roleId);
		boolean exist = FileHelper.isExist(serName);
		if (exist) {
			chat = storeChatService.deserializeChat(roleId);
			// 在storeChatService.storeChat中才刪除ser
			LOGGER.info("[" + roleId + "] Restored from ser file [" + serName + "]");
		} else {
			// 2.若沒ser, 則從DB回存到mem
			chat = chatService.findChat(roleId);
			if (chat != null) {
				LOGGER.info("Chat [" + chat.getId() + "] restored from DB");
			}
		}
		//
		if (chat == null) {
			chat = chatService.createChat(roleId);
			chatService.insert(chat);
		}
		return chat;
	}

	/**
	 * 當斷線時,聊天角色存檔
	 * 
	 * @param roleId
	 */
	protected void storeChat(Chat chat) {
		try {
			storeChatService.storeChat(false, chat);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during storeChat()").toString(), e);
		} finally {
			// 從mem移除
			chatSetService.removeChat(chat);
		}
	}

	/**
	 * 當斷線時,角色存檔
	 * 
	 * @param role
	 */
	protected void storeRole(Role role) {
		try {
			storeRoleService.storeRole(false, role);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during storeChat()").toString(), e);
		} finally {
			// 從mem移除
			roleRepository.removeRole(role);
		}
	}

	// --------------------------------------------------
	/**
	 * 檢查檢查本文結果
	 */
	public class CheckContextResultImpl extends AppResultSupporter implements CheckContextResult {

		private static final long serialVersionUID = -7440553804477362234L;

		private Context context;

		private ErrorType errorType = ErrorType.NO_ERROR;

		public CheckContextResultImpl() {
		}

		public Context getContext() {
			return context;
		}

		public void setContext(Context context) {
			this.context = context;
		}

		public ErrorType getErrorType() {
			return errorType;
		}

		public void setErrorType(ErrorType errorType) {
			this.errorType = errorType;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("context", context);
			builder.append("errorType", errorType);
			return builder.toString();
		}
	}

	public CheckContextResult checkContext(String contextId) {
		CheckContextResult result = new CheckContextResultImpl();
		//
		if (contextId == null) {
			result.setErrorType(ErrorType.CONTEXT_NOT_EXIST);
			return result;
		}
		// 建構本文
		Context context = systemService.createContext(contextId, null);
		result.setContext(context);
		result.setErrorType(ErrorType.NO_ERROR);
		return result;
	}

	public <T> Context contextConnect(String contextId, T attatch) {
		Context result = null;
		//
		try {
			// --------------------------------------------------
			// 檢查本文
			// --------------------------------------------------
			CheckContextResult checkContextResult = checkContext(contextId);
			//
			if (!ErrorType.NO_ERROR.equals(checkContextResult.getErrorType())) {
				return result;
			}

			// 系統服務,設定本文
			systemService.setContext(result);
			// 系統服務, 本文連線
			result = systemService.contextConnect(contextId, attatch);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during contextConnect()").toString(), e);
		}
		return result;
	}

	public <T> Context contextDisconnect(String contextId, T attatch) {
		Context result = null;
		try {
			// 系統服務, 本文斷線
			result = systemService.contextDisconnect(contextId, attatch);
			// 系統服務,設定本文
			systemService.setContext(null);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during contextDisconnect()").toString(), e);
		}
		//
		return result;
	}

	// --------------------------------------------------
	/**
	 * 檢查伺服器關連結果
	 */
	public class CheckRelationResultImpl extends AppResultSupporter implements CheckRelationResult {

		private static final long serialVersionUID = 56269573789421786L;

		private Relation relation;

		private ErrorType errorType = ErrorType.NO_ERROR;

		public CheckRelationResultImpl() {
		}

		public Relation getRelation() {
			return relation;
		}

		public void setRelation(Relation relation) {
			this.relation = relation;
		}

		public ErrorType getErrorType() {
			return errorType;
		}

		public void setErrorType(ErrorType errorType) {
			this.errorType = errorType;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("relation", relation);
			builder.append("errorType", errorType);
			return builder.toString();
		}
	}

	/**
	 * 檢查伺服器關連
	 * 
	 * @param relationId
	 * @return
	 */
	public CheckRelationResult checkRelation(String relationId) {
		CheckRelationResult result = new CheckRelationResultImpl();
		//
		if (relationId == null) {
			result.setErrorType(ErrorType.RELATION_NOT_EXIST);
			return result;
		}
		// 建構伺服器關連
		Relation relation = systemService.createRelation(relationId, null);
		result.setRelation(relation);
		result.setErrorType(ErrorType.NO_ERROR);
		//
		return result;
	}

	/**
	 * 伺服器關連連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationConnect(String relationId, T attatch) {
		Relation result = null;
		try {
			// --------------------------------------------------
			// 檢查伺服器關連
			// --------------------------------------------------
			CheckRelationResult checkRelationResult = checkRelation(relationId);
			result = checkRelationResult.getRelation();
			//
			if (!ErrorType.NO_ERROR.equals(checkRelationResult.getErrorType())) {
				return result;
			}
			// 伺服器關連集合服務
			relationRepository.addRelation(result);
			// 系統服務, 伺服器關連連線
			result = systemService.relationConnect(relationId, attatch);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during relationConnect()").toString(), e);
		}
		return result;
	}

	/**
	 * 伺服器關連斷線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationDisconnect(String relationId, T attatch) {
		Relation result = null;
		try {
			result = relationRepository.getRelation(relationId);
			if (result == null) {
				return null;
			}
			// 系統服務, 伺服器關連斷線
			result = systemService.relationDisconnect(relationId, attatch);
			// 伺服器關連集合服務
			relationRepository.removeRelation(result);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during relationDisconnect()").toString(), e);
		}
		//
		return result;
	}

	/**
	 * 伺服器關連無法連線
	 * 
	 * @param relationId
	 * @param attatch
	 * @return
	 */
	public <T> Relation relationRefused(String relationId, T attatch) {
		Relation result = null;
		try {
			// 系統服務, 伺服器關連無法連線
			result = systemService.relationRefused(relationId, attatch);
		} catch (Exception e) {
			LOGGER.error(new StringBuilder("Exception encountered during relationRefused()").toString(), e);
		}
		//
		return result;
	}

}
