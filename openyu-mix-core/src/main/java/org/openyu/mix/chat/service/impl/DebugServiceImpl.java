package org.openyu.mix.chat.service.impl;

import org.apache.commons.lang.StringUtils;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.chat.service.DebugService;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.StringHelper;
import org.openyu.socklet.message.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugServiceImpl extends AppServiceSupporter implements DebugService {

	private static final long serialVersionUID = 8901022041095041996L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(DebugServiceImpl.class);

	public DebugServiceImpl() {
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {

	}

	/**
	 * 秘技
	 * 
	 * info //查看角色資訊
	 * 
	 * exp 100 //增減經驗
	 * 
	 * level 1 //增減重置等級,0=重置
	 * 
	 * gold 100 //增減重置金幣,0=重置
	 * 
	 * fame 100 //增減重置聲望,0=重置
	 * 
	 * sr //儲存角色
	 * 
	 * sc //儲存聊天角色
	 * 
	 * attr 1,10,10,10 //增減角色屬性
	 * 
	 * attrc 1 //重置角色屬性 n/g
	 * 
	 * coin 100 //增減重置儲值幣,0=重置
	 * 
	 * vip 1 //增減重置vip等級,0=重置
	 * 
	 * item T_TREASURE_REFRESH_G001,1 //增減重置道具,0=重置
	 * 
	 * bagc //包包清除
	 * 
	 * bagc 0 //包包清除,tabIndex
	 * 
	 * sasangp 1 //四象玩,playValue
	 * 
	 * sasanga //四象中獎區所有道具放入包包
	 * 
	 * sasangr //四象重置
	 * 
	 * manorr 0,L_TROPICS_G001 //莊園開墾,farmIndex,土地id
	 * 
	 * manord 0 //莊園休耕,farmIndex
	 * 
	 * manorp S_COTTON_G001 //莊園種植,種子id
	 * 
	 * manorw //莊園澆水
	 * 
	 * manorw 0,0 //莊園澆水,farmIndex,gridIndex
	 * 
	 * manory //莊園祈禱
	 * 
	 * manory 0,0 //莊園祈禱,farmIndex,gridIndex
	 * 
	 * manors //莊園加速
	 * 
	 * manors 0,0 //莊園加速,farmIndex,gridIndex
	 * 
	 * manorh //莊園收割
	 * 
	 * manorh 0,0 //莊園收割,farmIndex,gridIndex
	 * 
	 * manorv //莊園復活
	 * 
	 * manorv 0,0 //莊園復活,farmIndex,gridIndex
	 * 
	 * manorc //莊園清除
	 * 
	 * manorc 0,0 //莊園清除,farmIndex,gridIndex
	 * 
	 * manorm //莊園成熟
	 * 
	 * manorm 0,0 //莊園成熟,farmIndex,gridIndex
	 * 
	 * manori //莊園枯萎
	 * 
	 * manori 0,0 //莊園枯萎,farmIndex,gridIndex
	 * 
	 * treasurer //祕寶刷新
	 * 
	 * treasureb //祕寶購買
	 * 
	 * treasureb 1,0 //祕寶購買,buyValue,index
	 * 
	 * trainj //訓練加入
	 * 
	 * trainq //訓練離開
	 * 
	 * traini //訓練鼓舞
	 * 
	 * trainr //訓練重置
	 * 
	 * wuxingp 1 //五行玩,playValue
	 * 
	 * wuxinga //五行中獎區所有道具放入包包
	 * 
	 * wuxingr //五行重置
	 * 
	 * @param roleId
	 * @param text
	 *            命令 值
	 * 
	 * @see DebugCheatInterceptor
	 */
	public void cheat(String roleId, String text) {
		if (StringHelper.isBlank(text)) {
			return;
		}
		//
		int pos = 0;
		StringBuilder buff = new StringBuilder(text);

		// ---------------------------------------------------
		// TODO 角色
		// ---------------------------------------------------
		// 查看角色資訊
		// info
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("info ")) {
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_INFO_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減經驗
		// exp 100
		// 01234
		pos = 4;
		if (text.length() > pos && buff.substring(0, pos).equals("exp ")) {
			long exp = NumberHelper.toLong(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_EXP_REQUEST);
			message.addString(roleId);// 角色id
			message.addLong(exp);// 增減的經驗
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減重置等級
		// level 100
		// 0123456
		pos = 6;
		if (text.length() > pos && buff.substring(0, pos).equals("level ")) {
			int level = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_LEVEL_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(level);// 增減的等級
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減重置金幣
		// gold 100
		// 012345
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("gold ")) {
			long gold = NumberHelper.toLong(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_GOLD_REQUEST);
			message.addString(roleId);// 角色id
			message.addLong(gold);// 增減的金幣
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減重置聲望
		// fame 100
		// 012345
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("fame ")) {
			int fame = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_FAME_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(fame);// 增減的聲望
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技儲存角色
		// rs
		// 01
		pos = 2;
		if (text.length() == pos && buff.substring(0, pos).equals("sr")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_STORE_ROLE_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減角色屬性值
		// attr 1,10,10,10
		// 012345
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("attr ")) {

			String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos), StringHelper.COMMA);
			//
			int attributeValue = 0;
			int point = 0;
			int addPoint = 0;
			int addRate = 0;
			//
			if (buffs != null) {
				if (buffs.length > 0) {
					attributeValue = NumberHelper.toInt(buffs[0]);
				}
				if (buffs.length > 1) {
					point = NumberHelper.toInt(buffs[1]);
				}
				if (buffs.length > 2) {
					addPoint = NumberHelper.toInt(buffs[2]);
				}
				if (buffs.length > 3) {
					addRate = NumberHelper.toInt(buffs[3]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_ATTRIBUTE_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(attributeValue);// 屬性類型
			message.addInt(point);// 增減屬性值
			message.addInt(addPoint);// 增減增加的屬性值
			message.addInt(addRate);// 增減增加的比率值
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減重置儲值幣
		// coin 100
		// 012345
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("coin ")) {
			int coin = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_COIN_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(coin);// 增減的儲值幣
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技增減重置vip等級
		// vip 1
		// 01234
		pos = 4;
		if (text.length() > pos && buff.substring(0, pos).equals("vip ")) {
			int vip = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ROLE,
					CoreMessageType.ROLE_DEBUG_CHANGE_VIP_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(vip);// 增減的vip等級
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 聊天
		// ---------------------------------------------------
		// 祕技儲存聊天角色
		// cs
		// 01
		pos = 2;
		if (text.length() == pos && buff.substring(0, pos).equals("sc")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.CHAT,
					CoreMessageType.CHAT_DEBUG_STORE_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 四象
		// ---------------------------------------------------
		// 祕技玩四象
		// sasangp 1
		// 012345678
		pos = 8;
		if (text.length() > pos && buff.substring(0, pos).equals("sasangp ")) {
			int playValue = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.SASANG,
					CoreMessageType.SASANG_DEBUG_PLAY_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(playValue);// 玩的類別
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技四象中獎區所有道具放入包包
		// sasanga
		// 0123456
		pos = 7;
		if (text.length() == pos && buff.substring(0, pos).equals("sasanga")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.SASANG,
					CoreMessageType.SASANG_DEBUG_PUT_ALL_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技四象重置
		// sasangr
		// 0123456
		pos = 7;
		if (text.length() == pos && buff.substring(0, pos).equals("sasangr")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.SASANG,
					CoreMessageType.SASANG_DEBUG_RESET_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 道具
		// ---------------------------------------------------
		// 祕技增減重置道具
		// item T_TREASURE_REFRESH_G001,100
		// 012345
		pos = 5;
		if (text.length() > pos && buff.substring(0, pos).equals("item ")) {
			String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos), ",");
			if (buffs.length == 2) {
				String itemId = buffs[0];
				int amount = NumberHelper.toInt(buffs[1]);
				//
				Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ITEM,
						CoreMessageType.ITEM_DEBUG_CHANGE_ITEM_REQUEST);
				message.addString(roleId);// 角色id
				message.addString(itemId);// 道具id
				message.addInt(amount);// 道具數量
				//
				messageService.addMessage(message);
			}
			return;
		}

		// 祕技包包清除
		// bagc
		// bagc 0
		// 012345
		pos = 4;
		if (text.length() >= pos && buff.substring(0, pos).equals("bagc")) {
			int tabIndex = -1;
			if (buff.length() > pos) {
				tabIndex = NumberHelper.toInt(buff.substring(pos + 1));
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.ITEM,
					CoreMessageType.ITEM_DEBUG_CLEAR_BAG_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(tabIndex);// 包包頁id,tabIndex
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 莊園
		// ---------------------------------------------------
		// 祕技莊園開墾
		// manorr 0,L_TROPICS_G001
		// 01234567
		pos = 7;
		if (text.length() > pos && buff.substring(0, pos).equals("manorr ")) {
			String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos), ",");
			if (buffs.length == 2) {
				int farmIndex = NumberHelper.toInt(buffs[0]);
				String itemId = buffs[1];
				//
				Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
						CoreMessageType.MANOR_DEBUG_RECLAIM_REQUEST);
				message.addString(roleId);// 角色id
				message.addInt(farmIndex);// 農場id,farmIndex
				message.addString(itemId);// 道具id
				//
				messageService.addMessage(message);
			}
			return;
		}

		// 祕技莊園休耕
		// manord
		// manord 0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manord")) {
			int farmIndex = -1;
			if (buff.length() > pos) {
				farmIndex = NumberHelper.toInt(buff.substring(pos + 1));
			}
			//
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_DISUSE_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園種植
		// manorp S_COTTON_G001
		// 01234567
		pos = 7;
		if (text.length() > pos && buff.substring(0, pos).equals("manorp ")) {
			String itemId = buff.substring(pos);
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_PLANT_REQUEST);
			message.addString(roleId);// 角色id
			message.addString(itemId);// 道具id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園澆水
		// manorw
		// manorw 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manorw")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_WATER_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園祈禱
		// manory
		// manory 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manory")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_PRAY_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園加速
		// manors
		// manors 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manors")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_SPEED_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園收割
		// manorh
		// manorh 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manorh")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_HARVEST_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園復活
		// manorv
		// manorv 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manorv")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_REVIVE_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園清除
		// manorc
		// manorc 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manorc")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_CLEAR_REQUEST);
			//
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園成熟
		// manorm
		// manorm 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manorm")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_MATURE_REQUEST);
			//
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技莊園枯萎
		// manori
		// manori 0,0
		// 01234567
		pos = 6;
		if (text.length() >= pos && buff.substring(0, pos).equals("manori")) {
			int farmIndex = -1;
			int gridIndex = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					farmIndex = NumberHelper.toInt(buffs[0]);
					gridIndex = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.MANOR,
					CoreMessageType.MANOR_DEBUG_WITHER_REQUEST);
			//
			message.addString(roleId);// 角色id
			message.addInt(farmIndex);// 農場id,farmIndex
			message.addInt(gridIndex);// 格子id,gridIndex
			//
			messageService.addMessage(message);
			return;
		}
		// ---------------------------------------------------
		// TODO 祕寶
		// ---------------------------------------------------
		// 祕技祕寶刷新
		// treasurer
		// 012345678
		pos = 9;
		if (text.length() == pos && buff.substring(0, pos).equals("treasurer")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TREASURE,
					CoreMessageType.TREASURE_DEBUG_REFRESH_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技祕寶購買
		// treasureb
		// treasureb 1,0
		// 01234567890
		pos = 9;
		if (text.length() >= pos && buff.substring(0, pos).equals("treasureb")) {
			int buyValue = -1;
			int index = -1;
			if (buff.length() > pos) {
				String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos + 1), ",");
				if (buffs.length == 2) {
					buyValue = NumberHelper.toInt(buffs[0]);
					index = NumberHelper.toInt(buffs[1]);
				}
			}
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TREASURE,
					CoreMessageType.TREASURE_DEBUG_BUY_REQUEST);
			//
			message.addString(roleId);// 角色id
			message.addInt(buyValue);// 購買類別
			message.addInt(index);// 上架祕寶索引
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 訓練
		// ---------------------------------------------------
		// 祕技訓練加入
		// trainj
		// 012345
		pos = 6;
		if (text.length() == pos && buff.substring(0, pos).equals("trainj")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TRAIN,
					CoreMessageType.TRAIN_DEBUG_JOIN_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技訓練離開
		// trainj
		// 012345
		pos = 6;
		if (text.length() == pos && buff.substring(0, pos).equals("trainq")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TRAIN,
					CoreMessageType.TRAIN_DEBUG_QUIT_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技訓練鼓舞
		// trainj
		// 012345
		pos = 6;
		if (text.length() == pos && buff.substring(0, pos).equals("traini")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TRAIN,
					CoreMessageType.TRAIN_DEBUG_INSPIRE_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技訓練重置
		// trainr
		// 012345
		pos = 6;
		if (text.length() == pos && buff.substring(0, pos).equals("trainr")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.TRAIN,
					CoreMessageType.TRAIN_DEBUG_RESET_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// ---------------------------------------------------
		// TODO 五行
		// ---------------------------------------------------
		// 祕技玩五行
		// wuxingp 1
		// 012345678
		pos = 8;
		if (text.length() > pos && buff.substring(0, pos).equals("wuxingp ")) {
			int playValue = NumberHelper.toInt(buff.substring(pos));
			//
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.WUXING,
					CoreMessageType.WUXING_DEBUG_PLAY_REQUEST);
			message.addString(roleId);// 角色id
			message.addInt(playValue);// 玩的類別
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技五行中獎區所有道具放入包包
		// wuxinga
		// 0123456
		pos = 7;
		if (text.length() == pos && buff.substring(0, pos).equals("wuxinga")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.WUXING,
					CoreMessageType.WUXING_DEBUG_PUT_ALL_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

		// 祕技五行重置
		// wuxingr
		// 0123456
		pos = 7;
		if (text.length() == pos && buff.substring(0, pos).equals("wuxingr")) {
			Message message = messageService.createMessage(CoreModuleType.CHAT, CoreModuleType.WUXING,
					CoreMessageType.WUXING_DEBUG_RESET_REQUEST);
			message.addString(roleId);// 角色id
			//
			messageService.addMessage(message);
			return;
		}

	}

	// /**
	// * 請求
	// *
	// * @param roleId
	// * @param text
	// */
	// public void request(String roleId, String text)
	// {
	// if (StringHelper.isBlank(text))
	// {
	// return;
	// }
	// //
	// int pos = 0;
	// StringBuilder buff = new StringBuilder(text);
	//
	// //請求
	// //req 210101,1
	// //01234
	// pos = 4;
	// if (text.length() > pos && buff.substring(0, pos).equals("req "))
	// {
	// String[] buffs = StringUtils.splitPreserveAllTokens(buff.substring(pos),
	// ",");
	// if (buffs.length > 0)
	// {
	// int messageIntValue = NumberHelper.toInt(buffs[0]);
	// CoreMessageType messageType = (CoreMessageType) EnumHelper.valueOf(
	// CoreMessageType.class, messageIntValue);
	// if (messageType == null)
	// {
	// return;
	// }
	// //
	// switch (messageType)
	// {
	// //req 210101,1
	// case SASANG_SYMBOL_PLAY_REQUEST:
	// {
	// Message message = messageService.createMessage(CoreModuleType.CHAT,
	// CoreModuleType.SASANG, CoreMessageType.SASANG_PLAY_REQUEST);
	// message.addString(roleId);//角色id
	// message.addInt(NumberHelper.toInt(buffs[1]));//玩的類型
	// //
	// messageService.addMessage(message);
	// break;
	// }
	// default:
	// {
	// break;
	// }
	// }
	//
	// }
	// return;
	// }
	//
	// }
}