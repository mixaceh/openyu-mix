package org.openyu.mix.role.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.app.service.supporter.AppServiceSupporter;
import org.openyu.mix.app.vo.supporter.AppResultSupporter;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.flutter.vo.AttributeFactor;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;
import org.openyu.mix.flutter.vo.Industry;
import org.openyu.mix.flutter.vo.IndustryCollector;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.impl.AttributeImpl;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.DecreaseItemResult;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.dao.RoleDao;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.service.RoleHelper;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleCollector;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.mix.vip.vo.VipCollector;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.ClassHelper;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.util.AssertHelper;
import org.openyu.socklet.message.vo.Message;

/**
 * 角色服務
 */
public class RoleServiceImpl extends AppServiceSupporter implements RoleService {

	private static final long serialVersionUID = 6183181261312261182L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	@Qualifier("accountService")
	protected transient AccountService accountService;

	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("roleSetService")
	protected transient RoleSetService roleSetService;

	/**
	 * 行業數據
	 */
	protected transient IndustryCollector industryCollector = IndustryCollector.getInstance();

	/**
	 * VIP數據
	 */
	protected transient VipCollector vipCollector = VipCollector.getInstance();

	/**
	 * 角色數據
	 */
	private static RoleCollector roleCollector = RoleCollector.getInstance();

	public RoleServiceImpl() {
	}

	public RoleDao getRoleDao() {
		return (RoleDao) getCommonDao();
	}

	@Autowired
	@Qualifier("roleDao")
	public void setRoleDao(RoleDao roleDao) {
		setCommonDao(roleDao);
	}

	/**
	 * 檢查設置
	 * 
	 * @throws Exception
	 */
	protected final void checkConfig() throws Exception {
		AssertHelper.notNull(this.commonDao, "The RoleDao is required");
	}

	// --------------------------------------------------
	// db
	// --------------------------------------------------
	public Role findRole(String id) {
		RolePo orig = getRoleDao().findRole(id);
		return ClassHelper.copyProperties(orig);
	}

	public Role findRole(Locale locale, String id) {
		RolePo orig = getRoleDao().findRole(locale, id);
		return ClassHelper.copyProperties(orig);
	}

	public List<Role> findRole(boolean valid) {
		List<RolePo> orig = getRoleDao().findRole(valid);
		return ClassHelper.copyProperties(orig);
	}

	public List<Role> findRole(Locale locale, boolean valid) {
		List<RolePo> orig = getRoleDao().findRole(locale, valid);
		return ClassHelper.copyProperties(orig);
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 角色連線
	 * 
	 * @param roleId
	 * @param attatch
	 * @return
	 */
	public <T> Role roleConnect(String roleId, T attatch) {
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return null;
		}
		//
		if (!(attatch instanceof Account)) {
			return result;
		}
		Account account = (Account) attatch;

		// acceptor id
		result.setAcceptorId(getAcceptorId());

		// 上線時間
		result.setEnterTime(System.currentTimeMillis());
		// 已連線
		result.setConnected(true);

		// vip等級
		VipType vipType = vipCollector.getVipType(account.getAccuCoin());
		result.setVipType(vipType != null ? vipType : VipType._0);

		// 發送連線
		sendRoleConnect(result, attatch);
		
		// 已連線
		result.getBagPen().setConnected(true);

		// 同步連線
		sendSyncRoleConnect(result);

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
		if (!(attatch instanceof Account)) {
			return null;
		}
		Account account = (Account) attatch;

		// 發送初始
		Message message = sendInitialize(role, account);
		return message;
	}

