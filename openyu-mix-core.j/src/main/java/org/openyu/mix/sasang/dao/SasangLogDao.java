package org.openyu.mix.sasang.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.sasang.log.SasangFamousLog;
import org.openyu.mix.sasang.log.SasangPlayLog;
import org.openyu.mix.sasang.log.SasangPutLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface SasangLogDao extends AppLogDao
{
	// --------------------------------------------------
	// SasangPlayLog
	// --------------------------------------------------
	/**
	 * 查詢四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangPlayLog> findSasangPlayLog(String roleId);

	/**
	 * 分頁查詢四象玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangPlayLog> findSasangPlayLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangPlayLog(String roleId);

	// --------------------------------------------------
	// SasangPutLog
	// --------------------------------------------------
	/**
	 * 查詢四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangPutLog> findSasangPutLog(String roleId);

	/**
	 * 分頁查詢四象放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangPutLog> findSasangPutLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangPutLog(String roleId);

	// --------------------------------------------------
	// SasangFamousLog
	// --------------------------------------------------
	/**
	 * 查詢四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<SasangFamousLog> findSasangFamousLog(String roleId);

	/**
	 * 分頁查詢四象放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<SasangFamousLog> findSasangFamousLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteSasangFamousLog(String roleId);
}
