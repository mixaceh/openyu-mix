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
import org.openyu.mix.role.vo.BagPen;
import org.openyu.mix.role.vo.EquipmentPen;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleField;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.sasang.vo.impl.SasangPenImpl;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.train.vo.impl.TrainPenImpl;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.treasure.vo.impl.TreasurePenImpl;
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
	@XmlElement(type = BagPenImpl.class)
	private BagPen bagPen = new BagPenImpl(this);

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
	@XmlElement(type = SasangPenImpl.class)
	private SasangPen sasangPen = new SasangPenImpl(this);

	/**
	 * 莊園欄位
	 */
	@XmlElement(type = ManorPenImpl.class)
	private ManorPen manorPen = new ManorPenImpl(this);

	/**
	 * 祕寶欄位
	 */
	@XmlElement(type = TreasurePenImpl.class)
	private TreasurePen treasurePen = new TreasurePenImpl(this);

	/**
	 * 訓練欄位
	 */
	@XmlElement(type = TrainPenImpl.class)
	private TrainPen trainPen = new TrainPenImpl(this);

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
	public BagPen getBagPen() {
		return bagPen;
	}

	public void setBagPen(BagPen bagPen) {
		this.bagPen = bagPen;
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
	public SasangPen getSasangPen() {
		return sasangPen;
	}

	public void setSasangPen(SasangPen sasangPen) {
		this.sasangPen = sasangPen;
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
	public TreasurePen getTreasurePen() {
		return treasurePen;
	}

	public void setTreasurePen(TreasurePen treasurePen) {
		this.treasurePen = treasurePen;
	}

	/**
	 * 訓練欄位
	 */
	public TrainPen getTrainPen() {
		return trainPen;
	}

	public void setTrainPen(TrainPen trainPen) {
		this.trainPen = trainPen;
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
		builder.append("bagPen", bagPen);
		builder.append("equipmentPen", equipmentPen);
		//
		builder.append("sasangPen", sasangPen);
		builder.append("manorPen", manorPen);
		builder.append("treasurePen", treasurePen);
		builder.append("trainPen", trainPen);
		builder.append("wuxingPen", wuxingPen);
		return builder.toString();
	}

	public Object clone() {
		RoleImpl copy = null;
		copy = (RoleImpl) super.clone();
		// copy.account = clone(account);
		copy.equipmentGroup = clone(equipmentGroup);
		copy.bagPen = clone(bagPen);
		copy.equipmentPen = clone(equipmentPen);
		//
		copy.sasangPen = clone(sasangPen);
		copy.manorPen = clone(manorPen);
		copy.treasurePen = clone(treasurePen);
		copy.trainPen = clone(trainPen);
		copy.wuxingPen = clone(wuxingPen);
		return copy;
	}
}
