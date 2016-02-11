package org.openyu.mix.wuxing.dao;

import java.util.List;

import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.wuxing.log.WuxingFamousLog;
import org.openyu.mix.wuxing.log.WuxingPlayLog;
import org.openyu.mix.wuxing.log.WuxingPutLog;
import org.openyu.commons.dao.inquiry.Inquiry;

public interface WuxingLogDao extends AppLogDao
{
	// --------------------------------------------------
	// WuxingPlayLog
	// --------------------------------------------------
	/**
	 * 查詢四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingPlayLog> findWuxingPlayLog(String roleId);

	/**
	 * 分頁查詢四象玩的log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingPlayLog> findWuxingPlayLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象玩的log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingPlayLog(String roleId);

	// --------------------------------------------------
	// WuxingPutLog
	// --------------------------------------------------
	/**
	 * 查詢四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingPutLog> findWuxingPutLog(String roleId);

	/**
	 * 分頁查詢四象放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingPutLog> findWuxingPutLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingPutLog(String roleId);

	// --------------------------------------------------
	// WuxingFamousLog
	// --------------------------------------------------
	/**
	 * 查詢四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	List<WuxingFamousLog> findWuxingFamousLog(String roleId);

	/**
	 * 分頁查詢四象放入log
	 * 
	 * @param inquiry
	 * @param roleId
	 * @return
	 */
	List<WuxingFamousLog> findWuxingFamousLog(Inquiry inquiry, String roleId);

	/**
	 * 刪除四象放入log
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteWuxingFamousLog(String roleId);
}
