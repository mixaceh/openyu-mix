package org.openyu.mix.chat.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.commons.bean.SeqIdAuditBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 聊天角色,id=roleId
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Chat extends SeqIdAuditBean
{
	String KEY = Chat.class.getName();

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	boolean isConnected();

	void setConnected(boolean connected);

	/**
	 * 是否開啟好友
	 * 
	 * @return
	 */
	boolean isOpenFriend();

	void setOpenFriend(boolean openFriend);

	/**
	 * 是否開啟聯繫
	 * 
	 * @return
	 */
	boolean isOpenContact();

	void setOpenContact(boolean openContact);

	/**
	 * 是否開啟推薦
	 * 
	 * @return
	 */
	boolean isOpenRecommand();

	void setOpenRecommand(boolean openRecommand);

	/**
	 * 聊天設定
	 * 
	 * @return
	 */
	Map<ChannelType, Channel> getChannels();

	void setChannels(Map<ChannelType, Channel> channels);

	/**
	 * 增加頻道設定
	 * 
	 * @param channel
	 */
	Channel addChannel(Channel channel);

	/**
	 * 取得頻道設定
	 * 
	 * @param chatType
	 * @return
	 */
	Channel getChannel(ChannelType chatType);

	/**
	 * 移除頻道設定
	 * 
	 * @param chatType
	 */
	Channel removeChannel(ChannelType chatType);

	/**
	 * 移除頻道設定
	 * 
	 * @param channel
	 * @return
	 */
	Channel removeChannel(Channel channel);

	/**
	 * 頻道設定是否存在
	 * 
	 * @param chatType
	 * @return
	 */
	boolean containChannel(ChannelType chatType);

	/**
	 * 頻道設定是否存在
	 * 
	 * @param channel
	 * @return
	 */
	boolean containChannel(Channel channel);

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
