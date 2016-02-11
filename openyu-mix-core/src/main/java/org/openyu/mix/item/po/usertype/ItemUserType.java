package org.openyu.mix.item.po.usertype;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.SessionImplementor;
import org.openyu.mix.flutter.po.usertype.AttributeGroupUserType;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.impl.ItemServiceImpl;
import org.openyu.mix.item.vo.Armor;
import org.openyu.mix.item.vo.EnhanceLevel;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.Material;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.mix.item.vo.Thing;
import org.openyu.mix.item.vo.ThingType;
import org.openyu.mix.item.vo.Weapon;
import org.openyu.mix.manor.vo.Land;
import org.openyu.mix.manor.vo.Seed;
import org.openyu.mix.manor.vo.MatureType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.hibernate.usertype.supporter.BaseUserTypeSupporter;
import org.openyu.commons.lang.ArrayHelper;

/**
 * 道具
 */
public class ItemUserType extends BaseUserTypeSupporter {

	private static final long serialVersionUID = -6499823897737328840L;

	private static transient AttributeGroupUserType attributesUserType = new AttributeGroupUserType();

	private static ItemService itemService;

	static {
		itemService = new ItemServiceImpl();
	}

	public ItemUserType() {
		// --------------------------------------------------
		// 最新版本,目前用1,若將來有新版本
		// 可用其他版號,如:VolType._2
		// --------------------------------------------------
		setVolType(VolType._1);
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public Class<?> returnedClass() {
		return Item.class;
	}

	// --------------------------------------------------
	// assemble
	// --------------------------------------------------
	/**
	 * 由物件組成欄位
	 */
	@SuppressWarnings("unchecked")
	public <R, T> R marshal(T value, SessionImplementor session) {
		R result = null;
		if (!(value instanceof Item)) {
			return result;
		}
		//
		Item src = (Item) value;
		StringBuilder dest = new StringBuilder();
		// vol
		dest.append(assembleVol(getVolType()));
		// v1
		dest.append(assembleBy_1(src));
		//
		result = (R) dest.toString();
		return result;
	}

	/**
	 * v1 由物件組成欄位
	 * 
	 * @param value
	 * @return
	 */
	public String assembleBy_1(Item src) {
		/**
		 * @see org.openyu.mix.item.service.impl.ItemServiceImpl.fillItem
		 */
		StringBuilder result = new StringBuilder();
		// id
		result.append(toString(src.getId()));// 0
		result.append(OBJECT_SPLITTER);
		// 唯一識別碼
		result.append(toString(src.getUniqueId()));// 1
		result.append(OBJECT_SPLITTER);
		// 數量
		result.append(toString(src.getAmount()));// 2
		result.append(OBJECT_SPLITTER);
		// 是否綁定(束缚)
		result.append(toString(src.isTied()));// 3
		//
		if (src instanceof Thing) {
			// 道具其它欄位
			Thing thing = (Thing) src;

			// 物品類別
			ThingType thingType = thing.getThingType();
			if (thingType == null) {
				return result.toString();
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (thingType) {
			default:
				break;
			}
		} else if (src instanceof Material) {
			// 材料相關資訊
			Material material = (Material) src;

			// 材料類別
			MaterialType materialType = material.getMaterialType();
			if (materialType == null) {
				return result.toString();
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (materialType) {
			default:
				break;
			}
		} else if (src instanceof Armor) {
			// 防具相關資訊
			Armor armor = (Armor) src;

			// 強化等級(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(armor.getEnhanceLevel()));
			// 屬性
			result.append(OBJECT_SPLITTER);
			result.append(attributesUserType.assembleBy_1(armor
					.getAttributeGroup()));
		} else if (src instanceof Weapon) {
			// 武器相關資訊
			Weapon weapon = (Weapon) src;

			// 強化等級(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(weapon.getEnhanceLevel()));
			// 屬性
			result.append(OBJECT_SPLITTER);
			result.append(attributesUserType.assembleBy_1(weapon
					.getAttributeGroup()));
		} else if (src instanceof Seed) {
			// 種子相關資訊
			Seed seed = (Seed) src;

			// 種植時間(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(seed.getPlantTime()));
			// 澆水時間(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(seed.getWaterTime()));
			// 祈禱時間(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(seed.getPrayTime()));
			// 成熟時間(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(seed.getMatureTime()));
			// 成熟類別(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(seed.getMatureType()));
		} else if (src instanceof Land) {
			// 土地相關資訊
			Land land = (Land) src;

			// 強化等級(玩家)
			result.append(OBJECT_SPLITTER);
			result.append(toString(land.getEnhanceLevel()));
			// 屬性
			result.append(OBJECT_SPLITTER);
			result.append(attributesUserType.assembleBy_1(land
					.getAttributeGroup()));
		} else {
			// just for pretty
		}
		//
		return result.toString();
	}

	// --------------------------------------------------
	// disassemble
	// --------------------------------------------------
	/**
	 * 反欄位組成物件
	 */
	@SuppressWarnings("unchecked")
	public <R, T, O> R unmarshal(T value, O owner, SessionImplementor session) {
		Item result = null;
		//
		if (!(value instanceof String)) {
			return (R) result;
		}
		//
		StringBuilder src = new StringBuilder((String) value);
		int vol = disassembleVol(src);
		VolType volType = EnumHelper.valueOf(VolType.class, vol);
		//
		if (volType == null) {
			return (R) result;
		}
		// v1
		if (volType.getValue() >= 1) {
			result = disassembleBy_1(src);
		}

		// v2,有新增的欄位,則繼續塞
		if (volType.getValue() >= 2) {

		}
		return (R) result;
	}

	public Item disassembleBy_1(StringBuilder src) {
		Item result = null;
		if (src == null) {
			return result;
		}
		//
		// 道具
		// ♥1♠T_POTION_HP_G001♦T_01234567890♦50♦1
		// 武器
		// ♥1♠W_MARS_SWORD_E001♦W_01234567890♦1♦0♦7♦2♦21♣50♣5♣0♦22♣30♣3♣0
		String[] values = StringUtils.splitPreserveAllTokens(src.toString(),
				OBJECT_SPLITTER);
		if (ArrayHelper.isEmpty(values)) {
			return result;
		}
		int idx = 0;
		// 道具
		// id
		String id = toObject(values, idx++, String.class);// 0
		// 唯一識別碼
		String uniqueId = toObject(values, idx++, String.class);// 1
		// 數量
		int amount = toObject(values, idx++, int.class);// 2

		// 建構道具
		result = itemService.createItem(id, uniqueId, amount);
		// 無法建構道具就傳回null
		if (result == null) {
			return result;
		}
		//
		// 是否綁定(束缚)
		boolean tied = toObject(values, idx++, boolean.class);// 3
		result.setTied(tied);

		// 其他欄位
		if (result instanceof Thing) {
			// 道具相關資訊
			Thing thing = (Thing) result;

			// 物品類別
			ThingType thingType = thing.getThingType();
			if (thingType == null) {
				return result;
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (thingType) {
			default:
				break;
			}
		} else if (result instanceof Material) {
			// 材料相關資訊
			Material material = (Material) result;

			// 材料類別
			MaterialType materialType = material.getMaterialType();
			if (materialType == null) {
				return result;
			}
			// 可依照不同的類別,再塞其他需要放的field
			switch (materialType) {
			default:
				break;
			}
		} else if (result instanceof Armor) {
			// 防具相關資訊
			Armor armor = (Armor) result;

			// 強化等級(玩家)
			EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class,
					toObject(values, idx++, int.class));
			armor.setEnhanceLevel(enhanceLevel != null ? enhanceLevel
					: EnhanceLevel._0);

			// 屬性
			// 2♦21♣50♣5♣0♦22♣30♣3♣0
			StringBuilder buff = new StringBuilder();
			for (int i = idx; i < values.length; i++) {
				buff.append(values[idx++]);
				if (i < values.length - 1) {
					buff.append(OBJECT_SPLITTER);
				}
			}
			// 有屬性時
			if (buff.length() > 0) {
				AttributeGroup attributeGroup = attributesUserType
						.disassembleBy_1(buff);
				armor.setAttributeGroup(attributeGroup);
			} else {
				// 若無屬性
				armor.setAttributeGroup(new AttributeGroupImpl());// 清掉預設屬性,因itemService.createItem預設有屬性
			}
		} else if (result instanceof Weapon) {
			// 武器相關資訊
			Weapon weapon = (Weapon) result;

			// 強化等級(玩家)
			EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class,
					toObject(values, idx++, int.class));
			weapon.setEnhanceLevel(enhanceLevel != null ? enhanceLevel
					: EnhanceLevel._0);

			// 屬性
			// 2♦21♣50♣5♣0♦22♣30♣3♣0
			StringBuilder buff = new StringBuilder();
			for (int i = idx; i < values.length; i++) {
				buff.append(values[idx++]);
				if (i < values.length - 1) {
					buff.append(OBJECT_SPLITTER);
				}
			}
			// 有屬性時
			if (buff.length() > 0) {
				AttributeGroup attributeGroup = attributesUserType
						.disassembleBy_1(buff);
				weapon.setAttributeGroup(attributeGroup);
			} else {
				// 若無屬性
				weapon.setAttributeGroup(new AttributeGroupImpl());// 清掉預設屬性,因itemService.createItem預設有屬性
			}
		} else if (result instanceof Seed) {
			// 種子相關資訊
			Seed seed = (Seed) result;

			// 種植時間(玩家)
			long plantTime = toObject(values, idx++, long.class);
			seed.setPlantTime(plantTime);
			// 澆水時間(玩家)
			long waterTime = toObject(values, idx++, long.class);
			seed.setWaterTime(waterTime);
			// 祈禱時間(玩家)
			long prayTime = toObject(values, idx++, long.class);
			seed.setPrayTime(prayTime);
			// 成熟時間(玩家)
			long matureTime = toObject(values, idx++, long.class);
			seed.setMatureTime(matureTime);
			// 成熟類別(玩家)
			MatureType matureType = EnumHelper.valueOf(MatureType.class,
					toObject(values, idx++, int.class));
			seed.setMatureType(matureType != null ? matureType
					: MatureType.PENDING);
		} else if (result instanceof Land) {
			// 土地相關資訊
			Land land = (Land) result;

			// 強化等級(玩家)
			EnhanceLevel enhanceLevel = EnumHelper.valueOf(EnhanceLevel.class,
					toObject(values, idx++, int.class));
			land.setEnhanceLevel(enhanceLevel != null ? enhanceLevel
					: EnhanceLevel._0);

			// 屬性
			// 2♦21♣50♣5♣0♦22♣30♣3♣0
			StringBuilder buff = new StringBuilder();
			for (int i = idx; i < values.length; i++) {
				buff.append(values[idx++]);
				if (i < values.length - 1) {
					buff.append(OBJECT_SPLITTER);
				}
			}
			// 有屬性時
			if (buff.length() > 0) {
				AttributeGroup attributeGroup = attributesUserType
						.disassembleBy_1(buff);
				land.setAttributeGroup(attributeGroup);
			} else {
				// 若無屬性
				land.setAttributeGroup(new AttributeGroupImpl());// 清掉預設屬性,因itemService.createItem預設有屬性
			}

		} else {
			// 其他
		}
		//
		return result;
	}
}
