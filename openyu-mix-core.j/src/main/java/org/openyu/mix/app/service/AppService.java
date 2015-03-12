package org.openyu.mix.app.service;

import org.openyu.commons.service.OjService;

/**
 * 應用服務
 */
public interface AppService extends OjService, RoleConnectable
{
	/**
	 * 版本,每次上傳svn,加1
	 */
	int VERSION = 1;
}
