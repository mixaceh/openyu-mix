package org.openyu.mix.role.dao;

import java.util.List;
import java.util.Locale;

import org.openyu.mix.app.dao.AppDao;
import org.openyu.mix.role.po.RolePo;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface RoleDao extends AppDao
{
	/**
	 * 查詢角色
	 * 
	 * @param roleId
	 * @return
	 */
	RolePo findRole(String roleId);

	/**
	 * 查詢角色
	 * 
	 * @param locale
	 * @param roleId
	 * @return
	 */
	RolePo findRole(Locale locale, String roleId);

	/**
	 * 查詢是否有效角色
	 * 
	 * @param valid
	 * @return
	 */
	List<RolePo> findRole(boolean valid);

	/**
	 * 分頁查詢是否有效角色
	 * 
	 * @param inquiry
	 * @param valid
	 * @return
	 */
	List<RolePo> findRole(Inquiry inquiry, boolean valid);

	/**
	 * 查詢是否有效角色
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	List<RolePo> findRole(Locale locale, boolean valid);

	/**
	 * 分頁查詢是否有效角色
	 * 
	 * @param inquiry
	 * @param locale
	 * @param valid
	 * @return
	 */
	List<RolePo> findRole(Inquiry inquiry, Locale locale, boolean valid);

}
