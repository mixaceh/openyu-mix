package org.openyu.mix.app.vo;

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.commons.bean.BaseBean;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 訊息通知
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Notice extends BaseBean
{

	/**
	 * 角色名稱
	 * 
	 * @return
	 */
	String getRoleName();

	void setRoleName(String roleName);

	/**
	 * 獎勵
	 * 
	 * <道具名稱,數量>
	 * 
	 * @return
	 */
	Map<String, Integer> getAwards();

	void setAwards(Map<String, Integer> awards);
}
