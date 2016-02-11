package org.openyu.mix.chat.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openyu.commons.bean.BaseBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 頻道設定
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Channel extends BaseBean {
	String KEY = Channel.class.getName();

	/**
	 * 聊天類別,key
	 * 
	 * @return
	 */
	ChannelType getId();

	void setId(ChannelType id);

	/**
	 * 是否開啟頻道
	 * 
	 * @return
	 */
	boolean isOpened();

	void setOpened(boolean opened);

}
