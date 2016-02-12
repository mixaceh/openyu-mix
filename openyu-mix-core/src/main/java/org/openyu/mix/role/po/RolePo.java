package org.openyu.mix.role.po;

import org.openyu.mix.flutter.po.FlutterPo;
import org.openyu.mix.manor.vo.ManorPen;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.sasang.vo.SasangPen;
import org.openyu.mix.train.vo.TrainPen;
import org.openyu.mix.treasure.vo.TreasureInfo;
import org.openyu.mix.wuxing.vo.WuxingPen;

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
	TreasureInfo getTreasureInfo();

	void setTreasureInfo(TreasureInfo treasureInfo);

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
