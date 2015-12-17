package org.openyu.mix.role.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.flutter.vo.AttributeGroup;
import org.openyu.mix.flutter.vo.Flutter;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.treasure.vo.TreasurePen;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.mix.wuxing.vo.WuxingPen;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 角色
 * 
 * 屬性=裝備(equipment)+坐騎(mount)+寵物(pet)+官階(official)
 * 
 * 
 * 1.屬性 attributeGroup
 * 
 * point=行業屬性初值+(level-1)*成長值
 * 
 * addPoint=???
 * 
 * addRate=???
 * 
 * 2.裝備屬性群 equipmentGroup
 * 
 * point=初值
 * 
 * addPoint=強化值
 * 
 * addRate=強化率
 * 
 * 3.坐騎屬性群 mountGroup
 * 
 * 4.寵物屬性群 petGroup
 * 
 * 5.官階屬性群 officialGroup
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Role extends Flutter {

	String KEY = Role.class.getName();

	/**
	 * 是否已連線
	 * 
	 * @see org.openyu.mix.role.service.impl.RoleServiceImpl.connect
	 * 
	 * @return
	 */
	boolean isConnected();

	void setConnected(boolean connected);

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	boolean getValid();

	void setValid(boolean valid);

	// /**
	// * 帳戶,不做關連,改直接用accountId,增加效率
	// *
	// * @return
	// */
	// Account getAccount();
	//
	// void setAccount(Account account);

	/**
	 * 帳戶id
	 * 
	 * @return
	 */
	String getAccountId();

	void setAccountId(String accountId);

	/**
	 * 上線時間
	 * 
	 * @return
	 */
	long getEnterTime();

	void setEnterTime(long enterTime);

	/**
	 * 離線時間
	 * 
	 * @return
	 */
	long getLeaveTime();

	void setLeaveTime(long leaveTime);

	/**
	 * 取得接收器id
	 * 
	 * @return
	 */
	String getAcceptorId();

	void setAcceptorId(String acceptorId);

	/**
	 * vip類別,由account.accuCoin來決定
	 * 
	 * @return
	 */
	VipType getVipType();

	/**
	 * 有事件觸發
	 * 
	 * @param vipType
	 */
	void setVipType(VipType vipType);

	/**
	 * 是否為vip
	 * 
	 * @return
	 */
	boolean isVipType();

	/**
	 * 裝備屬性群
	 * 
	 * @return
	 */
	AttributeGroup getEquipmentGroup();

	void setEquipmentGroup(AttributeGroup equipmentGroup);

	/**
	 * 包包欄位
	 * 
	 * @return
	 */
	BagPen getBagPen();

	void setBagPen(BagPen bagPen);

	/**
	 * 裝備欄位
	 * 
	 * @return
	 */
	EquipmentPen getEquipmentPen();

	void setEquipmentPen(EquipmentPen equipmentPen);

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------
	/**
	 * 四象欄位
	 * 
	 * @return
	 */
	SasangPen getSasangPen();

	void setSasangPen(SasangPen sasangPen);

	/**
	 * 莊園欄位
	 * 
	 * @return
	 */
	ManorPen getManorPen();

	void setManorPen(ManorPen manorPen);

	/**
	 * 祕寶欄位
	 * 
	 * @return
	 */
	TreasurePen getTreasurePen();

	void setTreasurePen(TreasurePen treasurePen);

	/**
	 * 訓練欄位
	 * 
	 * @return
	 */
	TrainPen getTrainPen();

	void setTrainPen(TrainPen trainPen);

	/**
	 * 五行欄位
	 * 
	 * @return
	 */
	WuxingPen getWuxingPen();

	void setWuxingPen(WuxingPen wuxingPen);

}
