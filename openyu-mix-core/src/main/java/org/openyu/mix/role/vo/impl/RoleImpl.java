package org.openyu.mix.role.vo.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.impl.AttributeGroupImpl;
import org.openyu.mix.flutter.vo.supporter.FlutterSupporter;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.manor.vo.impl.ManorPenImpl;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.EquipmentPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleField;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.sasang.vo.impl.SasangInfoImpl;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.train.vo.impl.TrainInfoImpl;
import org.openyu.mix.treasure.vo.TreasureInfo;
import org.openyu.mix.treasure.vo.impl.TreasureInfoImpl;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.mix.wuxing.vo.WuxingPen;
import org.openyu.mix.wuxing.vo.impl.WuxingPenImpl;
import org.openyu.commons.lang.event.EventAttach;

@XmlRootElement(name = "role")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleImpl extends FlutterSupporter implements Role {

	private static final long serialVersionUID = 245178220482833596L;

	/**
	 * 是否已連線
	 */
	@XmlTransient
	private boolean connected;

	/**
	 * 是否有效
	 */
	private boolean valid;

	// /**
	// * 帳號
	// */
	// @XmlJavaTypeAdapter(AccountIdXmlAdapter.class)
	// private Account account;

	private String accountId;

	/**
	 * 上線時間
	 */
	private long enterTime;

	/**
	 * 離線時間
	 */
	private long leaveTime;

	/**
	 * 取得接收器id
	 */
	private String acceptorId;

	/**
	 * vip類別
	 */
	private VipType vipType = VipType._0;

	/**
	 * 裝備屬性群
	 */
	@XmlElement(type = AttributeGroupImpl.class)
	private AttributeGroup equipmentGroup = new AttributeGroupImpl();

	/**
	 * 包包欄位
	 */
	@XmlElement(type = BagInfoImpl.class)
	private BagInfo bagInfo = new BagInfoImpl(this);

	/**
	 * 裝備欄位
	 */
	@XmlElement(type = EquipmentPenImpl.class)
	private EquipmentPen equipmentPen = new EquipmentPenImpl(this);

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------
	/**
	 * 四象欄位
	 */
	@XmlElement(type = SasangInfoImpl.class)
	private SasangInfo sasangInfo = new SasangInfoImpl(this);

	/**
	 * 莊園欄位
	 */
	@XmlElement(type = ManorPenImpl.class)
	private ManorPen manorPen = new ManorPenImpl(this);

	/**
	 * 祕寶欄位
	 */
	@XmlElement(type = TreasureInfoImpl.class)
	private TreasureInfo treasureInfo = new TreasureInfoImpl(this);

	/**
	 * 訓練欄位
	 */
	@XmlElement(type = TrainInfoImpl.class)
	private TrainInfo trainInfo = new TrainInfoImpl(this);

	/**
	 * 五行欄位
	 */
	@XmlElement(type = WuxingPenImpl.class)
	private WuxingPen wuxingPen = new WuxingPenImpl(this);

	public RoleImpl() {
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

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	// public Account getAccount()
	// {
	// return account;
	// }
	//
	// public void setAccount(Account account)
	// {
	// this.account = account;
	// }

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}

	public long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getAcceptorId() {
		return acceptorId;
	}

	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}

	/**
	 * vip類別,由account.accuCoin來決定
	 */
	public VipType getVipType() {
		return vipType;
	}

	/**
	 * 有事件觸發
	 * 
	 * @param vipType
	 */

	public void setVipType(VipType vipType) {
		EventAttach<VipType, VipType> eventAttach = eventAttach(this.vipType, vipType);
		//
		fireBeanChanging(this, RoleField.VIP_TYPE, eventAttach);
		this.vipType = vipType;
		fireBeanChanged(this, RoleField.VIP_TYPE, eventAttach);
	}

	public boolean isVipType() {
		return (vipType != null && vipType.getValue() > 0);
	}

	/**
	 * 裝備屬性群
	 */
	public AttributeGroup getEquipmentGroup() {
		return equipmentGroup;
	}

	public void setEquipmentGroup(AttributeGroup equipmentGroup) {
		this.equipmentGroup = equipmentGroup;
	}

	/**
	 * 包包欄位
	 * 
	 * @return
	 */
	public BagInfo getBagInfo() {
		return bagInfo;
	}

	public void setBagInfo(BagInfo bagInfo) {
		this.bagInfo = bagInfo;
	}

	/**
	 * 裝備欄位
	 * 
	 * @return
	 */
	public EquipmentPen getEquipmentPen() {
		return equipmentPen;
	}

	public void setEquipmentPen(EquipmentPen equipmentPen) {
		this.equipmentPen = equipmentPen;
	}

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------
	/**
	 * 四象欄位
	 */
	public SasangInfo getSasangInfo() {
		return sasangInfo;
	}

	public void setSasangInfo(SasangInfo sasangInfo) {
		this.sasangInfo = sasangInfo;
	}

	/**
	 * 莊園欄位
	 */
	public ManorPen getManorPen() {
		return manorPen;
	}

	public void setManorPen(ManorPen manorPen) {
		this.manorPen = manorPen;
	}

	/**
	 * 祕寶欄位
	 */
	public TreasureInfo getTreasureInfo() {
		return treasureInfo;
	}

	public void setTreasureInfo(TreasureInfo treasureInfo) {
		this.treasureInfo = treasureInfo;
	}

	/**
	 * 訓練欄位
	 */
	public TrainInfo getTrainInfo() {
		return trainInfo;
	}

	public void setTrainInfo(TrainInfo trainInfo) {
		this.trainInfo = trainInfo;
	}

	/**
	 * 五行欄位
	 */
	public WuxingPen getWuxingPen() {
		return wuxingPen;
	}

	public void setWuxingPen(WuxingPen wuxingPen) {
		this.wuxingPen = wuxingPen;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("connected", connected);
		builder.append("valid", valid);
		// builder.append("account", (account != null ? account.getId() :
		// null));
		builder.append("accountId", accountId);
		builder.append("enterTime", enterTime);
		builder.append("leaveTime", leaveTime);
		builder.append("acceptorId", acceptorId);
		//
		builder.append("vipType", vipType);
		builder.append("equipmentGroup", equipmentGroup);
		builder.append("bagInfo", bagInfo);
		builder.append("equipmentPen", equipmentPen);
		//
		builder.append("sasangInfo", sasangInfo);
		builder.append("manorPen", manorPen);
		builder.append("treasureInfo", treasureInfo);
		builder.append("trainInfo", trainInfo);
		builder.append("wuxingPen", wuxingPen);
		return builder.toString();
	}

	public Object clone() {
		RoleImpl copy = null;
		copy = (RoleImpl) super.clone();
		// copy.account = clone(account);
		copy.equipmentGroup = clone(equipmentGroup);
		copy.bagInfo = clone(bagInfo);
		copy.equipmentPen = clone(equipmentPen);
		//
		copy.sasangInfo = clone(sasangInfo);
		copy.manorPen = clone(manorPen);
		copy.treasureInfo = clone(treasureInfo);
		copy.trainInfo = clone(trainInfo);
		copy.wuxingPen = clone(wuxingPen);
		return copy;
	}
}
