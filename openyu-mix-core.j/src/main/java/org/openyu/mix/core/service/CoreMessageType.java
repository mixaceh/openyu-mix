package org.openyu.mix.core.service;

import org.openyu.socklet.message.vo.MessageType;

/**
 * 訊息
 * 
 * 第1位數 => 1=系統用, 2=子功能用
 * 
 * 第2,3位數模組 => 01-99
 * 
 * 第4,5,6位數訊息,=> 001-999, sync=001-100, request=101-200, response=201-300,
 * debug=901-999
 * 
 */
public enum CoreMessageType implements MessageType {

	// ---------------------------------------------------
	// spec server 110-119
	// ---------------------------------------------------

	// ---------------------------------------------------
	// TODO ACCOUNT請求-account server
	// ---------------------------------------------------
	/**
	 * 授權請求
	 * 
	 * ACCOUNT.ACCOUNT_AUTHORIZE_REQUEST
	 * 
	 * -> LOGIN.LOGIN_AUTHORIZE_REQUEST
	 * 
	 * -> ACCOUNT.ACCOUNT_AUTHORIZE_FROM_LOGIN_REQUEST
	 * 
	 * -> CLIENT.ACCOUNT_AUTHORIZE_RESPONSE
	 * 
	 * 0, String, 帳戶id
	 * 
	 * 1, String, 密碼
	 * 
	 */
	ACCOUNT_AUTHORIZE_REQUEST(110111),

	/**
	 * 授權請求來自於login server
	 * 
	 * 0, String, 帳戶id
	 * 
	 * 1, String, 認證碼
	 */
	ACCOUNT_AUTHORIZE_FROM_LOGIN_REQUEST(110112),

	// ---------------------------------------------------
	// ACCOUNT回應
	// ---------------------------------------------------
	/**
	 * 授權回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, String, 帳戶id
	 * 
	 * 2, String, 認證碼
	 * 
	 * 3, String, server ip
	 * 
	 * 4, String, server port
	 */
	ACCOUNT_AUTHORIZE_RESPONSE(110211),

	/**
	 * 帳戶儲值
	 * 
	 * 0, int, 目前的儲值
	 * 
	 * 1, int, 增減的儲值
	 */
	ACCOUNT_COIN_RESPONSE(110212),

	/**
	 * 帳戶累計儲值
	 * 
	 * 0, int, 目前的累計儲值
	 */
	ACCOUNT_ACCU_COIN_RESPONSE(110213),

	// ---------------------------------------------------
	// TODO LOGIN請求-login server
	// ---------------------------------------------------

	/**
	 * 授權請求來自於account server
	 * 
	 * 0, String, 帳戶id
	 * 
	 * 1, String, 認證碼
	 * 
	 * 2, String, client ip
	 */
	LOGIN_AUTHORIZE_FROM_ACCOUNT_REQUEST(111111),

	/**
	 * 角色連線
	 * 
	 * 0, String, 帳號id
	 * 
	 * 1, String, 角色id
	 */
	LOGIN_CONNECT_REQUEST(111112),

	/**
	 * 角色斷線
	 * 
	 * 0, String, 帳號id
	 * 
	 * 1, String, 角色id
	 */
	LOGIN_DISCONNECT_REQUEST(111113),

	// ---------------------------------------------------
	// LOGIN回應
	// ---------------------------------------------------

	// ---------------------------------------------------
	// TODO CORE請求
	// ---------------------------------------------------
	/**
	 * 本文連線(內部用)
	 *
	 * 0, String, contextId
	 */
	CORE_CONTEXT_CONNECT_REQUEST(150111),

	/**
	 * 本文斷線(內部用)
	 * 
	 * 0, String, contextId
	 */
	CORE_CONTEXT_DISCONNECT_REQUEST(150112),

	/**
	 * 伺服器關連連線(內部用)
	 * 
	 * 0, String, relationId
	 */
	CORE_RELATION_CONNECT_REQUEST(150115),

	/**
	 * 伺服器關連斷線(內部用)
	 * 
	 * 0, String, relationId
	 */
	CORE_RELATION_DISCONNECT_REQUEST(150116),

	/**
	 * 伺服器關連無法連線(內部用)
	 * 
	 * 0, String, relationId
	 */
	CORE_RELATION_REFUSED_REQUEST(150117),

	/**
	 * 角色連線(內部用)
	 * 
	 * 0, String, 角色id
	 */
	CORE_ROLE_CONNECT_REQUEST(150121),

	/**
	 * 角色斷線(內部用)
	 * 
	 * 0, String, 角色id
	 */
	CORE_ROLE_DISCONNECT_REQUEST(150122),

	// ---------------------------------------------------
	// CORE回應
	// ---------------------------------------------------

	// ---------------------------------------------------
	// TODO 系統請求
	// ---------------------------------------------------
	/**
	 * 伺服器關連, 取得所有伺服器關連請求
	 * 
	 * 0, String, 角色id
	 */
	SYSTEM_ALL_RELATION_REQUEST(191111),

