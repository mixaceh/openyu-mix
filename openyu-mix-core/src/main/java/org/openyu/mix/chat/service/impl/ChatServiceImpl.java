package org.openyu.mix.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.chat.dao.ChatDao;
import org.openyu.mix.chat.po.ChatPo;
import org.openyu.mix.chat.service.ChatService;
import org.openyu.mix.chat.service.ChatRepository;
import org.openyu.mix.chat.service.DebugService;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.impl.ChannelImpl;
import org.openyu.mix.chat.vo.impl.ChatImpl;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.ChatCollector;
import org.openyu.mix.chat.vo.Friend;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleRepository;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.ClassHelper;
import org.openyu.commons.util.AssertHelper;
import org.openyu.socklet.message.vo.Message;

/**
 * 聊天服務
 */
public class ChatServiceImpl extends AppServiceSupporter implements ChatService {

	private static final long serialVersionUID = -2552742044657566534L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	@Autowired
	@Qualifier("roleRepository")
	protected transient RoleRepository roleRepository;

	@Autowired
	@Qualifier("chatRepository")
	protected transient ChatRepository chatRepository;

	@Autowired
	@Qualifier("debugService")
	protected transient DebugService debugService;

	/**
	 * 聊天角色數據
	 */
	private static ChatCollector chatCollector = ChatCollector.getInstance();

	public ChatServiceImpl() {
	}

	public ChatDao getChatDao() {
		return (ChatDao) getCommonDao();
	}

	@Autowired
	@Qualifier("chatDao")
	public void setChatDao(ChatDao chatDao) {
		setCommonDao(chatDao);
	}
	
	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The ChatDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------
	/**
	 * 查詢聊天角色
	 * 
	 * @param chatId
	 * @return
	 */
	public Chat findChat(String chatId) {
		ChatPo orig = getChatDao().findChat(chatId);
		return ClassHelper.copyProperties(orig);
	}

	// --------------------------------------------------
	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 *            , => Chat
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleRepository.getRole(roleId);
		if (result == null) {
			return result;
		}
		//
		if (!(attatch instanceof Chat)) {
			return result;
		}
		Chat chat = (Chat) attatch;

		// 重讀各種聊天玩家列表
		retrieveFriendGroups(chat);

		// 發送連線
		sendRoleConnect(result, chat);

