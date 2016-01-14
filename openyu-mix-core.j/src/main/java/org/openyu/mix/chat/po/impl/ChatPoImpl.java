package org.openyu.mix.chat.po.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Store;
import org.openyu.mix.chat.po.ChatPo;
import org.openyu.mix.chat.po.bridge.ChannelsBridge;
import org.openyu.mix.chat.po.bridge.FriendGroupBridge;
import org.openyu.mix.chat.vo.Channel;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.chat.vo.ChannelType;
import org.openyu.mix.chat.vo.FriendGroup.MaxType;
import org.openyu.mix.chat.vo.impl.FriendGroupImpl;
import org.openyu.commons.entity.supporter.SeqIdAuditEntitySupporter;

//--------------------------------------------------
//hibernate
//--------------------------------------------------
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "chat", indexes = { @Index(name = "idx_chat_id", columnList = "id") })
@SequenceGenerator(name = "sg_chat", sequenceName = "seq_chat", allocationSize = 1)
// when use ehcache, config in ehcache.xml
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "org.openyu.mix.chat.po.impl.ChatPoImpl")
@Proxy(lazy = false)
// --------------------------------------------------
// search
// --------------------------------------------------
// @Indexed
public class ChatPoImpl extends SeqIdAuditEntitySupporter implements ChatPo {

	private static final long serialVersionUID = 9075386264904702013L;

	private Long seq;

	/**
	 * 是否開啟好友
	 */
	private Boolean openFriend;

	/**
	 * 是否開啟聯繫
	 */
	private Boolean openContact;

	/**
	 * 是否開啟推薦
	 */
	private Boolean openRecommand;

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

	public ChatPoImpl() {
	}

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sg_chat")
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	@Column(name = "open_friend")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Boolean isOpenFriend() {
		return openFriend;
	}

	public void setOpenFriend(Boolean openFriend) {
		this.openFriend = openFriend;
	}

	@Column(name = "open_contact")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Boolean isOpenContact() {
		return openContact;
	}

	public void setOpenContact(Boolean openContact) {
		this.openContact = openContact;
	}

	@Column(name = "open_recommand")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	public Boolean isOpenRecommand() {
		return openRecommand;
	}

	public void setOpenRecommand(Boolean openRecommand) {
		this.openRecommand = openRecommand;
	}

	@Column(name = "channels", length = 255)
	@Type(type = "org.openyu.mix.chat.po.userType.ChannelsUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = ChannelsBridge.class)
	public Map<ChannelType, Channel> getChannels() {
		return channels;
	}

	public void setChannels(Map<ChannelType, Channel> channels) {
		this.channels = channels;
	}

	@Column(name = "friend_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(FriendGroup friendGroup) {
		this.friendGroup = friendGroup;
	}

	@Column(name = "other_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getOtherGroup() {
		return otherGroup;
	}

	public void setOtherGroup(FriendGroup otherGroup) {
		this.otherGroup = otherGroup;
	}

	@Column(name = "block_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getBlockGroup() {
		return blockGroup;
	}

	public void setBlockGroup(FriendGroup blockGroup) {
		this.blockGroup = blockGroup;
	}

	@Column(name = "pending_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getPendingGroup() {
		return pendingGroup;
	}

	public void setPendingGroup(FriendGroup pendingGroup) {
		this.pendingGroup = pendingGroup;
	}

	@Column(name = "contact_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(FriendGroup contactGroup) {
		this.contactGroup = contactGroup;
	}

	@Column(name = "recommand_group", length = 1024)
	@Type(type = "org.openyu.mix.chat.po.userType.FriendGroupUserType")
	@Field(store = Store.YES, index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
	@FieldBridge(impl = FriendGroupBridge.class)
	public FriendGroup getRecommandGroup() {
		return recommandGroup;
	}

	public void setRecommandGroup(FriendGroup recommandGroup) {
		this.recommandGroup = recommandGroup;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
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
		ChatPoImpl copy = null;
		copy = (ChatPoImpl) super.clone();
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
