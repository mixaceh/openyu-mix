package org.openyu.mix.app.aop.supporter;

import org.openyu.commons.aop.supporter.BaseAspectSupporter;
import org.openyu.commons.thread.ThreadService;
import org.openyu.commons.thread.anno.DefaultThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppAspectSupporter extends BaseAspectSupporter {

	private static final long serialVersionUID = 2736748896785214592L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AppAspectSupporter.class);

	/**
	 * 線程服務
	 */
	@DefaultThreadService
	protected transient ThreadService threadService;

	public AppAspectSupporter() {

	}
}
