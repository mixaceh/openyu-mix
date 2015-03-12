package org.openyu.mix.chat.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.FriendGroup.MaxType;
import org.openyu.commons.bean.supporter.SeqIdAuditBeanSupporter;

/**
 * 聊天角色,id=roleId
 */
@XmlRootElement(name = "chat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatImpl extends SeqIdAuditBeanSupporter implements Chat {

	private static final long serialVersionUID = -8117740679324290052L;

	/**
	 * 是否已連線
	 */
	@XmlTransient
	private boolean connected;

	/**
	 * 是否開啟好友
	 */
	private boolean openFriend;

	/**
	 * 是否開啟聯繫
	 */
	private boolean openContact;

	/**
	 * 是否開啟推薦
	 */
	private boolean openRecommand;

	/**
	 * 頻道設定
	 */
	private Map<ChannelType, Channel> channels = new LinkedHashMap<ChannelType, Channel>();

	/**
	 * 好友列表
	 */
	private FriendGroup friendGroup = new FriendGroupImpl(MaxType.FRIEND);

	/**
	 * 被其他玩家加入好友的玩家列表
	 */
	private FriendGroup otherGroup = new FriendGroupImpl();

	/**
	 * 隔絕列表
	 */
	private FriendGroup blockGroup = new FriendGroupImpl(MaxType.BLACK);

	/**
	 * 待加入玩家審核列表
	 */
	private FriendGroup pendingGroup = new FriendGroupImpl(MaxType.PENDING);

	/**
	 * 最近聯繫好友列表
	 */
	private FriendGroup contactGroup = new FriendGroupImpl(MaxType.CONTACT);

	/**
	 * 同地圖中等級相差(含)3級內推薦列表
	 */
	private FriendGroup recommandGroup = new FriendGroupImpl(MaxType.RECOMMAND);

	public ChatImpl() {
	}

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * 是否開啟好友
	 * 
	 * @return
	 */
	public boolean isOpenFriend() {
		return openFriend;
	}

	public void setOpenFriend(boolean openFriend) {
		this.openFriend = openFriend;
	}

	/**
	 * 是否開啟聯繫
	 * 
	 * @return
	 */
	public boolean isOpenContact() {
		return openContact;
	}

	public void setOpenContact(boolean openContact) {
		this.openContact = openContact;
	}

	/**
	 * 是否開啟推薦
	 * 
	 * @return
	 */
	public boolean isOpenRecommand() {
		return openRecommand;
	}

	public void setOpenRecommand(boolean openRecommand) {
		this.openRecommand = openRecommand;
	}

	// ---------------------------------------------------
	// 頻道設定
	// ---------------------------------------------------
	public Map<ChannelType, Channel> getChannels() {
		return channels;
	}

	public void setChannels(Map<ChannelType, Channel> channels) {
		this.channels = channels;
	}

	public Channel addChannel(Channel channel) {
		Channel result = null;
		if (channel != null) {
			result = channels.put(channel.getId(), channel);
		}
		return result;
	}

	public Channel getChannel(ChannelType chatType) {
		Channel result = null;
		if (chatType != null) {
			result = channels.get(chatType);
		}
		return result;
	}

	public Channel removeChannel(ChannelType chatType) {
		Channel result = null;
		if (chatType != null) {
			result = channels.remove(chatType);
		}
		return result;
	}

	public Channel removeChannel(Channel channel) {
		Channel result = null;
		if (channel != null) {
			result = removeChannel(channel.getId());
		}
		return result;
	}

	public boolean containChannel(ChannelType chatType) {
		boolean result = false;
		if (chatType != null) {
			result = channels.containsKey(chatType);
		}
		return result;
	}

	public boolean containChannel(Channel channel) {
		boolean result = false;
		if (channel != null) {
			result = containChannel(channel.getId());
		}
		return result;
	}

	// ---------------------------------------------------
	// 朋友列表
	// ---------------------------------------------------

	public FriendGroup getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(FriendGroup friendGroup) {
		this.friendGroup = friendGroup;
	}

	public FriendGroup getOtherGroup() {
		return otherGroup;
	}

	public void setOtherGroup(FriendGroup otherGroup) {
		this.otherGroup = otherGroup;
	}

	public FriendGroup getBlockGroup() {
		return blockGroup;
	}

	public void setBlockGroup(FriendGroup blockGroup) {
		this.blockGroup = blockGroup;
	}

	public FriendGroup getPendingGroup() {
		return pendingGroup;
	}

	public void setPendingGroup(FriendGroup pendingGroup) {
		this.pendingGroup = pendingGroup;
	}

	public FriendGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(FriendGroup contactGroup) {
		this.contactGroup = contactGroup;
	}

	public FriendGroup getRecommandGroup() {
		return recommandGroup;
	}

	public void setRecommandGroup(FriendGroup recommandGroup) {
		this.recommandGroup = recommandGroup;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("openFriend", openFriend);
		builder.append("openContact", openContact);
		builder.append("openRecommand", openRecommand);
		builder.append("channels", channels);
		//
		builder.append("friendGroup", friendGroup);
		builder.append("otherGroup", otherGroup);
		builder.append("blockGroup", blockGroup);
		builder.append("pendingGroup", pendingGroup);
		builder.append("contactGroup", contactGroup);
		builder.append("recommandGroup", recommandGroup);
		return builder.toString();
	}

	public Object clone() {
		ChatImpl copy = null;
		copy = (ChatImpl) super.clone();
		copy.channels = clone(channels);
		//
		copy.friendGroup = clone(friendGroup);
		copy.otherGroup = clone(otherGroup);
		copy.blockGroup = clone(blockGroup);
		copy.pendingGroup = clone(pendingGroup);
		copy.contactGroup = clone(contactGroup);
		copy.recommandGroup = clone(recommandGroup);
		return copy;
	}
}