	/**
	 * 發送初始
	 *
	 * @param role
	 * @param account
	 * @return
	 */
	protected Message sendInitialize(Role role, Account account) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_INITIALIZE_RESPONSE, role.getId());
		//
		message.addString(role.getId());// id
		message.addString(role.getName());// 名稱
		message.addLong(role.getExp());// 經驗
		message.addInt(role.getLevel());// 等級
		message.addLong(role.getGold());// 金幣
		message.addInt(role.getPower());// 戰力

		// 儲值
		message.addInt(account != null ? account.getCoin() : 0);// 儲值總額
		message.addInt(account != null ? account.getAccuCoin() : 0);// 累計儲值總額

		message.addLong(role.getEnterTime());// 上線時間
		message.addInt(role.getVipType()); // vip等級

		// 外觀
		message.addInt(role.getGenderType());// 性別
		message.addInt(role.getHairType());// 髮型
		message.addInt(role.getFaceType());// 臉型

		message.addString(role.getMap()); // 地圖

		int[] coordinate = role.getCoordinate();
		message.addInt(coordinate[0]);// 座標x
		message.addInt(coordinate[1]);// 座標y

		message.addString(role.getIndustryId());// 行業
		// 屬性
		// List<AttributeType> changedAttributes = role.getChangedAttributes();
		// fillChangedAttribute(message, role, changedAttributes);
		// role.resetChangedAttributes();

		// 填充屬性群
		RoleHelper.fillAttributeGroup(message, role.getAttributeGroup());

		// 包包
		itemService.fillBagPen(message, role.getBagPen());

		// TODO 技能

		// TODO 任務

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
		Role result = roleSetService.getRole(roleId);
		if (result == null) {
			return null;
		}

		// 離線時間
		result.setLeaveTime(System.currentTimeMillis());
		// 已斷線
		result.setConnected(false);

		// 發送斷線
		sendRoleDisconnect(result, attatch);

		// 同步斷線
		sendSyncRoleDisconnect(result);
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
	// 同步角色
	// --------------------------------------------------
	/**
	 * 發送同步角色連線
	 *
	 * @param role
	 */
	public Message sendSyncRoleConnect(Role role) {
		Message message = messageService.createSync(CoreModuleType.ROLE, CoreModuleType.ROLE,
				CoreMessageType.ROLE_SYNC_ROLE_CONNECT_REQUEST);

		message.addObject(role);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 同步角色連線
	 * 
	 * @param syncRole
	 * @return
	 */
	public Role syncRoleConnect(Role syncRole) {
		// LOGGER.info("syncRoleConnect: " + syncRole.getId());
		return roleSetService.addSyncRole(syncRole);
	}

	/**
	 * 發送同步角色斷線
	 * 
	 * @param syncRole
	 * @return
	 */
	public Message sendSyncRoleDisconnect(Role syncRole) {
		Message message = messageService.createSync(CoreModuleType.ROLE, CoreModuleType.ROLE,
				CoreMessageType.ROLE_SYNC_ROLE_DISCONNECT_REQUEST);
		message.addString(syncRole.getId());
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 同步角色斷線
	 * 
	 * @param syncRoleId
	 * @return
	 */
	public Role syncRoleDisconnect(String syncRoleId) {
		// LOGGER.info("syncRoleDisconnect: " + syncRoleId);
		return roleSetService.removeSyncRole(syncRoleId);
	}

	/**
	 * 發送同步角色欄位
	 * 
	 * @param syncRoleId
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public Message sendSyncRoleField(String syncRoleId, String fieldName, Serializable value) {
		Message message = messageService.createSync(CoreModuleType.ROLE, CoreModuleType.ROLE,
				CoreMessageType.ROLE_SYNC_ROLE_FIELD_REQUEST);

		message.addString(syncRoleId);
		message.addString(fieldName);
		message.addObject(value);
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 同步, 同步角色的欄位
	 * 
	 * @param syncRoleId
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public Object syncRoleField(String syncRoleId, String fieldName, Object value) {
		// LOGGER.info("syncRoleField: " + syncRoleId
		// +", "+syncRoleId+", "+value);

		// 檢查條件
		Role syncRole = roleSetService.getSyncRole(syncRoleId);
		if (syncRole == null) {
			return null;
		}
		//
		// 取field來set
		Field field = ClassHelper.getDeclaredField(RoleImpl.class, fieldName);
		if (field != null) {
			// System.out.println(ClassHelper.getFieldValue(syncRole, field));
			//
			ClassHelper.setFieldValue(syncRole, field, value);
			//
			// System.out.println(ClassHelper.getFieldValue(syncRole, field));
		} else {
			LOGGER.warn("Can't synchronize syncRole field [" + fieldName + "]");
		}
		return value;
	}

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 建構角色
	 *
	 * @param roleId
	 * @param name
	 * @param raceType
	 * @param careerType
	 * @return
	 */
	public Role createRole(String roleId, String name, RaceType raceType, CareerType careerType, GenderType genderType,
			HairType hairType, FaceType faceType) {
		Role result = null;
		// 行業
		Industry industry = industryCollector.getIndustry(raceType, careerType);
		if (industry == null) {
			LOGGER.warn("No industry in race or career XML to be created");
			return result;
		}

		// 角色
		result = new RoleImpl();
		result.setId(roleId);
		result.setName(name);
		result.setValid(true);
		//
		result.setIndustryId(industry.getId());// 行業
		result.setLevel(1);// 等級,有事件觸發
		result.setExp(0L);// 經驗
		result.setGold(0L);// 金幣
		result.setValid(true);// 啟用
		result.setVipType(VipType._0);// vip=0,有事件觸發

		// 外觀
		result.setGenderType(genderType);
		result.setHairType(hairType);
		result.setFaceType(faceType);

		// 地圖
		result.setMap("map01");

		// 座標
		result.setCoordinate(300 * 32, 200 * 32);

		// 重新計算所有屬性
		calcAttrubutes(result);

		// TODO 技能

		return result;
	}

	/**
	 * 重新計算所有屬性,等級,行業會改變屬性
	 *
	 * 屬性=行業屬性初值+(level-1)*成長值
	 *
	 * point=industry.ponit+(role.level-1)*industry.addoint
	 *
	 * @param role
	 * @return
	 */
	public boolean calcAttrubutes(Role role) {
		boolean result = false;
		//
		Industry industry = industryCollector.getIndustry(role.getIndustryId());
		if (industry == null) {
			return result;
		}

		// 基礎屬性,五大屬性
		calcAttrubute(role, industry, AttributeType.STRENGTH);// 力量
		calcAttrubute(role, industry, AttributeType.AGILITY);// 敏捷
		calcAttrubute(role, industry, AttributeType.INTELLIGENCE);// 智力
		calcAttrubute(role, industry, AttributeType.SPIRIT);// 精神
		calcAttrubute(role, industry, AttributeType.CONSTITUTION);// 體質

		// 物攻 calcPhysicalAttack
		// 物攻致命(暴擊)率 calcPhysicalCriticalRate
		// 物攻致命(暴擊)傷害率 calcPhysicalCriticalDamageRate
		calcAttrubute(role, industry, AttributeType.PHYSICAL_HIT_RATE);// 物攻命中率
		calcAttrubute(role, industry, AttributeType.PHYSICAL_ATTACK_SPEED);// 物攻速度

		// 物防 calcMagicDefence
		calcAttrubute(role, industry, AttributeType.PHYSICAL_DODGE_RATE);// 物防迴避率

		// 魔攻 calcMagicAttack
		// 魔攻致命(暴擊)率 calcMagicCriticalRate
		// 魔攻致命(暴擊)傷害率 calcMagicCriticalDamageRate
		calcAttrubute(role, industry, AttributeType.MAGIC_HIT_RATE);// 魔攻命中率
		calcAttrubute(role, industry, AttributeType.MAGIC_ATTACK_SPEED);// 施法速度

		// 魔防 calcMagicDefence
		calcAttrubute(role, industry, AttributeType.MAGIC_DODGE_RATE);// 魔防迴避率

		// 共通屬性
		calcAttrubute(role, industry, AttributeType.MOVE_SPEED);// 移動速度

		// 含有因子的屬性
		calcFactorAttrubutes(role, industry);
		//
		result = true;
		return result;
	}

	/**
	 * 計算屬性
	 *
	 * 屬性=行業屬性初值+(level-1)*成長值
	 *
	 * @param role
	 * @param industry
	 * @param attributeType
	 */
	protected void calcAttrubute(Role role, Industry industry, AttributeType attributeType) {
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		// 行業成長值
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		attributeGroup.setPoint(attributeType, initPoint + levelFactor * addPoint);
	}

	protected int safeLevel(int level) {
		int maxLevel = industryCollector.getMaxLevel();
		// int maxLevel=200;
		int result = (level < 1 ? 1 : (level > maxLevel ? maxLevel : level));
		return result;
	}

	/**
	 * 計算含有因子的屬性
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcFactorAttrubutes(Role role, Industry industry) {
		// 物攻
		calcPhysicalAttack(role, industry);
		// 物攻致命(暴擊)率
		calcPhysicalCriticalRate(role, industry);
		// 物攻致命(暴擊)傷害率
		calcPhysicalCriticalDamageRate(role, industry);
		// 物防
		calcPhysicalDefence(role, industry);
		// 物防迴避率
		calcPhysicalDodgeRate(role, industry);

		// 魔攻
		calcMagicAttack(role, industry);
		// 魔攻致命(暴擊)率
		calcMagicCriticalRate(role, industry);
		// 魔攻致命(暴擊)傷害率
		calcMagicCriticalDamageRate(role, industry);
		// 魔防
		calcMagicDefence(role, industry);
		// 魔防迴避率
		calcMagicDodgeRate(role, industry);

		// max health
		calcMaxHealth(role, industry, true);
		// max mana
		calcMaxMana(role, industry, true);
	}

	/**
	 * 物攻
	 *
	 * STRENGTH*AttributeFactor.factor+AGILITY*AttributeFactor.factor2
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcPhysicalAttack(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.PHYSICAL_ATTACK;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		double factor2 = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
			factor2 = ratio(attributeFactor.getFactor2());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.STRENGTH) * factor)
				+ (int) (attributeGroup.getFinal(AttributeType.AGILITY) * factor2);
		//
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 物攻致命(暴擊)率
	 *
	 * AGILITY*AttributeFactor.factor+STRENGTH*AttributeFactor.factor2
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcPhysicalCriticalRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.PHYSICAL_CRITICAL_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		double factor2 = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
			factor2 = ratio(attributeFactor.getFactor2());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.AGILITY) * factor)
				+ (int) (attributeGroup.getFinal(AttributeType.STRENGTH) * factor2);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 物攻致命(暴擊)傷害率
	 *
	 * STRENGTH*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcPhysicalCriticalDamageRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.PHYSICAL_CRITICAL_DAMAGE_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.STRENGTH) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 物防
	 *
	 * AGILITY*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcPhysicalDefence(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.PHYSICAL_DEFENCE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.AGILITY) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 物防迴避率
	 *
	 * AGILITY*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcPhysicalDodgeRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.PHYSICAL_DODGE_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.AGILITY) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 魔攻
	 *
	 * INTELLIGENCE*AttributeFactor.factor+SPIRIT*AttributeFactor.factor2
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMagicAttack(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.MAGIC_ATTACK;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		double factor2 = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
			factor2 = ratio(attributeFactor.getFactor2());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.INTELLIGENCE) * factor)
				+ (int) (attributeGroup.getFinal(AttributeType.SPIRIT) * factor2);
		//
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 魔攻致命(暴擊)率
	 *
	 * SPIRIT*AttributeFactor.factor+INTELLIGENCE*AttributeFactor.factor2
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMagicCriticalRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.MAGIC_CRITICAL_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		double factor2 = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
			factor2 = ratio(attributeFactor.getFactor2());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.SPIRIT) * factor)
				+ (int) (attributeGroup.getFinal(AttributeType.INTELLIGENCE) * factor2);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 魔攻致命(暴擊)傷害率
	 *
	 * INTELLIGENCE*AttributeFactor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMagicCriticalDamageRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.MAGIC_CRITICAL_DAMAGE_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.INTELLIGENCE) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 魔防
	 *
	 * SPIRIT*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMagicDefence(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.MAGIC_DEFENCE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.SPIRIT) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * 魔防迴避率
	 *
	 * SPIRIT*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMagicDodgeRate(Role role, Industry industry) {
		AttributeType attributeType = AttributeType.MAGIC_DODGE_RATE;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.SPIRIT) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
	}

	/**
	 * max hp
	 *
	 * CONSTITUTION*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMaxHealth(Role role, Industry industry, boolean changeHp) {
		AttributeType attributeType = AttributeType.MAX_HEALTH;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.CONSTITUTION) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
		//
		if (changeHp) {
			attributeGroup.setPoint(AttributeType.HEALTH, attributeGroup.getFinal(AttributeType.MAX_HEALTH));
		}
	}

	/**
	 * max mp
	 *
	 * SPIRIT*AttributeFactor.factor
	 *
	 * @param role
	 * @param industry
	 */
	protected void calcMaxMana(Role role, Industry industry, boolean changeMp) {
		AttributeType attributeType = AttributeType.MAX_MANA;
		// 等級因子
		int levelFactor = safeLevel(role.getLevel()) - 1;

		// 行業初值
		int initPoint = industry.getAttributeGroup().getPoint(attributeType);
		int addPoint = industry.getAttributeGroup().getAddPoint(attributeType);

		// 屬性因子
		double factor = 1d;
		AttributeFactor attributeFactor = industryCollector.getAttributeFactor(attributeType);
		if (attributeFactor != null) {
			factor = ratio(attributeFactor.getFactor());
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		int weightPoint = (int) (attributeGroup.getFinal(AttributeType.SPIRIT) * factor);
		attributeGroup.setPoint(attributeType, initPoint + weightPoint + levelFactor * addPoint);
		//
		if (changeMp) {
			attributeGroup.setPoint(AttributeType.MANA, attributeGroup.getFinal(AttributeType.MAX_MANA));
		}
	}

	/**
	 * 增減經驗,exp->level->attribute
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param exp
	 *            增減的經驗
	 * @return
	 */
	public long changeExp(boolean sendable, Role role, long exp) {
		long result = 0L;
		// 檢查條件
		// 若=0不改變了
		if (exp == 0) {
			return result;
		}
		//

		// TODO 扣經驗變負數了,先不管

		// 角色等級
		int level = role.getLevel();
		// 最大等級
		int maxLevel = industryCollector.getMaxLevel();// lv1=10w,lv2=40w
		// 目前等級的最大exp
		long maxLevelExp = industryCollector.getExp(level);
		// 會增加的等級
		int addLevel = 0;
		//
		long levelExp = role.getExp() + exp;// 9w+3w=12w
		while (level < maxLevel && levelExp >= maxLevelExp) {
			// 目前等級,該有的經驗= buffExp-levelExp,12w-10w=2w
			levelExp = levelExp - maxLevelExp;
			// 生一級了
			addLevel++;
			maxLevelExp = industryCollector.getExp(++level);
		}
		// 有升級
		if (addLevel != 0) {
			changeLevel(sendable, role, addLevel);
		}
		// 封頂了
		// buffExp=2w,exp=3w
		if (level >= maxLevel) {
			// System.out.println(buffExp + "-----" + exp);
			if (levelExp >= exp) {
				levelExp = levelExp - exp;
				exp = 0;
			}
			// buffExp=2w,exp=3w
			else {
				exp = exp - levelExp;// 1w
				levelExp = 0;
			}
		}
		//
		role.setExp(levelExp);// 目前等級,該有的經驗
		// 增減的經驗
		result = exp;// TODO 滿級時,exp需算好
		if (sendable) {
			sendExp(role, role.getExp(), result);
		}
		return exp;
	}

	/**
	 * 發送經驗
	 *
	 * @param role
	 *            角色
	 * @param exp
	 *            經驗
	 * @param diffExp
	 *            增減的經驗
	 * @return
	 */
	public Message sendExp(Role role, long exp, long diffExp) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_EXP_RESPONSE, role.getId());

		message.addLong(exp);// 目前等級的經驗
		message.addLong(diffExp);// 增減的經驗
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 增減技魂(sp)
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param sp
	 *            增減的技魂(sp)
	 * @return
	 */
	public long changeSp(boolean sendable, Role role, long sp) {
		// 增減的聲望
		long result = 0;
		// 檢查條件
		// 若=0不改變了
		if (sp == 0) {
			return result;
		}
		//
		role.setSp(role.getSp() + sp);// 可以為負的
		// 增減的聲望
		result = sp;

		// 是否發訊息
		if (result != 0 && sendable) {
			sendSp(role, role.getSp(), result);
		}
		return result;
	}

	/**
	 * 發送技魂(sp)
	 *
	 * @param role
	 *            角色
	 * @param sp
	 *            技魂(sp)
	 * @param diffSp
	 *            增減的技魂(sp)
	 * @return
	 */
	public Message sendSp(Role role, long sp, long diffSp) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_SP_RESPONSE, role.getId());

		message.addLong(sp);// 目前的技魂(sp)
		message.addLong(diffSp);// 增減的技魂(sp)
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 增減等級
	 *
	 * 1.重新計算屬性
	 *
	 * 2.增加技能
	 *
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 *            角色id
	 * @param level
	 *            要增減的等級
	 * @return
	 */
	public int changeLevel(boolean sendable, Role role, int level) {
		int result = 0;
		// 檢查條件
		//
		int beforeLevel = role.getLevel();// 改變前等級
		int buffLevel = beforeLevel;// 改變後等級
		int maxLevel = industryCollector.getMaxLevel();
		for (int i = 0; i < Math.abs(level); i++) {
			buffLevel += (level > 0 ? 1 : -1);
			if (buffLevel > maxLevel) {
				buffLevel = maxLevel;
				break;
			}
			//
			if (buffLevel < 1) {
				// 最低1級
				buffLevel = 1;
				break;
			}

			// TODO 當實際有增加等級時,學技能
			if (level > 0 && buffLevel > beforeLevel) {

			}
		}

		// 若有等級增減,=> 等級差
		result = buffLevel - beforeLevel;
		// 有增減時,會有正負等級差,沒增減時=0
		if (result != 0) {
			// 設定等級
			role.setLevel(buffLevel);
			// 重新計算所有屬性
			// recalcAttrubutes(role);//改成事件觸發,移到RoleChangeAdapter處理

			// 是否發訊息
			if (sendable) {
				sendLevel(role, role.getLevel());
				// TODO 發送屬性
				// sendAttrubute(role);
				// TODO 發送技能
				// sendSkill(role);
			}
		}
		return result;
	}

	/**
	 * 發送等級
	 *
	 * @param role
	 *            角色
	 * @param level
	 *            等級
	 * @return
	 */
	public Message sendLevel(Role role, int level) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_LEVEL_RESPONSE, role.getId());

		message.addInt(level);// 目前的等級
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 發送成名等級
	 *
	 * @param role
	 * @param diffLevel
	 * @return
	 */
	public Message sendFamousLevel(Role role, int diffLevel) {
		// 每10級發1次訊息
		int famousLevel = role.getLevel() - diffLevel;
		if ((famousLevel / 10) < (role.getLevel() / 10)) {
			// 取所有角色id
			List<String> receivers = roleSetService.getRoleIds();
			//
			Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
					CoreMessageType.ROLE_FAMOUS_LEVEL_RESPONSE, receivers);

			message.addString(role.getName());// 角色名稱
			message.addInt(role.getLevel());// 目前的等級
			//
			messageService.addMessage(message);

			return message;
		}
		return null;
	}

	// /**
	// * 發送成名等級
	// *
	// * @param receivers
	// * @param roleName
	// * @param level
	// */
	// protected void sendFamousLevel(List<String> receivers, String roleName,
	// int level)
	// {
	// Message message = messageService.createMessage(CoreModuleType.ROLE,
	// CoreModuleType.CLIENT,
	// CoreMessageType.ROLE_FAMOUS_LEVEL_RESPONSE, receivers);
	//
	// message.addString(roleName);//角色名稱
	// message.addInt(level);//目前的等級
	// //
	// messageService.addMessage(message);
	// }

	// --------------------------------------------------
	// 增減金幣
	// --------------------------------------------------
	/**
	 * 檢查增加金幣
	 *
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @return
	 */
	public boolean checkIncreaseGold(Role role, long gold) {
		boolean result = false;
		// 檢查條件
		// gold 不能為負數,都當正數處理,且兩數相加不能大於Long.MAX_VALUE
		boolean overflow = NumberHelper.isAddOverflow(gold, role.getGold());
		result = (gold < 0 ? false : !overflow);
		return result;
	}

	/**
	 * 增加金幣
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @param goldReason
	 *            log用,金幣增加的原因
	 * @return
	 */
	public long increaseGold(boolean sendable, Role role, long gold, GoldType goldReason) {
		long result = 0L;
		if (gold > 0) {
			result = changeGold(sendable, role, gold, ActionType.INCREASE, goldReason);
		}
		return result;
	}

	/**
	 * 檢查減少金幣
	 *
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @return
	 */
	public boolean checkDecreaseGold(Role role, long gold) {
		boolean result = false;
		// 檢查條件
		// gold 不能為負數,都當正數處理
		result = (gold < 0 ? false : (gold > role.getGold() ? false : true));
		return result;
	}

	/**
	 * 減少金幣
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param gold
	 *            金幣
	 * @param goldReason
	 *            log用,金幣減少的原因
	 * @return
	 */
	public long decreaseGold(boolean sendable, Role role, long gold, GoldType goldReason) {
		long result = 0L;
		//
		if (gold > 0) {
			result = changeGold(sendable, role, (-1) * gold, ActionType.DECREASE, goldReason);
		}
		return result;
	}

	/**
	 * 增減金幣
	 *
	 * @param sendable
	 *            是否發送訊息
	 * @param role
	 *            角色
	 * @param gold
	 *            增減的金幣
	 * @param goldAction
	 *            log用,金幣操作類別
	 * @param goldReason
	 *            log用,金幣增減的原因
	 * @return
	 */
	public long changeGold(boolean sendable, Role role, long gold, ActionType goldAction, GoldType goldReason) {
		long result = 0L;
		//
		// 若=0不改變了
		if (gold == 0) {
			return result;
		}
		//
		// long diffGold = 0L;
		if (gold < 0 && ActionType.INCREASE.equals(goldAction)) {
			gold = Math.abs(gold);
		} else if (gold > 0 && ActionType.DECREASE.equals(goldAction)) {
			gold = (-1) * gold;
		}
		//
		role.setGold(role.getGold() + gold);
		// 增減的金幣
		result = gold;

		// 發訊息
		if (result != 0 && sendable) {
			sendGold(role, role.getGold(), result);
		}
		return result;
	}

	/**
	 * 重置金幣,gold=0
	 *
	 * @param sendable
	 * @param role
	 * @return
	 */
	public boolean resetGold(boolean sendable, Role role, GoldType goldReason) {
		boolean result = false;
		// 檢查條件
		//
		if (role.getGold() == 0) {
			return result;
		}
		//
		role.setGold(0);
		result = true;

		// 發訊息
		if (result && sendable) {
			sendGold(role, role.getGold(), 0);
		}
		//
		return result;
	}

	/**
	 * 發送金幣
	 *
	 * @param role
	 * @param gold
	 *            金幣
	 * @param diffGold
	 *            增減的金幣
	 * @return
	 */
	public Message sendGold(Role role, long gold, long diffGold) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_GOLD_RESPONSE, role.getId());

		message.addLong(gold);// 目前的金幣
		message.addLong(diffGold);// 增減的金幣
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 增減聲望
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param fame
	 *            增減的聲望
	 * @return
	 */
	public int changeFame(boolean sendable, Role role, int fame) {
		// 增減的聲望
		int result = 0;
		// 檢查條件
		// 若=0不改變了
		if (fame == 0) {
			return result;
		}
		//
		role.setFame(role.getFame() + fame);// 可以為負的
		// 增減的聲望
		result = fame;

		// 是否發訊息
		if (result != 0 && sendable) {
			sendFame(role, role.getFame(), result);
		}
		return result;
	}

	/**
	 * 發送聲望
	 *
	 * @param role
	 *            角色id
	 * @param fame
	 *            聲望
	 * @param diffFame
	 * @return
	 */
	public Message sendFame(Role role, int fame, int diffFame) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_FAME_RESPONSE, role.getId());

		message.addInt(fame);// 目前的聲望
		message.addInt(diffFame);// 增減的聲望
		//
		messageService.addMessage(message);

		return message;
	}

	/**
	 * 增減屬性
	 *
	 * @param sendable
	 * @param role
	 * @param attributeValue
	 *            屬性類型
	 * @param point
	 *            增減屬性值
	 * @param addPoint
	 *            增減增加的屬性值
	 * @param addRate
	 *            增減增加的比率值
	 * @return
	 */
	public Attribute changeAttribute(boolean sendable, Role role, int attributeValue, int point, int addPoint,
			int addRate) {
		Attribute result = null;
		// 檢查條件
		//
		AttributeType attributeType = EnumHelper.valueOf(AttributeType.class, attributeValue);
		if (attributeType == null) {
			return result;
		}
		//
		AttributeGroup attributeGroup = role.getAttributeGroup();
		Attribute attribute = attributeGroup.getAttribute(attributeType);
		if (attribute == null) {
			attribute = new AttributeImpl(attributeType);
			attributeGroup.addAttribute(attribute);
		}
		//
		attribute.changePoint(point);
		attribute.changeAddPoint(addPoint);
		attribute.changeAddRate(addRate);
		result = attribute;
		//
		if (result != null && sendable) {
			sendAttribute(role, attribute);
		}
		return result;
	}

	/**
	 * 發送單一屬性回應
	 *
	 * @param role
	 * @param attribute
	 * @return
	 */
	public Message sendAttribute(Role role, Attribute attribute) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_ATTRIBUTE_RESPONSE, role.getId());

		RoleHelper.fillAttribute(message, attribute);
		//
		messageService.addMessage(message);

		return message;
	}

	// /**
	// * 填充屬性
	// *
	// * @param message
	// * @param attribute
	// */
	// public void fillAttribute(Message message, Attribute attribute) {
	// message.addInt(attribute.getId());
	// message.addInt(attribute.getPoint());
	// message.addInt(attribute.getFinal());
	// }

	/**
	 * 發送屬性群
	 *
	 * @param role
	 * @param attributeGroup
	 * @return
	 */
	public Message sendAttributeGroup(Role role, AttributeGroup attributeGroup) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_ATTRIBUTE_RESPONSE, role.getId());

		RoleHelper.fillAttributeGroup(message, attributeGroup);
		//
		messageService.addMessage(message);

		return message;
	}

	// /**
	// * 填充屬性群
	// *
	// * @param message
	// * @param attributeGroup
	// */
	// public void fillAttributeGroup(Message message,
	// AttributeGroup attributeGroup) {
	// Map<AttributeType, Attribute> attributes = attributeGroup
	// .getAttributes();
	// message.addInt(attributes.size());
	// for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
	// fillAttribute(message, entry.getValue());
	// }
	// }

	/**
	 * 增減vip等級
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param vip
	 *            vip等級
	 * @return
	 */
	public int changeVip(boolean sendable, Role role, int vip) {
		int result = 0;
		// 檢查條件
		// 若=0不改變了
		if (vip == 0) {
			return result;
		}
		//
		Account account = accountService.findAccount(role.getAccountId());
		if (account == null) {
			return result;
		}
		//
		VipType maxVipType = vipCollector.getMaxVipType();
		//
		int newVipValue = role.getVipType().getValue() + vip;
		VipType newVipType = null;
		if (newVipValue > maxVipType.getValue()) {
			newVipType = maxVipType;
		} else {
			newVipType = EnumHelper.valueOf(VipType.class, newVipValue);
		}
		//
		if (newVipType == null) {
			return result;
		}
		//
		VipType origVipType = role.getVipType();
		if (origVipType != null && newVipType == origVipType) {
			return result;
		}
		//
		int newAccuCoin = vipCollector.getAccuCoin(newVipType);
		if (newAccuCoin > -1) {
			account.setAccuCoin(newAccuCoin);
			// 存db
			int update = accountService.update(account);
			// 成功
			if (update > 0) {
				VipType vipType = vipCollector.getVipType(newAccuCoin);
				role.setVipType(vipType != null ? vipType : VipType._0);
				//
				result = vip;
			}
		}

		// 是否發訊息
		if (result != 0 && sendable) {
			sendVip(role, role.getVipType(), result);
		}
		return result;
	}

	/**
	 * 發送vip等級
	 *
	 * @param role
	 *            角色
	 * @param vipType
	 *            vip等級
	 * @param diffVip
	 *            增減的vip等級
	 * @return
	 */
	public Message sendVip(Role role, VipType vipType, int diffVip) {
		Message message = messageService.createMessage(CoreModuleType.ROLE, CoreModuleType.CLIENT,
				CoreMessageType.ROLE_VIP_RESPONSE, role.getId());

		message.addInt(vipType);// 目前的vip等級
		message.addInt(diffVip);// 增減的vip等級
		//
		messageService.addMessage(message);

		return message;
	}

	public static class SpendResultImpl extends AppResultSupporter implements SpendResult {

		private static final long serialVersionUID = -8640525635625123919L;

		/**
		 * 錯誤類別
		 */
		private ErrorType errorType;

		/**
		 * 真正成功扣道具及儲值幣的次數
		 */
		private int totalTimes;

		/**
		 * 消耗道具的次數
		 */
		private int itemTimes;

		/**
		 * 消耗道具的數量
		 */
		private int itemAmount;

		/**
		 * 消耗的道具
		 */
		private List<Item> items = new LinkedList<Item>();

		/**
		 * 消耗儲值幣的次數
		 */
		private int coinTimes;

		/**
		 * 消耗的儲值幣
		 */
		private int coin;

		public SpendResultImpl(ErrorType errorType) {
			this.errorType = errorType;
		}

		public ErrorType getErrorType() {
			return errorType;
		}

		public void setErrorType(ErrorType errorType) {
			this.errorType = errorType;
		}

		public int getTotalTimes() {
			return totalTimes;
		}

		public void setTotalTimes(int totalTimes) {
			this.totalTimes = totalTimes;
		}

		public int getItemTimes() {
			return itemTimes;
		}

		public void setItemTimes(int itemTimes) {
			this.itemTimes = itemTimes;
		}

		public int getItemAmount() {
			return itemAmount;
		}

		public void setItemAmount(int itemAmount) {
			this.itemAmount = itemAmount;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		public int getCoinTimes() {
			return coinTimes;
		}

		public void setCoinTimes(int coinTimes) {
			this.coinTimes = coinTimes;
		}

		public int getCoin() {
			return coin;
		}

		public void setCoin(int coin) {
			this.coin = coin;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
			builder.appendSuper(super.toString());
			builder.append("errorType", errorType);
			builder.append("totalTimes", totalTimes);
			builder.append("itemTimes", itemTimes);
			builder.append("itemAmount", itemAmount);
			builder.append("items", items);
			builder.append("coinTimes", coinTimes);
			builder.append("coin", coin);
			return builder.toString();
		}

		public Object clone() {
			SpendResultImpl copy = null;
			copy = (SpendResultImpl) super.clone();
			copy.items = clone(items);
			return copy;
		}
	}

	/**
	 * 消耗道具或儲值幣
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param itemId
	 *            消耗道具d
	 * @param everyAmount
	 *            每次扣的道具數量
	 * @param everyCoin
	 *            每次扣的儲值幣
	 * @param coinType
	 *            coinType log用,儲值增減的原因
	 * @param vipLimit
	 *            vip限制
	 * @return
	 */
	public SpendResult spendByItemCoin(boolean sendable, Role role, String itemId, int everyAmount, int everyCoin,
			CoinType coinType, VipType vipLimit) {
		SpendResult result = null;
		//
		ErrorType errorType = ErrorType.NO_ERROR;
		List<DecreaseItemResult> decreaseResults = null;
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			result = new SpendResultImpl(errorType);
			return result;
		}

		BagPen.ErrorType bagError = itemService.checkDecreaseItem(role, itemId, everyAmount);
		// 扣道具
		if (bagError == BagPen.ErrorType.NO_ERROR) {
			decreaseResults = itemService.decreaseItemWithItemId(sendable, role, itemId, everyAmount);
			// 失敗
			if (decreaseResults.size() == 0) {
				errorType = ErrorType.CAN_NOT_DECREASE_ITEM;
				result = new SpendResultImpl(errorType);
				return result;
			}
			// 扣道具成功
			result = new SpendResultImpl(ErrorType.NO_ERROR);
			result.setTotalTimes(1);
			result.setItemTimes(1);
			result.setItemAmount(everyAmount);
			for (DecreaseItemResult decreaseResult : decreaseResults) {
				result.getItems().add(decreaseResult.getItem());
			}
		}
		// 檢查消耗儲值幣
		else {
			// vip不足
			if (role.getVipType().getValue() < vipLimit.getValue()) {
				errorType = ErrorType.VIP_NOT_ENOUGH;
				result = new SpendResultImpl(errorType);
				return result;
			}

			// 儲值幣不足
			boolean checkDecrease = accountService.checkDecreaseCoin(role.getAccountId(), everyCoin);
			if (!checkDecrease) {
				errorType = ErrorType.COIN_NOT_ENOUGH;
				result = new SpendResultImpl(errorType);
				return result;
			}

			// 扣儲值幣
			int decrease = accountService.decreaseCoin(sendable, role.getAccountId(), role, everyCoin, coinType);
			// 失敗
			if (decrease == 0) {
				errorType = ErrorType.COIN_NOT_ENOUGH;
				result = new SpendResultImpl(errorType);
				return result;
			}
			// 扣儲值幣成功
			result = new SpendResultImpl(ErrorType.NO_ERROR);
			result.setTotalTimes(1);
			result.setCoinTimes(1);
			result.setCoin(everyCoin);
		}
		//
		return result;
	}

	/**
	 * 消耗道具或儲值幣
	 *
	 * @param sendable
	 * @param role
	 *            角色
	 * @param spendTimes
	 *            消耗的次數
	 * @param itemId
	 *            消耗道具d
	 * @param everyAmount
	 *            每次扣的道具數量
	 * @param everyCoin
	 *            每次扣的儲值幣
	 * @param coinType
	 *            coinType log用,儲值增減的原因
	 * @param vipLimit
	 *            vip限制
	 * @return
	 */
	public SpendResult spendByItemCoin(boolean sendable, Role role, int spendTimes, String itemId, int everyAmount,
			int everyCoin, CoinType coinType, VipType vipLimit) {
		SpendResult result = null;
		//
		ErrorType errorType = ErrorType.NO_ERROR;
		List<DecreaseItemResult> decreaseResults = null;
		// 角色不存在
		if (role == null) {
			errorType = ErrorType.ROLE_NOT_EXIST;
			result = new SpendResultImpl(errorType);
			return result;
		}
		//
		BagPen bagPen = role.getBagPen();
		//
		int itemTimes = 0;// 消耗道具的次數
		int itemAmount = 0;// 消耗道具的數量
		//
		int coinTimes = 0;// 消耗儲值幣的次數
		int coin = 0;// 消耗的儲值幣
		int totalTimes = 0;// 真正成功扣道具及儲值幣的次數
		//
		// 包包內道具數量
		int bagAmount = bagPen.getAmount(itemId);// 100
		// 可扣道具的次數
		int canItemTimes = bagAmount / everyAmount;
		// 100>50,50=50,10<50
		// 消耗道具的次數
		itemTimes = (canItemTimes > spendTimes ? spendTimes : canItemTimes);
		// 消耗道具的數量
		itemAmount = itemTimes * everyAmount;

		// 剩餘未消耗的次數,用儲值幣
		int residualTimes = spendTimes - itemTimes;
		if (residualTimes > 0) {
			// 角色的儲值幣,餘額可能為0
			int roleCoin = accountService.findCoin(role.getAccountId());

			// 可扣儲值幣的次數
			int canCoinTimes = roleCoin / everyCoin;
			// 沒得扣
			if (canCoinTimes == 0) {
				errorType = ErrorType.COIN_NOT_ENOUGH;
				result = new SpendResultImpl(errorType);
				return result;
			}
			//
			coinTimes = (canCoinTimes > residualTimes ? residualTimes : canCoinTimes);
			coin = coinTimes * everyCoin;
			// 需消耗儲值幣時,檢查消耗儲值幣
			if (coin > 0) {
				// vip不足
				if (role.getVipType().getValue() < vipLimit.getValue()) {
					errorType = ErrorType.VIP_NOT_ENOUGH;
					result = new SpendResultImpl(errorType);
					return result;
				}
				// 檢查儲值幣
				boolean checkDecrease = accountService.checkDecreaseCoin(role.getAccountId(), coin);
				if (!checkDecrease) {
					errorType = ErrorType.COIN_NOT_ENOUGH;
					result = new SpendResultImpl(errorType);
					return result;
				}

			}
		}

		// 扣道具
		if (itemAmount > 0) {
			decreaseResults = itemService.decreaseItemWithItemId(sendable, role, itemId, itemAmount);
			// 失敗
			if (decreaseResults.size() == 0) {
				errorType = ErrorType.CAN_NOT_DECREASE_ITEM;
				result = new SpendResultImpl(errorType);
				return result;
			}
			//
			totalTimes += itemTimes;
			// 扣道具成功
			result = new SpendResultImpl(ErrorType.NO_ERROR);
			result.setTotalTimes(totalTimes);
			result.setItemTimes(itemTimes);
			result.setItemAmount(itemAmount);
			for (DecreaseItemResult decreaseResult : decreaseResults) {
				result.getItems().add(decreaseResult.getItem());
			}
		}

		// 扣儲值幣
		if (coin > 0) {
			int decrease = accountService.decreaseCoin(sendable, role.getAccountId(), role, coin, coinType);
			// 失敗
			if (decrease == 0) {
				// 若有扣道具成功,但扣儲值幣失敗,把剛扣的道具滾回去
				if (decreaseResults.size() > 0) {
					totalTimes -= itemTimes;
					itemTimes = 0;
					//
					itemService.increaseItemWithItemId(sendable, role, itemId, itemAmount);
				}
				errorType = ErrorType.COIN_NOT_ENOUGH;
				result = new SpendResultImpl(errorType);
				return result;
			}

			// 成功
			totalTimes += coinTimes;
			// 扣道具+扣儲值幣成功
			if (result == null) {
				result = new SpendResultImpl(ErrorType.NO_ERROR);
			}
			result.setTotalTimes(totalTimes);
			result.setCoinTimes(coinTimes);
			result.setCoin(coin);
		}
		//
		return result;
	}
}
