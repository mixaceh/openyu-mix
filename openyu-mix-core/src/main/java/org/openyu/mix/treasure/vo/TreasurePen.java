package org.openyu.mix.treasure.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.AppPen;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 祕寶欄位
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface TreasurePen extends AppPen
{
	String KEY = TreasurePen.class.getName();

	/**
	 * 刷新時間
	 * 
	 * @return
	 */
	long getRefreshTime();

	void setRefreshTime(long refreshTime);

	/**
	 * 上架祕寶
	 * 
	 * @return
	 */
	Map<Integer, Treasure> getTreasures();

	void setTreasures(Map<Integer, Treasure> treasures);
}
