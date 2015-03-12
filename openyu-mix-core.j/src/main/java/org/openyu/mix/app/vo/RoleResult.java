package org.openyu.mix.app.vo;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.role.vo.Role;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 角色結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface RoleResult extends AppResult
{

	String KEY = RoleResult.class.getName();

	/**
	 * 角色
	 * 
	 * @return
	 */
	Role getRole();

	void setRole(Role role);
}
