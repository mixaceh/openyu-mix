package org.openyu.mix.activity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.activity.service.ActivityService;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.activity.vo.ActivityCollector;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleRepository;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.supporter.BaseRunnableSupporter;
import org.openyu.socklet.message.vo.Message;

/**
 * 活動服務
 */
public class ActivityServiceImpl extends AppServiceSupporter implements ActivityService {

	private static final long serialVersionUID = -4584962342046502809L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("roleRepository")
	private transient RoleRepository roleRepository;

	private transient ActivityCollector activityCollector = ActivityCollector.getInstance();

	private transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 監聽毫秒
	 */
	private long LISTEN_MILLS = 10 * 1000L;

	/**
	 * 監聽
	 */
	private transient ActivityListenRunner activityListenRunner;

	public ActivityServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.activityListenRunner = new ActivityListenRunner(threadService);
		this.activityListenRunner.start();
	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		super.doShutdown();
		this.activityListenRunner.shutdown();
	}

	// --------------------------------------------------

	/**
	 * 監聽
	 */
	protected class ActivityListenRunner extends BaseRunnableSupporter {

		public ActivityListenRunner(ThreadService threadService) {
			super(threadService);
		}

		@Override
		protected void doRun() throws Exception {
			while (true) {
				try {
					if (isShutdown()) {
						break;
					}
					listen();
					ThreadHelper.sleep(LISTEN_MILLS);
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 監聽
	 * 
	 * @return
	 */
	protected void listen() {
	}

	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleRepository.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 發送連線
		sendRoleConnect(result, attatch);
		//
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
		// 發送初始化
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
		Message message = messageService.createMessage(CoreModuleType.ACTIVITY, CoreModuleType.CLIENT,
				CoreMessageType.ACTIVITY_INITIALIZE_RESPONSE, role.getId());

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
		Role result = roleRepository.getRole(roleId);
		if (result == null) {
			return result;
		}

		// 發送斷線
		sendRoleDisconnect(result, attatch);
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

}
