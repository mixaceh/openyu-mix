package org.openyu.mix.login.service;

import org.openyu.commons.service.BaseService;

public interface LoginService extends BaseService
{
	void authorize(String accountId, String authKey, String ip);
}
