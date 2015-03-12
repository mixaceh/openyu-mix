package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.Item;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 道具結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface ItemResult extends AppResult
{

	String KEY = ItemResult.class.getName();

	/**
	 * 道具
	 * 
	 * @return
	 */
	Item getItem();

	void setItem(Item item);
}