	// ---------------------------------------------------
	// 伺服器關連回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 * 
	 * 0, String, contextId 本文id
	 * 
	 * 1, int, size
	 * 
	 * {
	 * 
	 * 2, String, relationId 伺服器關連id
	 * 
	 * 3, boolean, 是否連線
	 * 
	 * 4, long, 上線時間
	 * 
	 * 5, long, 離線時間
	 * 
	 * }
	 */
	SYSTEM_INITIALIZE_RESPONSE(191201),

	/**
	 * 本文連線回應
	 * 
	 * 0, String, contextId 本文id
	 * 
	 * 1, long, 上線時間
	 */
	SYSTEM_CONTEXT_CONNECT_RESPONSE(191202),

	/**
	 * 本文斷線回應
	 * 
	 * 0, String, contextId 本文id
	 * 
	 * 1, long, 離線時間
	 */
	SYSTEM_CONTEXT_DISCONNECT_RESPONSE(191203),

	/**
	 * 伺服器關連連線回應
	 * 
	 * 0, String, relationId 伺服器關連id
	 * 
	 * 1, long, 上線時間
	 */
	SYSTEM_RELATION_CONNECT_RESPONSE(191204),

	/**
	 * 伺服器關連斷線回應
	 * 
	 * 0, String, relationId 伺服器關連id
	 * 
	 * 1, long, 離線時間
	 */
	SYSTEM_RELATION_DISCONNECT_RESPONSE(191205),

	/**
	 * 伺服器關連無法連線回應
	 * 
	 * 0, String, relationId 伺服器關連id
	 */
	SYSTEM_RELATION_REFUSED_RESPONSE(191206),

	/**
	 * 伺服器關連, 取得所有伺服器關連回應
	 * 
	 * 0, int, size
	 * 
	 * {
	 * 
	 * 1, String, relationId 伺服器關連id
	 * 
	 * 2, boolean, 是否連線
	 * 
	 * 3, long, 上線時間
	 * 
	 * 4, long, 離線時間
	 * 
	 * }
	 */
	SYSTEM_ALL_RELATION_RESPONSE(191213),

	// ---------------------------------------------------
	// TODO ROLE請求
	// ---------------------------------------------------
	/**
	 * 同步角色連線
	 * 
	 * 0, role, 角色
	 */
	ROLE_SYNC_ROLE_CONNECT_REQUEST(151001),

	/**
	 * 同步角色斷線
	 * 
	 * 0, String, 角色id
	 */
	ROLE_SYNC_ROLE_DISCONNECT_REQUEST(151002),

	/**
	 * 同步角色欄位
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, fieldName
	 * 
	 * 2, Object, value
	 */
	ROLE_SYNC_ROLE_FIELD_REQUEST(151003),

	/**
	 * 增減經驗
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的經驗
	 */
	ROLE_CHANGE_EXP_REQUEST(151111),

	/**
	 * 增減技魂(sp)
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的技魂
	 */
	ROLE_CHANGE_SP_REQUEST(151112),

	/**
	 * 增減等級
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的等級
	 */
	ROLE_CHANGE_LEVEL_REQUEST(151113),

	/**
	 * 增減金幣
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的金幣
	 * 
	 * 2, int, GoldType=金幣增減的原因
	 */
	ROLE_CHANGE_GOLD_REQUEST(151114),

	/**
	 * 增減聲望
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的聲望
	 */
	ROLE_CHANGE_FAME_REQUEST(151115),

	/**
	 * 屬性請求
	 * 
	 * 0, String, 角色id
	 */
	ROLE_CHANGE_ATTRIBUTE_REQUEST(151116),

	// ---------------------------------------------------
	// 角色祕技
	// ---------------------------------------------------
	/**
	 * 祕技儲存角色
	 * 
	 * 0, String, 角色id
	 */
	ROLE_DEBUG_STORE_ROLE_REQUEST(151901),

	/**
	 * 祕技增減經驗
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的經驗
	 */
	ROLE_DEBUG_CHANGE_EXP_REQUEST(151902),

	/**
	 * 祕技增減技魂(sp)
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的技魂
	 */
	ROLE_DEBUG_CHANGE_SP_REQUEST(151903),

	/**
	 * 祕技增減等級
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的等級
	 */
	ROLE_DEBUG_CHANGE_LEVEL_REQUEST(151904),

	/**
	 * 祕技增減金幣
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, long, 增減的金幣
	 */
	ROLE_DEBUG_CHANGE_GOLD_REQUEST(151905),

	/**
	 * 祕技增減聲望
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的聲望
	 */
	ROLE_DEBUG_CHANGE_FAME_REQUEST(151906),

	/**
	 * 祕技增減屬性
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 屬性類型
	 * 
	 * 2, int, 增減的point
	 * 
	 * 3, int, 增減的addPoint
	 * 
	 * 4, int, 增減的addRate
	 */
	ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST(151907),

