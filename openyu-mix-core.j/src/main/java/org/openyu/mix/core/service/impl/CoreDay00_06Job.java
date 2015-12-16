package org.openyu.mix.core.service.impl;

import org.openyu.commons.quartz.supporter.BaseJobSupporter;
import org.openyu.mix.core.service.CoreService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Core每日00:06執行
 */
public class CoreDay00_06Job extends BaseJobSupporter {

	@Autowired
	@Qualifier("coreService")
	private transient CoreService coreService;

	public CoreDay00_06Job() {

	}

	protected void doExecute(JobExecutionContext jobExecutionContext) throws Exception {
		coreService.day00_06();
	}
}
