package org.openyu.mix.core.service.impl;

import org.openyu.commons.quartz.supporter.BaseJobSupporter;
import org.openyu.mix.core.service.CoreService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 排程每日00:00執行
 */
public class Day00_03Job extends BaseJobSupporter {

	@Autowired
	@Qualifier("coreService")
	private transient CoreService coreService;

	public Day00_03Job() {

	}

	protected void doExecute(JobExecutionContext jobExecutionContext) throws Exception {
		coreService.day00_03();
	}
}