	/**
	 * 祕技增減儲值幣,不放在ACCOUNT,因slave無ACCOUNT可處理
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的儲值幣
	 */
	ROLE_DEBUG_CHANGE_COIN_REQUEST(151908),

	/**
	 * 祕技增減vip等級
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 增減的vip等級
	 */
	ROLE_DEBUG_CHANGE_VIP_REQUEST(151909),

	// ---------------------------------------------------
	// 角色回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 * 
	 * 0, String id
	 * 
	 * 1, String 名稱
	 * 
	 * 2, long 經驗
	 * 
	 * 3, int 等級
	 * 
	 * 4, long 金幣
	 * 
	 * 5, int 戰力
	 * 
	 * 6, int 儲值總額
	 * 
	 * 7, int 累計儲值總額
	 * 
	 * ...
	 */
	ROLE_INITIALIZE_RESPONSE(151201),

	/**
	 * 經驗回應
	 * 
	 * 0, long, 目前等級的經驗
	 * 
	 * 1, long, 增減的經驗
	 */
	ROLE_EXP_RESPONSE(151213),

	/**
	 * 技魂(sp)回應
	 * 
	 * 0, long, 目前技魂(sp)
	 * 
	 * 1, long, 增減的技魂(sp)
	 */
	ROLE_SP_RESPONSE(151214),

	/**
	 * 等級回應
	 * 
	 * 0, int, 目前的等級
	 */
	ROLE_LEVEL_RESPONSE(151215),

	/**
	 * 金幣回應
	 * 
	 * 0, long, 目前的金幣
	 * 
	 * 1, long, 增減的金幣
	 */
	ROLE_GOLD_RESPONSE(151216),

	/**
	 * 聲望回應
	 * 
	 * 0, int, 目前的聲望
	 * 
	 * 1, int, 增減的聲望
	 */
	ROLE_FAME_RESPONSE(151217),

	/**
	 * 單一屬性回應
	 * 
	 * 0, int, 屬性類型
	 * 
	 * 1, int, 屬性值
	 * 
	 * 2, int, 最終屬性值
	 */
	ROLE_ATTRIBUTE_RESPONSE(151218),

	/**
	 * 屬性群回應
	 * 
	 * 0, int, 屬性size
	 * 
	 * {
	 * 
	 * 1, int, 屬性類型
	 * 
	 * 2, int, 屬性值
	 * 
	 * 3, int, 最終屬性值
	 * 
	 * }
	 */
	ROLE_ATTRIBUTE_GROUP_RESPONSE(151219),

	/**
	 * 增減vip等級回應
	 * 
	 * 0, int, 目前的vip等級
	 * 
	 * 1, int, 增減的vip等級
	 */
	ROLE_VIP_RESPONSE(151220),

	/**
	 * 儲存角色
	 * 
	 * 0, int, errorType 錯誤碼
	 * 
	 * 1, int, 儲存類型, StoreType
	 * 
	 * 2, int, 重試次數
	 */
	ROLE_STORE_ROLE_RESPONSE(151221),

	// ---------------------------------------------------
	// 成名回應
	// ---------------------------------------------------
	/**
	 * 成名等級回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 目前的等級
	 */
	ROLE_FAMOUS_LEVEL_RESPONSE(151501),

	// ---------------------------------------------------
	// TODO 聊天請求
	// ---------------------------------------------------
	/**
	 * 聊天請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 頻道類型
	 * 
	 * 2, String, 聊天內容
	 * 
	 * 3, String, html
	 */
	CHAT_SPEAK_REQUEST(152111),

	// ---------------------------------------------------
	// 聊天祕技請求
	// ---------------------------------------------------
	/**
	 * 祕技儲存聊天角色
	 * 
	 * 0, String, 聊天角色id,chatId
	 */
	CHAT_DEBUG_STORE_REQUEST(152901),

	// ---------------------------------------------------
	// 聊天回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	CHAT_INITIALIZE_RESPONSE(152201),

	/**
	 * 聊天回應
	 * 
	 * 0, int, errorType 錯誤碼
	 * 
	 * 1, int, 頻道類型, ChannelType
	 * 
	 * 2, String, 發送者id
	 * 
	 * 3, String, 發送者名稱
	 * 
	 * 4, String, 聊天內容
	 * 
	 * 5, String, html
	 * 
	 */
	CHAT_SPEAK_RESPONSE(152213),

	/**
	 * 儲存聊天角色
	 * 
	 * 0, int, errorType 錯誤碼
	 * 
	 * 1, int, 儲存類型, StoreType
	 * 
	 * 2, int, 重試次數
	 */
	CHAT_STORE_CHAT_RESPONSE(152221),

	// ---------------------------------------------------
	// TODO 郵件請求
	// ---------------------------------------------------
	/**
	 * 郵件請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 郵件mailId
	 */
	MAIL_MAIL_REQUEST(153111),

