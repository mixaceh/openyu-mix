package org.openyu.mix.chat.vo.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.chat.vo.Chat;
import org.openyu.mix.chat.vo.Friend;
import org.openyu.mix.chat.vo.FriendGroup;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

public class FriendGroupImpl extends BaseBeanSupporter implements FriendGroup
{

	private static final long serialVersionUID = 5452275813310391278L;

	private MaxType maxType;

	private Map<String, Friend> friends = new LinkedHashMap<String, Friend>();

	public FriendGroupImpl(MaxType maxType)
	{
		this.maxType = maxType;
	}

	public FriendGroupImpl()
	{
		//0=無限數量
		this(MaxType.UNLIMITED);
	}

	public MaxType getMaxType()
	{
		return maxType;
	}

	public void setMaxType(MaxType maxType)
	{
		this.maxType = maxType;
	}

	public Map<String, Friend> getFriends()
	{
		return friends;
	}

	public void setFriends(Map<String, Friend> friends)
	{
		this.friends = friends;
	}

	public ErrorType addFriend(Chat chat, Role role)
	{
		return addFriend(chat, role, false);
	}

	public ErrorType addFriend(Chat chat, Role role, boolean loopAdd)
	{
		ErrorType result = ErrorType.NO_ERROR;

		//聊天角色不存在
		if (chat == null)
		{
			return ErrorType.CHAT_NOT_EXIST;
		}

		//角色不存在
		if (role == null)
		{
			return ErrorType.ROLE_NOT_EXIST;
		}

		//對方未開啟加入好友
		if (!chat.isOpenFriend())
		{
			return ErrorType.NOT_OPEN_ADD;
		}

		//已加為好友
		if (containFriend(chat.getId()))
		{
			return ErrorType.ALREADY_HAS_FRIEND;
		}

		//有數量限制,!MaxType.UNLIMITED,超過最大數量
		if (!MaxType.UNLIMITED.equals(maxType) && friends.size() >= maxType.size())
		{
			if (!loopAdd)
			{
				return ErrorType.OVER_MAX_SIZE;
			}
			else
			{
				//若循環增加,超過最大數量時,移除第1個,加到最後一個
				String removeChatId = null;
				for (String key : friends.keySet())
				{
					removeChatId = key;
					break;
				}
				if (removeChatId != null)
				{
					friends.remove(removeChatId);
				}
			}
		}

		//建構朋友
		Friend friend = new FriendImpl();
		friend.setId(role.getId());//roleId
		friend.setNames(role.getNames());
		friend.setGenderType(role.getGenderType());
		friend.setIndustryId(role.getIndustryId());
		friend.setLevel(role.getLevel());
		friend.setPower(role.getPower());
		friend.setVipType(role.getVipType());
		//
		friends.put(friend.getId(), friend);

		return result;
	}

	public Friend getFriend(String friendId)
	{
		Friend result = null;
		if (friendId != null)
		{
			result = friends.get(friendId);
		}
		return result;
	}

	public ErrorType removeFriend(String friendId)
	{
		ErrorType result = ErrorType.NO_ERROR;
		if (!containFriend(friendId))
		{
			return ErrorType.FRIEND_NOT_EXIST;
		}
		friends.remove(friendId);

		return result;
	}

	public ErrorType removeFriend(Friend friend)
	{
		ErrorType result = ErrorType.NO_ERROR;
		if (friend == null)
		{
			return ErrorType.FRIEND_NOT_EXIST;
		}
		result = removeFriend(friend.getId());
		return result;
	}

	public boolean containFriend(String friendId)
	{
		boolean result = false;
		if (friendId != null)
		{
			result = friends.containsKey(friendId);
		}
		return result;
	}

	public boolean containFriend(Friend friend)
	{
		boolean result = false;
		if (friend != null)
		{
			result = containFriend(friend.getId());
		}
		return result;
	}

	public ErrorType setFriend(String friendId, Friend friend)
	{
		ErrorType result = ErrorType.NO_ERROR;
		if (friendId == null)
		{
			return ErrorType.FRIEND_NOT_EXIST;
		}
		//
		friends.put(friendId, friend);
		return result;
	}

	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("maxType", maxType);
		builder.append("friends", friends);
		return builder.toString();
	}

	public Object clone()
	{
		FriendGroupImpl copy = null;
		copy = (FriendGroupImpl) super.clone();
		copy.friends = clone(friends);
		return copy;
	}

}
