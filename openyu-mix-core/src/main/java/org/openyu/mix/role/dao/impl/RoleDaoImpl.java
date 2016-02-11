package org.openyu.mix.role.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openyu.mix.app.dao.supporter.AppDaoSupporter;
import org.openyu.mix.role.dao.RoleDao;
import org.openyu.mix.role.po.RolePo;
import org.openyu.mix.role.po.impl.RolePoImpl;
import org.openyu.commons.dao.inquiry.Inquiry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleDaoImpl extends AppDaoSupporter implements RoleDao {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(RoleDaoImpl.class);

	private static final String ROLE_PO_NAME = RolePoImpl.class.getName();

	public RoleDaoImpl() {
	}

	/**
	 * 查詢角色
	 * 
	 * @param roleId
	 * @return
	 */
	public RolePo findRole(String roleId) {
		return findRole(null, roleId);
	}

	/**
	 * 查詢角色
	 * 
	 * @param locale
	 * @param roleId
	 * @return
	 */
	public RolePo findRole(Locale locale, String roleId) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ROLE_PO_NAME + " ");
		hql.append("where 1=1 ");

		// id
		hql.append("and id = :id ");
		params.put("id", roleId);
		//
		return findUniqueByHql(locale, hql.toString(), params);
	}

	/**
	 * 查詢是否有效角色
	 * 
	 * @param valid
	 * @return
	 */
	public List<RolePo> findRole(boolean valid) {
		return findRole(null, null, valid);
	}

	/**
	 * 分頁查詢是否有效角色
	 * 
	 * @param inquiry
	 * @param valid
	 * @return
	 */
	public List<RolePo> findRole(Inquiry inquiry, boolean valid) {
		return findRole(inquiry, null, valid);
	}

	/**
	 * 查詢是否有效角色
	 * 
	 * @param locale
	 * @param valid
	 * @return
	 */
	public List<RolePo> findRole(Locale locale, boolean valid) {
		return findRole(null, locale, valid);
	}

	/**
	 * 分頁查詢是否有效角色
	 * 
	 * @param inquiry
	 * @param locale
	 * @param valid
	 * @return
	 */
	public List<RolePo> findRole(Inquiry inquiry, Locale locale, boolean valid) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		//
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(ROLE_PO_NAME + " ");
		hql.append("where 1=1 ");

		// valid
		if (valid) {
			hql.append("and valid=:valid ");
		} else {
			hql.append("and (valid=:valid ");
			hql.append("or valid is null) ");
		}
		params.put("valid", valid);
		//
		return findByHql(inquiry, locale, hql, params);
	}

}