	/**
	 * 新郵件請求
	 * 
	 * 0, String, 角色id
	 */
	MAIL_NEW_REQUEST(153112),

	// ---------------------------------------------------
	// 郵件回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	MAIL_INITIALIZE_RESPONSE(153201),

	/**
	 * 郵件回應
	 * 
	 * 0, String, 郵件id
	 * 
	 */
	MAIL_MAIL_RESPONSE(153212),

	/**
	 * 新郵件回應
	 * 
	 * 0, int, errorType 錯誤碼
	 */
	MAIL_NEW_RESPONSE(153213),

	// ---------------------------------------------------
	// TODO 活動請求
	// ---------------------------------------------------
	/**
	 * 刷新請求
	 * 
	 * 0, String, 角色id
	 */
	ACTIVITY_XXX_REQUEST(157111),

	// ---------------------------------------------------
	// 活動回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	ACTIVITY_INITIALIZE_RESPONSE(157201),

	// ---------------------------------------------------
	// 成名回應
	// ---------------------------------------------------

	// ---------------------------------------------------
	// 活動祕技
	// ---------------------------------------------------

	// ---------------------------------------------------
	// TODO 道具請求
	// ---------------------------------------------------
	/**
	 * 道具請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 道具uniqueId
	 */
	ITEM_GET_ITEM_REQUEST(159111),

	/**
	 * 減少道具請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 道具uniqueId
	 * 
	 * 2, int 數量
	 */
	ITEM_DECREASE_ITEM_REQUEST(159112),

	/**
	 * 使用道具請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 對象id(角色id,裝備uniqueId)
	 * 
	 * 2, String, 道具uniqueId
	 * 
	 * 3, int, 使用數量
	 */
	ITEM_USE_ITEM_REQUEST(159113),

	// ---------------------------------------------------
	// 道具祕技請求
	// ---------------------------------------------------
	/**
	 * 祕技增減道具
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 道具id
	 * 
	 * 2, int 數量
	 * 
	 */
	ITEM_DEBUG_CHANGE_ITEM_REQUEST(159901),

	/**
	 * 祕技包包清除
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 包包頁id,tabIndex
	 */
	ITEM_DEBUG_CLEAR_BAG_REQUEST(159911),

	// ---------------------------------------------------
	// 道具回應
	// ---------------------------------------------------
	/**
	 * 道具回應
	 * 
	 * 0, String, 道具id
	 * 
	 * 1, String, 道具uniqueId
	 * 
	 * 2,int ,道具數量
	 * 
	 * 3.道具其它欄位
	 * 
	 */
	ITEM_GET_ITEM_RESPONSE(159202),

	/**
	 * 單一包包頁是否鎖定回應
	 * 
	 * 0, int, 包包頁索引,tabIndex
	 * 
	 * 1, boolean, 是否鎖定
	 * 
	 */
	ITEM_TAB_LOCKED_RESPONSE(159203),

	/**
	 * 所有包包頁是否鎖定回應
	 * 
	 * 0, int, 包包頁size
	 * 
	 * {
	 * 
	 * 1, int, 包包頁索引,tabIndex
	 * 
	 * 2, boolean, 是否鎖定
	 * 
	 * }
	 */
	ITEM_TABS_LOCKED_RESPONSE(159204),

	/**
	 * 包包頁回應
	 * 
	 * 
	 * 0, int, 包包頁索引,tabIndex
	 * 
	 * 1, int, 已放道具的格子數量
	 * 
	 * {
	 * 
	 * 2, int 格子索引,gridIndex
	 * 
	 * 3, 道具回應, ITEM_ITEM_RESPONSE
	 * 
	 * }
	 */
	ITEM_TAB_RESPONSE(159205),

	/**
	 * 包包欄位回應
	 * 
	 * 0, int, 已解鎖包包頁size
	 * 
	 * {
	 * 
	 * 包包頁回應,ITEM_BAG_TAB_RESPONSE
	 * 
	 * }
	 */
	ITEM_BAG_RESPONSE(159206),

	/**
	 * 增加道具回應
	 * 
	 * 0, int, size
	 * 
	 * {
	 * 
	 * 1, int, tagIndex
	 * 
	 * 2, int, gridIndex
	 * 
	 * 3, String, 道具id
	 * 
	 * 4, String, 道具uniqueId
	 * 
	 * 5,int, 道具數量
	 * 
	 * 6,boolean, 是否束缚
	 * 
	 * }
	 * 
	 */
	ITEM_INCREASE_RESPONSE(159211),

	/**
	 * 減少道具回應
	 * 
	 * 0, int, size
	 * 
	 * { 0, String, 道具uniqueId
	 * 
	 * 1, int, 道具數量 }
	 */
	ITEM_DECREASE_RESPONSE(159212),

	/**
	 * 使用道具回應
	 * 
	 * 0, int, errorType 錯誤碼
	 */
	ITEM_USE_RESPONSE(159213),

