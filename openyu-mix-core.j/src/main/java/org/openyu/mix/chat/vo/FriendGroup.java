package org.openyu.mix.chat.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.openyu.mix.role.vo.Role;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 朋友群,因有好友,被加入,隔絕,聯繫,推薦列表使用
 * 
 * 故拉出來獨立集合處理
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface FriendGroup extends BaseBean
{

	/**
	 * 錯誤類別
	 */
	public enum ErrorType implements IntEnum
	{
		/**
		 * 未知
		 */
		UNKNOWN(-1),

		/**
		 * 沒有錯誤
		 */
		NO_ERROR(0),

		/**
		 * 角色不存在
		 */
		ROLE_NOT_EXIST(1),

		/**
		 * 聊天角色不存在
		 */
		CHAT_NOT_EXIST(2),

		/**
		 * 朋友不存在
		 */
		FRIEND_NOT_EXIST(3),

		/**
		 * 對方未開啟加入好友
		 */
		NOT_OPEN_ADD(11),

		/**
		 * 超過最大數量
		 */
		OVER_MAX_SIZE(12),

		/**
		 * 已加為好友
		 */
		ALREADY_HAS_FRIEND(13),

		//
		;

		private final int intValue;

		private ErrorType(int intValue)
		{
			this.intValue = intValue;
		}

		public int getValue()
		{
			return intValue;
		}

		public String toString()
		{
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.append("name", "(" + intValue + ") " + super.toString());
			return builder.toString();
		}
	}

	/**
	 * 最大數量類別
	 */
	public enum MaxType implements IntEnum
	{
		/**
		 * 無限數量
		 */
		UNLIMITED(0, 0),

		/**
		 * 最大好友數量
		 */
		FRIEND(1, 50),

		/**
		 * 最大隔絕的玩家數量
		 */
		BLACK(2, 50),

		/**
		 * 最大待加入的玩家數量
		 */
		PENDING(3, 20),

		/**
		 * 最大聯繫數量,最近與玩家密語過的其他玩家
		 */
		CONTACT(4, 20),

		/**
		 * 最大推薦數量,地圖中與玩家等級差距在3級內的玩家,1<=4<=7
		 */
		RECOMMAND(5, 20),

		/**
		 * 只有一個
		 */
		ONE(10, 1),
		//
		;

		private final int intValue;

		/**
		 * 數量
		 */
		private final int size;

		private MaxType(int intValue, int size)
		{
			this.intValue = intValue;
			this.size = size;
		}

		public int getValue()
		{
			return intValue;
		}

		public int size()
		{
			return size;
		}
	}

	/**
	 * 最大數量
	 * 
	 * @return
	 */
	MaxType getMaxType();

	void setMaxType(MaxType maxType);

	/**
	 * 朋友
	 * 
	 * @return
	 */
	Map<String, Friend> getFriends();

	void setFriends(Map<String, Friend> friends);

	/**
	 * 增加朋友,若已有朋友,是無法再加入進去
	 * 
	 * @param chat
	 * @param role
	 */
	ErrorType addFriend(Chat chat, Role role);

	/**
	 * 增加朋友
	 * 
	 * @param chat
	 * @param role
	 * @param loopAdd 循環增加,超過最大數量時,移除第1個,加到最後一個
	 */
	ErrorType addFriend(Chat chat, Role role, boolean loopAdd);

	/**
	 * 取得朋友
	 * 
	 * @param friendId
	 * @return
	 */
	Friend getFriend(String friendId);

	/**
	 * 移除朋友
	 * 
	 * @param friendId
	 */
	ErrorType removeFriend(String friendId);

	/**
	 * 移除朋友
	 * 
	 * @param friend
	 * @return
	 */
	ErrorType removeFriend(Friend friend);

	/**
	 * 朋友是否存在
	 * 
	 * @param friendId
	 * @return
	 */
	boolean containFriend(String friendId);

	/**
	 * 朋友是否存在
	 * 
	 * @param friend
	 * @return
	 */
	boolean containFriend(Friend friend);

	/**
	 * 設定朋友,取代原先的朋友資訊
	 * 
	 * @param friendId
	 * @param friend
	 * @return
	 */
	ErrorType setFriend(String friendId, Friend friend);

}
