package org.openyu.mix.flutter.vo.supporter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Flutter;
import org.openyu.mix.flutter.vo.FlutterField;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;
import org.openyu.commons.bean.supporter.SeqIdAuditNamesBeanSupporter;
import org.openyu.commons.lang.event.EventAttach;

@XmlRootElement(name = "flutter")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlutterSupporter extends SeqIdAuditNamesBeanSupporter implements
		Flutter {

	private static final long serialVersionUID = -8970575230009509355L;

	/**
	 * 性別
	 */
	private GenderType genderType;

	/**
	 * 髮型
	 */
	private HairType hairType;

	/**
	 * 臉型
	 */
	private FaceType faceType;

	/**
	 * 戰力
	 */
	private int power;

	/**
	 * 地圖
	 */
	private String map;

	/**
	 * 座標 x, 300 * 32
	 */
	private int pixelX;

	/**
	 * 座標 y, 200 * 32
	 */
	private int pixelY;

	// ---------------------------------------------------

	// /**
	// * 種族類別
	// */
	// private RaceType raceType;
	//
	// /**
	// * 職業類別
	// */
	// private CareerType careerType;

	/**
	 * 行業=種族+職業
	 */
	private String industryId;

	/**
	 * 經驗
	 */
	private long exp;

	/**
	 * 等級
	 */
	private int level;

	/**
	 * 技能經驗
	 */
	private long sp;

	/**
	 * 金幣
	 */
	private long gold;

	/**
	 * 聲望
	 */
	private int fame;

	/**
	 * 屬性群
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup attributeGroup = new AttributeGroupImpl();

	// /**
	// * 上次已改變的屬性type
	// */
	// private transient Map<AttributeType, Attribute> changedAttributes = new
	// LinkedHashMap<AttributeType, Attribute>();

	public FlutterSupporter() {
	}

	public GenderType getGenderType() {
		return genderType;
	}

	public void setGenderType(GenderType genderType) {
		this.genderType = genderType;
	}

	public HairType getHairType() {
		return hairType;
	}

	public void setHairType(HairType hairType) {
		this.hairType = hairType;
	}

	public FaceType getFaceType() {
		return faceType;
	}

	public void setFaceType(FaceType faceType) {
		this.faceType = faceType;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public int[] getCoordinate() {
		int[] result = new int[2];
		result[0] = this.pixelX;
		result[1] = this.pixelY;
		return result;
	}

	public void setCoordinate(int pixelX, int pixelY) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}

	// ---------------------------------------------------

	// public RaceType getRaceType()
	// {
	// return raceType;
	// }
	//
	// public void setRaceType(RaceType raceType)
	// {
	// this.raceType = raceType;
	// }
	//
	// public CareerType getCareerType()
	// {
	// return careerType;
	// }
	//
	// public void setCareerType(CareerType careerType)
	// {
	// this.careerType = careerType;
	// }

	/**
	 * 行業=種族+職業
	 * 
	 * @return
	 */
	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	/**
	 * 有事件觸發
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		EventAttach<Integer, Integer> eventAttach = eventAttach(this.level,
				level);
		//
		fireBeanChanging(this, FlutterField.LEVEL, eventAttach);
		this.level = level;
		fireBeanChanged(this, FlutterField.LEVEL, eventAttach);
	}

	public long getSp() {
		return sp;
	}

	public void setSp(long sp) {
		this.sp = sp;
	}

	/**
	 * 金幣
	 * 
	 * @return
	 */
	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	/**
	 * 聲望
	 * 
	 * @return
	 */
	public int getFame() {
		return fame;
	}

	/**
	 * 有事件觸發
	 * 
	 * @param fame
	 */
	public void setFame(int fame) {
		EventAttach<Integer, Integer> eventAttach = eventAttach(this.fame, fame);
		//
		fireBeanChanging(this, FlutterField.FAME, eventAttach);
		this.fame = fame;
		fireBeanChanged(this, FlutterField.FAME, eventAttach);
	}

	// ---------------------------------------------------
	// 屬性
	// ---------------------------------------------------
	public AttributeGroup getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup) {
		this.attributeGroup = (AttributeGroupImpl) attributeGroup;
	}

	// /**
	// * 取得已改變的屬性type
	// *
	// * @return
	// */
	// public List<AttributeType> getChangedAttributes()
	// {
	// List<AttributeType> result = new LinkedList<AttributeType>();
	// //
	// for (Attribute attribute : attributeGroup.getAttributes().values())
	// {
	// Attribute changedAttribute = changedAttributes.get(attribute.getId());
	// if (changedAttribute != null)
	// {
	// if (attribute.getPoint() != changedAttribute.getPoint()
	// || attribute.getAddPoint() != changedAttribute.getAddPoint()
	// || attribute.getAddRate() != changedAttribute.getAddRate())
	// {
	// result.add(attribute.getId());
	// }
	// }
	// else
	// {
	// result.add(attribute.getId());
	// }
	//
	// }
	// return result;
	// }
	//
	// /**
	// * 重置上次已發送的屬性
	// */
	// public void resetChangedAttributes()
	// {
	// for (Attribute attribute : attributeGroup.getAttributes().values())
	// {
	// Attribute changedAttribute = changedAttributes.get(attribute.getId());
	// if (changedAttribute == null)
	// {
	// changedAttribute = new AttributeImpl(attribute.getId());
	// }
	// //
	// changedAttribute.setPoint(attribute.getPoint());
	// changedAttribute.setAddPoint(attribute.getAddPoint());
	// changedAttribute.setAddRate(attribute.getAddRate());
	// }
	// }

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		// builder.append("raceType", raceType);
		// builder.append("careerType", careerType);
		builder.append("industryId", industryId);
		builder.append("exp", exp);
		builder.append("level", level);
		builder.append("sp", sp);
		builder.append("gold", gold);
		builder.append("attributeGroup", attributeGroup);
		return builder.toString();
	}

	public Object clone() {
		FlutterSupporter copy = null;
		copy = (FlutterSupporter) super.clone();
		copy.attributeGroup = clone(attributeGroup);
		return copy;
	}

}