	/**
	 * 強化回應
	 * 
	 * 0, boolean, 是否成功
	 * 
	 * 1, boolean, 是否消失
	 * 
	 * 2, String, id
	 * 
	 * 3, String, uniqueId
	 * 
	 * 4, int, 強化等級
	 * 
	 * 5, int, 屬性size
	 * 
	 * { 6, int, 屬性類別,key
	 * 
	 * 7, int, 屬性值
	 * 
	 * 8, int, 強化所提升的屬性值
	 * 
	 * 9, int, 強化所增加的比率值
	 * 
	 * }
	 * 
	 */
	ITEM_ENHANCE_RESPONSE(159214),

	/**
	 * 清除強化回應
	 * 
	 * 0, String, id
	 * 
	 * 1, String, uniqueId
	 */
	ITEM_UNENHANCE_RESPONSE(159215),

	// ---------------------------------------------------
	// TODO 四象請求
	// ---------------------------------------------------
	/**
	 * 玩的請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 玩的類別,1=青銅,2=銀,3=金,4=黑
	 */
	SASANG_PLAY_REQUEST(210111),

	/**
	 * 中獎區單擊道具放入包包請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 道具id
	 * 
	 * 2, int, 數量
	 */
	SASANG_PUT_ONE_REQUEST(210117),

	/**
	 * 中獎區所有道具放入包包請求
	 * 
	 * 0, String, 角色id
	 */
	SASANG_PUT_ALL_REQUEST(210118),

	// ---------------------------------------------------
	// 四象回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	SASANG_INITIALIZE_RESPONSE(210201),

	/**
	 * 四象欄位回應
	 * 
	 * 0, long, 玩的時間
	 * 
	 * 1, int, 每日已玩的次數
	 * 
	 * 2, String, 四象結果
	 * 
	 * 3, SASANG_AWARDS_RESPONSE
	 */
	SASANG_PEN_RESPONSE(210206),

	/**
	 * 玩的回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int, 玩的類別
	 * 
	 */
	SASANG_PLAY_RESPONSE(210211),

	/**
	 * 通知回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 獎勵size
	 * 
	 * {
	 * 
	 * 2, String, 道具名稱
	 * 
	 * 3, int, 道具數量
	 * 
	 * }
	 */
	SASANG_NOTICE_RESPONSE(210213),

	/**
	 * 多個通知回應
	 * 
	 * 0, int, 通知size
	 * 
	 * {
	 * 
	 * 1. SASANG_NOTICE_RESPONSE
	 * 
	 * }
	 */
	SASANG_NOTICES_RESPONSE(210214),

	/**
	 * 人氣榜回應
	 * 
	 * 0, int, 通知size
	 * 
	 * {
	 * 
	 * 1, SASANG_NOTICE_RESPONSE
	 * 
	 * }
	 */
	SASANG_BOARD_RESPONSE(210215),

	/**
	 * 中獎區回應
	 * 
	 * 0, int, 獎勵size
	 * 
	 * {
	 * 
	 * 1, String, 道具id
	 * 
	 * 2, int, 道具數量
	 * 
	 * }
	 */
	SASANG_AWARDS_RESPONSE(210216),

	/**
	 * 單擊獎勵放入包包回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, String 道具id
	 * 
	 * 2, int 道具數量
	 */
	SASANG_PUT_ONE_RESPONSE(210217),

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int 獎勵size
	 * 
	 * {
	 * 
	 * 2, String 道具id
	 * 
	 * 3, int 道具數量
	 * 
	 * }
	 */
	SASANG_PUT_ALL_RESPONSE(210218),

	/**
	 * 每日次數重置回應
	 */
	SASANG_RESET_RESPONSE(210221),

	// ---------------------------------------------------
	// 四象祕技
	// ---------------------------------------------------
	/**
	 * 祕技四象玩
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 玩的類別,1=青銅,2=銀,3=金
	 */
	SASANG_DEBUG_PLAY_REQUEST(210901),

	/**
	 * 祕技四象中獎區所有道具放入包包
	 * 
	 * 0, String, 角色id
	 */
	SASANG_DEBUG_PUT_ALL_REQUEST(210902),

	/**
	 * 祕技四象重置
	 * 
	 * 0, String, 角色id
	 */
	SASANG_DEBUG_RESET_REQUEST(210904),

	// ---------------------------------------------------
	// 成名回應
	// ---------------------------------------------------
	/**
	 * 成名玩回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 獎勵等級
	 */
	SASANG_FAMOUS_PLAY_RESPONSE(210501),

	/**
	 * 成名玩回應
	 * 
	 * 0, int, 成名size
	 * 
	 * {
	 * 
	 * SASANG_PLAY_RESPONSE
	 * 
	 * }
	 */
	SASANG_FAMOUS_PLAYS_RESPONSE(210502),

