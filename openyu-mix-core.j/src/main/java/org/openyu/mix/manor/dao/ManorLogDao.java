package org.openyu.mix.manor.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.manor.log.ManorLandLog;
import org.openyu.mix.manor.log.ManorSeedLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface ManorLogDao extends AppLogDao
{
	// --------------------------------------------------
	// ManorLandLog
	// --------------------------------------------------
	/**
	 * 查詢莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ManorLandLog> findManorLandLog(String roleId);

	/**
	 * 分頁查詢莊園土地log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ManorLandLog> findManorLandLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除莊園土地log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteManorLandLog(String roleId);

	// --------------------------------------------------
	// ManorSeedLog
	// --------------------------------------------------
	/**
	 * 查詢莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	List<ManorSeedLog> findManorSeedLog(String roleId);

	/**
	 * 分頁查詢莊園種子log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<ManorSeedLog> findManorSeedLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除莊園種子log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteManorSeedLog(String roleId);

}
