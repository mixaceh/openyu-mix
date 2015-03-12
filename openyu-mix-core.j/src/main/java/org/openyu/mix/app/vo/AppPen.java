package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 欄位
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface AppPen extends BaseBean
{
	String KEY = AppPen.class.getName();

	/**
	 * 是否已連線
	 * 
	 * @return
	 */
	boolean isConnected();

	void setConnected(boolean connected);
}