	// ---------------------------------------------------
	// TODO 卜卦請求
	// ---------------------------------------------------
	DIVINE_PLAY_REQUEST(211111), // 玩的請求

	// ---------------------------------------------------
	// 卜卦回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	DIVINE_INITIALIZE_RESPONSE(211201),

	// ---------------------------------------------------
	// TODO 莊園請求
	// ---------------------------------------------------
	/**
	 * 開墾請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場id,farmIndex
	 * 
	 * 2, String, 土地uniqueId
	 * 
	 */
	MANOR_RECLAIM_REQUEST(213111),

	/**
	 * 休耕請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場id,farmIndex
	 * 
	 */
	MANOR_DISUSE_REQUEST(213112),

	/**
	 * 耕種請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 耕種類別 @see ManorService.CultureType
	 * 
	 * 2, int, 農場id,farmIndex
	 * 
	 * 3, int, 格子id,gridIndex
	 * 
	 * 4, String, 種子uniqueId
	 */
	MANOR_CULTURE_REQUEST(213113),

	/**
	 * 耕種請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 耕種類別 @see ManorService.CultureType
	 */
	MANOR_CULTURE_ALL_REQUEST(213114),

	// ---------------------------------------------------
	// 莊園回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	MANOR_INITIALIZE_RESPONSE(213201),
	/**
	 * 單一農場頁是否鎖定回應
	 * 
	 * 0, int, 農場頁索引,farmIndex
	 * 
	 * 1, boolean, 是否鎖定
	 * 
	 */
	MANOR_FARM_LOCKED_RESPONSE(213203),

	/**
	 * 所有農場頁是否鎖定回應
	 * 
	 * 0, int, 農場頁size
	 * 
	 * {
	 * 
	 * 1, int, 農場頁索引,farmIndex
	 * 
	 * 2, boolean, 是否鎖定
	 * 
	 * }
	 */
	MANOR_FARMS_LOCKED_RESPONSE(213204),

	/**
	 * 農場回應
	 * 
	 * 0, int, 農場頁索引,farmIndex
	 * 
	 * 1, boolean, 是否有土地,true=土地回應
	 * 
	 * {
	 * 
	 * 2, 土地回應, ITEM_ITEM_RESPONSE
	 * 
	 * }
	 * 
	 * 3, int, 已放種子的格子數量
	 * 
	 * {
	 * 
	 * 4, int 格子索引,gridIndex
	 * 
	 * 5, 道具回應, ITEM_ITEM_RESPONSE
	 * 
	 * 6, long, 剩餘毫秒
	 * 
	 * }
	 */
	MANOR_FARM_RESPONSE(213205),

	/**
	 * 莊園欄位回應
	 * 
	 * 0, int, 已解鎖農場頁size
	 * 
	 * {
	 * 
	 * 1, 農場回應, MANOR_FARM_RESPONSE
	 * 
	 * }
	 */
	MANOR_PEN_RESPONSE(213206),

	/**
	 * 開墾回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int, 農場id,farmIndex
	 * 
	 * 2, String, 土地Id
	 * 
	 * 3, String, 土地uniqueId
	 */
	MANOR_RECLAIM_RESPONSE(213211),

	/**
	 * 休耕回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int, 農場id,farmIndex
	 */
	MANOR_DISUSE_RESPONSE(213212),

	/**
	 * 耕種回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int 耕種類別
	 * 
	 * 2, int, 農場id,farmIndex
	 * 
	 * 3, int, 格子id,gridIndex
	 * 
	 * 4, String, 種子Id
	 * 
	 * 5, String, 種子uniqueId
	 * 
	 * 6, long, 種植時間
	 * 
	 * 7, long, 澆水時間
	 * 
	 * 8, long, 成熟時間
	 * 
	 * 9, long, 成熟類別
	 * 
	 * 10, long, 剩餘毫秒
	 */
	MANOR_CULTURE_RESPONSE(213213),

	/**
	 * 耕種回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, size 耕種結果size
	 * 
	 * 2, MANOR_CULTURE_RESPONSE
	 * 
	 */
	MANOR_CULTURE_ALL_RESPONSE(213214),

	// ---------------------------------------------------
	// 莊園祕技
	// ---------------------------------------------------
	/**
	 * 祕技莊園開墾
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, String, 土地id
	 */
	MANOR_DEBUG_RECLAIM_REQUEST(213901),

	/**
	 * 祕技莊園休耕
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 */
	MANOR_DEBUG_DISUSE_REQUEST(213902),

	/**
	 * 祕技莊園種植
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 種子id
	 */
	MANOR_DEBUG_PLANT_REQUEST(213911),

	/**
	 * 祕技莊園澆水
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_WATER_REQUEST(213912),

	/**
	 * 祕技莊園祈禱
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_PRAY_REQUEST(213913),

	/**
	 * 祕技莊園加速
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_SPEED_REQUEST(213914),

	/**
	 * 祕技莊園收割
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_HARVEST_REQUEST(213915),

	/**
	 * 祕技莊園復活
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_REVIVE_REQUEST(213916),

	/**
	 * 祕技莊園清除
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 */
	MANOR_DEBUG_CLEAR_REQUEST(213917),

