package org.openyu.mix.app.service.aop.supporter;

import org.openyu.mix.app.service.aop.AppMethodInterceptor;
import org.openyu.commons.aop.supporter.BaseMethodInterceptorSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AppMethodInterceptorSupporter extends
		BaseMethodInterceptorSupporter implements AppMethodInterceptor {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(AppMethodInterceptorSupporter.class);

	public AppMethodInterceptorSupporter() {
	}
}
