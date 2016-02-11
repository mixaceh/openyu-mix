package org.openyu.mix.treasure.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.IdBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 祕寶庫
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Stock extends IdBean
{

	/**
	 * 祕寶
	 * 
	 * <祕寶id,祕寶>
	 * 
	 * @return
	 */
	Map<String, Treasure> getTreasures();

	void setTreasures(Map<String, Treasure> treasures);

}