	/**
	 * 祕技莊園成熟
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_MATURE_REQUEST(213921),

	/**
	 * 祕技莊園枯萎
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 農場頁id,farmIndex
	 * 
	 * 2, int, 格子id,gridIndex
	 */
	MANOR_DEBUG_WITHER_REQUEST(213922),

	// ---------------------------------------------------
	// TODO 祕寶請求
	// ---------------------------------------------------

	/**
	 * 刷新請求
	 * 
	 * 0, String, 角色id
	 */
	TREASURE_REFRESH_REQUEST(214111),

	/**
	 * 購買請求
	 * 
	 * 0, int, 購買類別 @see TreasureService.BuyType
	 * 
	 * 1, int, 上架祕寶索引,index
	 */
	TREASURE_BUY_REQUEST(214112),

	// ---------------------------------------------------
	// 祕寶回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	TREASURE_INITIALIZE_RESPONSE(214201),

	/**
	 * 上架祕寶回應
	 * 
	 * 0, String, 祕寶庫id
	 * 
	 * 1, String, 祕寶id
	 * 
	 * 2, boolean, 是否已購買
	 */
	TREASURE_TREASURE_RESPONSE(214205),

	/**
	 * 祕寶欄位回應
	 * 
	 * 0, long, 刷新時間
	 * 
	 * 1, long, 剩餘毫秒
	 * 
	 * 2, int, 上架祕寶size
	 * 
	 * {
	 * 
	 * 3, int, 上架祕寶索引,index
	 * 
	 * 4, 上架祕寶回應, TREASURE_TREASURE_RESPONSE
	 * 
	 * }
	 */
	TREASURE_PEN_RESPONSE(214206),

	/**
	 * 刷新回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 */
	TREASURE_REFRESH_RESPONSE(214211),

	/**
	 * 購買回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int, 購買類別
	 * 
	 * 2, int, 上架祕寶索引,index
	 */
	TREASURE_BUY_RESPONSE(214212),

	/**
	 * 通知回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 獎勵size
	 * 
	 * {
	 * 
	 * 2, String, 道具名稱
	 * 
	 * 3, int, 道具數量
	 * 
	 * }
	 */

	TREASURE_NOTICE_RESPONSE(214213),

	/**
	 * 已購買的祕寶,人氣榜
	 * 
	 * 0, int, 已購買的祕寶訊息size
	 * 
	 * {
	 * 
	 * 1, TREASURE_NOTICE_RESPONSE
	 * 
	 * }
	 */
	TREASURE_BOARD_RESPONSE(214214),

	// ---------------------------------------------------
	// 成名回應
	// ---------------------------------------------------
	/**
	 * 成名購買回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, String, 道具名稱
	 */
	TREASURE_FAMOUS_BUY_RESPONSE(214501),

	// ---------------------------------------------------
	// 祕寶祕技
	// ---------------------------------------------------
	/**
	 * 祕技祕寶刷新
	 * 
	 * 0, String, 角色id
	 */
	TREASURE_DEBUG_REFRESH_REQUEST(214901),

	/**
	 * 祕技祕寶購買
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 購買類別 @see TreasureService.BuyType
	 * 
	 * 2, int, 上架祕寶索引,index
	 */
	TREASURE_DEBUG_BUY_REQUEST(214902),

	// ---------------------------------------------------
	// TODO 訓練請求
	// ---------------------------------------------------
	/**
	 * 加入請求
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_JOIN_REQUEST(215111),

	/**
	 * 離開請求
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_QUIT_REQUEST(215112),

	/**
	 * 鼓舞請求
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_INSPIRE_REQUEST(215113),

	// ---------------------------------------------------
	// 訓練回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	TRAIN_INITIALIZE_RESPONSE(215201),

	/**
	 * 訓練欄位回應
	 * 
	 * 0, long, 開始時間
	 * 
	 * 1, long, 離開時間
	 * 
	 * 2, long, 每天已訓練毫秒
	 * 
	 * 3, long, 鼓舞時間
	 * 
	 * 4, long, 剩餘毫秒
	 */
	TRAIN_PEN_RESPONSE(215206),

	/**
	 * 訓練加入回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 */
	TRAIN_JOIN_RESPONSE(215211),

	/**
	 * 訓練離開回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, long, 離開時間
	 */
	TRAIN_QUIT_RESPONSE(215212),

	/**
	 * 訓練鼓舞回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, long, 鼓舞時間
	 */
	TRAIN_INSPIRE_RESPONSE(215213),

	/**
	 * 訓練重置回應
	 * 
	 * 0, TRAIN_PEN_RESPONSE
	 * 
	 */
	TRAIN_RESET_RESPONSE(215221),

