package org.openyu.mix.role.po;

import org.openyu.mix.flutter.po.FlutterPo;
import org.openyu.mix.manor.vo.ManorInfo;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.sasang.vo.SasangInfo;
import org.openyu.mix.train.vo.TrainInfo;
import org.openyu.mix.treasure.vo.TreasureInfo;
import org.openyu.mix.wuxing.vo.WuxingInfo;

/**
 * 角色
 */
public interface RolePo extends FlutterPo {
	String KEY = RolePo.class.getName();

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	Boolean getValid();

	void setValid(Boolean valid);

	/**
	 * 帳戶
	 * 
	 * @return
	 */
	// AccountPo getAccount();
	//
	// void setAccount(AccountPo account);

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
	Long getEnterTime();

	void setEnterTime(Long enterTime);

	/**
	 * 離線時間
	 * 
	 * @return
	 */
	Long getLeaveTime();

	void setLeaveTime(Long leaveTime);

	/**
	 * 取得接收器id
	 * 
	 * @return
	 */
	String getAcceptorId();

	void setAcceptorId(String acceptorId);

	/**
	 * 包包欄位
	 * 
	 * @return
	 */
	BagInfo getBagInfo();

	void setBagInfo(BagInfo bagInfo);

	// ---------------------------------------------------
	// 其他模組相關欄位
	// ---------------------------------------------------
	/**
	 * 四象欄位
	 * 
	 * @return
	 */
	SasangInfo getSasangInfo();

	void setSasangInfo(SasangInfo sasangInfo);

	/**
	 * 莊園欄位
	 * 
	 * @return
	 */
	ManorInfo getManorInfo();

	void setManorInfo(ManorInfo manorInfo);

	/**
	 * 祕寶欄位
	 * 
	 * @return
	 */
	TreasureInfo getTreasureInfo();

	void setTreasureInfo(TreasureInfo treasureInfo);

	/**
	 * 訓練欄位
	 * 
	 * @return
	 */
	TrainInfo getTrainInfo();

	void setTrainInfo(TrainInfo trainInfo);

	/**
	 * 五行欄位
	 * 
	 * @return
	 */
	WuxingInfo getWuxingInfo();

	void setWuxingInfo(WuxingInfo wuxingInfo);
}
