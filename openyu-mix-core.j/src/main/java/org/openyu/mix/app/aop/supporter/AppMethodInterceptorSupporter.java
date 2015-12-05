package org.openyu.mix.app.aop.supporter;

import org.openyu.commons.aop.supporter.BaseMethodInterceptorSupporter;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.anno.DefaultThreadService;
import org.openyu.mix.app.aop.AppMethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AppMethodInterceptorSupporter extends BaseMethodInterceptorSupporter
		implements AppMethodInterceptor {

	private static final long serialVersionUID = 1794539563167771771L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AppMethodInterceptorSupporter.class);
	/**
	 * 線程服務
	 */
	@DefaultThreadService
	protected transient ThreadService threadService;

	public AppMethodInterceptorSupporter() {
	}
}