	// ---------------------------------------------------
	// 訓練祕技
	// ---------------------------------------------------
	/**
	 * 祕技訓練加入
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_DEBUG_JOIN_REQUEST(215901),

	/**
	 * 祕技訓練離開
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_DEBUG_QUIT_REQUEST(215902),

	/**
	 * 祕技訓練鼓舞
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_DEBUG_INSPIRE_REQUEST(215903),

	/**
	 * 祕技訓練重置
	 * 
	 * 0, String, 角色id
	 */
	TRAIN_DEBUG_RESET_REQUEST(215904),

	// ---------------------------------------------------
	// TODO 五行請求
	// ---------------------------------------------------
	/**
	 * 玩的請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 玩的類別,1=青銅,2=銀,3=金,4=黑
	 */
	WUXING_PLAY_REQUEST(216111),

	/**
	 * 中獎區單擊道具放入包包請求
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, String, 道具id
	 * 
	 * 2, int, 數量
	 */
	WUXING_PUT_ONE_REQUEST(216117),

	/**
	 * 中獎區所有道具放入包包請求
	 * 
	 * 0, String, 角色id
	 */
	WUXING_PUT_ALL_REQUEST(216118),

	// ---------------------------------------------------
	// 五行回應
	// ---------------------------------------------------
	/**
	 * 初始回應
	 */
	WUXING_INITIALIZE_RESPONSE(216201),

	/**
	 * 五行欄位回應
	 * 
	 * 0, long, 玩的時間
	 * 
	 * 1, int, 每日已玩的次數
	 * 
	 * 2, String, 五行結果
	 * 
	 * 3, WUXING_AWARDS_RESPONSE
	 */
	WUXING_PEN_RESPONSE(216206),

	/**
	 * 玩的回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int, 玩的類別
	 * 
	 */
	WUXING_PLAY_RESPONSE(216211),

	/**
	 * 通知回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 獎勵size
	 * 
	 * {
	 * 
	 * 2, String, 道具名稱
	 * 
	 * 3, int, 道具數量
	 * 
	 * }
	 */
	WUXING_NOTICE_RESPONSE(216213),

	/**
	 * 多個通知回應
	 * 
	 * 0, int, 通知size
	 * 
	 * {
	 * 
	 * 1. WUXING_NOTICE_RESPONSE
	 * 
	 * }
	 */
	WUXING_NOTICES_RESPONSE(216214),

	/**
	 * 人氣榜回應
	 * 
	 * 0, int, 通知size
	 * 
	 * {
	 * 
	 * 1, WUXING_NOTICE_RESPONSE
	 * 
	 * }
	 */
	WUXING_BOARD_RESPONSE(216215),

	/**
	 * 中獎區回應
	 * 
	 * 0, int, 獎勵size
	 * 
	 * {
	 * 
	 * 1, String, 道具id
	 * 
	 * 2, int, 道具數量
	 * 
	 * }
	 */
	WUXING_AWARDS_RESPONSE(216216),

	/**
	 * 單擊獎勵放入包包回應
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, String 道具id
	 * 
	 * 2, int 道具數量
	 */
	WUXING_PUT_ONE_RESPONSE(216217),

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * 0, int, ErrorType 錯誤碼
	 * 
	 * 1, int 獎勵size
	 * 
	 * {
	 * 
	 * 2, String 道具id
	 * 
	 * 3, int 道具數量
	 * 
	 * }
	 */
	WUXING_PUT_ALL_RESPONSE(216218),

	/**
	 * 每日次數重置回應
	 */
	WUXING_RESET_RESPONSE(216221),

	// ---------------------------------------------------
	// 五行祕技
	// ---------------------------------------------------
	/**
	 * 祕技五行玩
	 * 
	 * 0, String, 角色id
	 * 
	 * 1, int, 玩的類別,1=青銅,2=銀,3=金
	 */
	WUXING_DEBUG_PLAY_REQUEST(216901),

	/**
	 * 祕技五行中獎區所有道具放入包包
	 * 
	 * 0, String, 角色id
	 */
	WUXING_DEBUG_PUT_ALL_REQUEST(216902),

	/**
	 * 祕技五行重置
	 * 
	 * 0, String, 角色id
	 */
	WUXING_DEBUG_RESET_REQUEST(216904),

	// ---------------------------------------------------
	// 成名回應
	// ---------------------------------------------------
	/**
	 * 成名玩回應
	 * 
	 * 0, String, 角色名稱
	 * 
	 * 1, int, 獎勵等級
	 */
	WUXING_FAMOUS_PLAY_RESPONSE(216501),

	/**
	 * 成名玩回應
	 * 
	 * 0, int, 成名size
	 * 
	 * {
	 * 
	 * WUXING_PLAY_RESPONSE
	 * 
	 * }
	 */
	WUXING_FAMOUS_PLAYS_RESPONSE(216502),
	//
	;

	private final int value;

	private CoreMessageType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