		// 已連線
		chat.setConnected(true);
		//
		return result;
	}

	/**
	 * 重讀各種聊天玩家列表
	 * 
	 * @param chat
	 */
	protected void retrieveFriendGroups(Chat chat) {
		// 好友列表
		retrieveFriendGroup(chat.getFriendGroup());
		// 隔絕列表,id,name
		retrieveFriendGroup(chat.getBlockGroup());
		// 審核列表,id,name
		retrieveFriendGroup(chat.getPendingGroup());
		// 聯繫列表
		retrieveFriendGroup(chat.getContactGroup());
		// 推薦列表
		retrieveFriendGroup(chat.getRecommandGroup());
	}

	/**
	 * 重讀玩家列表
	 * 
	 * @param friendGroup
	 */
	protected void retrieveFriendGroup(FriendGroup friendGroup) {
		for (Friend friend : friendGroup.getFriends().values()) {
			Role destRole = roleRepository.getRole(friend.getId());
			// 對方不在線上,就不重讀了
			if (destRole == null) {
				continue;
			}

			// 對方在線上
			friend.setNames(destRole.getNames());
			friend.setGenderType(destRole.getGenderType());
			friend.setIndustryId(destRole.getIndustryId());
			friend.setLevel(destRole.getLevel());
			friend.setPower(destRole.getPower());
			friend.setVipType(destRole.getVipType());
		}
	}

	/**
	 * 發送角色連線
	 * 
	 * @param role
	 * @param attatch
	 * @return
	 */
	public <T> Message sendRoleConnect(Role role, T attatch) {
		if (!(attatch instanceof Chat)) {
			return null;
		}
		Chat chat = (Chat) attatch;

		// 發送初始
		Message message = sendInitialize(role, chat);
		return message;
	}

	/**
	 * 發送初始
	 * 
	 * @param role
	 * @param chat
	 * @return
	 */
	protected Message sendInitialize(Role role, Chat chat) {
		Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.CLIENT,
				CoreMessageType.CHAT_INITIALIZE_RESPONSE, role.getId());

		// 1.聊天設定
		message.addInt(chat.getChannels().size());
		for (Map.Entry<ChannelType, Channel> entry : chat.getChannels().entrySet()) {
			ChannelType key = entry.getKey();
			Channel value = entry.getValue();
			message.addInt(key);
			message.addBoolean(value.isOpened());
		}

		// 2.填充自己聊天角色
		fillChat(message, role, chat, true);

		// 3.填充各種聊天玩家列表
		fillFriendGroups(message, chat);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 填充聊天角色
	 * 
	 * @param message
	 * @param role
	 * @param chat
	 * @param isOnline
	 */
	protected void fillChat(Message message, Role role, Chat chat, boolean isOnline) {
		message.addBoolean(isOnline);// 是否在線
		//
		message.addString(chat.getId());// id=roleId
		message.addString(role.getName());// 名稱
		message.addInt(role.getGenderType());// 性別
		message.addString(role.getIndustryId());// 行業
		message.addInt(role.getLevel());// 等級
		message.addInt(role.getPower());// 戰力
		message.addInt(role.getVipType());// vip等級
		//
		message.addString(role.getMap());// 地圖
	}

	/**
	 * 填充所有朋友列表
	 * 
	 * @param message
	 * @param chat
	 */
	protected void fillFriendGroups(Message message, Chat chat) {
		// 好友列表
		fillFriendGroup(message, chat.getFriendGroup());
		// 隔絕列表,id,name
		fillSimpleFriendGroup(message, chat.getBlockGroup());
		// 審核列表,id,name
		fillSimpleFriendGroup(message, chat.getPendingGroup());
		// 聯繫列表
		fillFriendGroup(message, chat.getContactGroup());
		// 推薦列表
		fillFriendGroup(message, chat.getRecommandGroup());
	}

	/**
	 * 填充單一朋友列表
	 * 
	 * @param message
	 * @param friendGroup
	 */
	protected void fillFriendGroup(Message message, FriendGroup friendGroup) {
		int size = (friendGroup != null ? friendGroup.getFriends().size() : 0);
		message.addInt(size);
		for (Friend friend : friendGroup.getFriends().values()) {
			message.addString(friend.getId());// id
			message.addString(friend.getName());// 名稱
			message.addInt(friend.getLevel());// 等級
			message.addInt(friend.getPower());// 戰力
			message.addInt(friend.getVipType());// vip等級
			message.addInt(friend.getGenderType());// 性別
			message.addString(friend.getIndustryId());// 行業
		}
	}

	/**
	 * 填充簡易單一朋友列表
	 * 
	 * 填充id,name
	 * 
	 * @param message
	 * @param friendGroup
	 */
	protected void fillSimpleFriendGroup(Message message, FriendGroup friendGroup) {
		int size = (friendGroup != null ? friendGroup.getFriends().size() : 0);
		message.addInt(size);
		for (Friend friend : friendGroup.getFriends().values()) {
			message.addString(friend.getId());// id
			message.addString(friend.getName());// 名稱
		}
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

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 建構聊天角色
	 * 
	 * @param roleId
	 * @return
	 */
	public Chat createChat(String roleId) {
		Chat result = new ChatImpl();
		result.setId(roleId);
		result.setOpenFriend(true);

		// 頻道
		buildChannel(result);

		return result;
	}

	/**
	 * 內部建構頻道
	 * 
	 * @param chat
	 */
	protected void buildChannel(Chat chat) {
		for (ChannelType id : ChannelType.values()) {
			Channel channel = new ChannelImpl(id);
			channel.setOpened(true);
			//
			chat.addChannel(channel);
		}
	}

	// --------------------------------------------------
	/**
	 * 聊天
	 * 
	 * @param role
	 * @param channelValue
	 * @param text
	 * @param html
	 * 
	 * @see ChannelType=3-8
	 */
	public void speak(Role role, int channelValue, String text, String html) {
		ChannelType channelType = EnumHelper.valueOf(ChannelType.class, channelValue);
		// 秘技
		debugService.cheat(role.getId(), text);
		//
		switch (channelType) {
		case NEARBY: {
			// nearbyChat(role, text, html);
			break;
		}
		case TEAM: {
			// teamChat(role, text, html);
			break;
		}
		case LEAGUE: {
			// leagueChat(role, text, html);
			break;
		}
		case LOCAL: {
			// 本地聊天,本地圖中
			localSpeak(channelType, role, text, html);
			break;
		}
		case WORLD: {
			// 世界聊天,所有地圖
			worldSpeak(channelType, role, text, html);
			break;
		}
		case TRADE: {
			// 交易聊天,本地圖中
			tradeSpeak(channelType, role, text, html);
			break;
		}
		default: {
			break;
		}
		}
	}

	/**
	 * 本地聊天,本地圖中
	 * 
	 * @param channelType
	 * @param role
	 * @param text
	 * @param html
	 */
	protected void localSpeak(ChannelType channelType, Role role, String text, String html) {
		if (role == null) {
			return;
		}
		// 取本地所有角色id
		List<String> receivers = roleRepository.getRoleIds(false);

		// TODO 過濾詞句

		// TODO 隔絕

		sendSpeak(ErrorType.NO_ERROR, receivers, channelType, role, role.getName(), text, html);
	}

	/**
	 * 世界聊天,所有地圖
	 * 
	 * @param channelType
	 * @param role
	 * @param text
	 * @param html
	 */
	protected void worldSpeak(ChannelType channelType, Role role, String text, String html) {
		if (role == null) {
			return;
		}
		// 取所有角色id
		List<String> receivers = roleRepository.getRoleIds();

		// TODO 過濾詞句

		// TODO 隔絕

		sendSpeak(ErrorType.NO_ERROR, receivers, channelType, role, role.getName(), text, html);
	}

	/**
	 * 交易聊天,本地圖中
	 * 
	 * @param channelType
	 * @param role
	 * @param text
	 * @param html
	 */
	protected void tradeSpeak(ChannelType channelType, Role role, String text, String html) {
		if (role == null) {
			return;
		}
		// 取本地所有角色id
		List<String> receivers = roleRepository.getRoleIds(false);

		// TODO 過濾詞句

		// TODO 隔絕

		sendSpeak(ErrorType.NO_ERROR, receivers, channelType, role, role.getName(), text, html);
	}

	/**
	 * 發送聊天
	 * 
	 * @param errorType
	 * @param receivers
	 * @param channelType
	 * @param role
	 * @param roleName
	 * @param text
	 * @param html
	 */
	public Message sendSpeak(ErrorType errorType, List<String> receivers, ChannelType channelType, Role role,
			String roleName, String text, String html) {
		Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.CLIENT,
				CoreMessageType.CHAT_SPEAK_RESPONSE, receivers);

		message.addInt(errorType);// 錯誤碼

		// 沒錯誤才發以下欄位
		if (ErrorType.NO_ERROR.equals(errorType)) {
			message.addInt(channelType);// 頻道類型
			message.addString(role.getId());// 發送者id
			message.addString(roleName);// 發送者名稱
			message.addString(text);// 聊天內容
			message.addString(html);// html
		}
		//
		messageService.addMessage(message);
		return message;
	}
}