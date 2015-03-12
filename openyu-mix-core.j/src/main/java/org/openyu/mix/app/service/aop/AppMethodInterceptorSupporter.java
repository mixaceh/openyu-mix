package org.openyu.mix.app.service.aop;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openyu.mix.app.service.AppMethodInterceptor;
import org.openyu.commons.aop.supporter.BaseMethodInterceptorSupporter;

public abstract class AppMethodInterceptorSupporter extends BaseMethodInterceptorSupporter
		implements AppMethodInterceptor
{
	private static transient final Logger log = LogManager
			.getLogger(AppMethodInterceptorSupporter.class);

	public AppMethodInterceptorSupporter()
	{}
}
