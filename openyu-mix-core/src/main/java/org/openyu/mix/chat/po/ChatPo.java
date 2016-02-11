package org.openyu.mix.chat.po;

import java.util.Map;

import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.commons.entity.SeqIdAuditEntity;

/**
 * 聊天角色
 */
public interface ChatPo extends SeqIdAuditEntity
{

	String KEY = ChatPo.class.getName();

	/**
	 * 是否開啟好友
	 * 
	 * @return
	 */
	Boolean isOpenFriend();

	void setOpenFriend(Boolean openFriend);

	/**
	 * 是否開啟聯繫
	 * 
	 * @return
	 */
	Boolean isOpenContact();

	void setOpenContact(Boolean openContact);

	/**
	 * 是否開啟推薦
	 * 
	 * @return
	 */
	Boolean isOpenRecommand();

	void setOpenRecommand(Boolean openRecommand);

	/**
	 * 頻道設定
	 * 
	 * @return
	 */
	Map<ChannelType, Channel> getChannels();

	void setChannels(Map<ChannelType, Channel> channels);

	//---------------------------------------------------
	//朋友列表
	//---------------------------------------------------
	/**
	 * 好友列表
	 * 
	 * @return
	 */
	FriendGroup getFriendGroup();

	void setFriendGroup(FriendGroup friendGroup);

	/**
	 * 被其他玩家加入好友的玩家列表
	 * 
	 * @return
	 */
	FriendGroup getOtherGroup();

	void setOtherGroup(FriendGroup otherGroup);

	/**
	 * 隔絕列表
	 * 
	 * @return
	 */
	FriendGroup getBlockGroup();

	void setBlockGroup(FriendGroup blockGroup);

	/**
	 * 待加入玩家審核列表
	 * 
	 * @return
	 */
	FriendGroup getPendingGroup();

	void setPendingGroup(FriendGroup pendingGroup);

	/**
	 * 最近聯繫好友列表
	 * 
	 * @return
	 */
	FriendGroup getContactGroup();

	void setContactGroup(FriendGroup contactGroup);

	/**
	 * 同地圖中等級相差(含)3級內推薦列表
	 * 
	 * @return
	 */
	FriendGroup getRecommandGroup();

	void setRecommandGroup(FriendGroup recommandGroup);
}
