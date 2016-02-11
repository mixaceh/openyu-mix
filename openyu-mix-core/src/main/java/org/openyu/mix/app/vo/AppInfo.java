package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 欄位資訊
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AppInfo extends BaseBean
{
	String KEY = AppInfo.class.getName();

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	boolean isConnected();

	void setConnected(boolean connected);
}
