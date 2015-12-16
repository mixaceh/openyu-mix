package org.openyu.mix.job;

import org.openyu.mix.app.job.supporter.AppJobSupporter;
import org.openyu.mix.sasang.service.SasangService;
import org.openyu.mix.train.service.TrainService;
import org.openyu.mix.wuxing.service.WuxingService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 每日00:00執行
 */
public class Day_00_00Job extends AppJobSupporter {

	@Autowired
	@Qualifier("trainService")
	protected transient TrainService trainService;

	@Autowired
	@Qualifier("sasangService")
	protected transient SasangService sasangService;

	@Autowired
	@Qualifier("wuxingService")
	protected transient WuxingService wuxingService;

	public Day_00_00Job() {

	}

	protected void doExecute(JobExecutionContext jobExecutionContext) throws Exception {
		// 訓練,重置每日可訓練毫秒限制,所有線上玩家
		trainService.reset(true);
		// 四象,重置每日已玩的次數,所有線上玩家
		sasangService.reset(true);
		// 五行,重置每日已玩的次數,所有線上玩家
		wuxingService.reset(true);
	}
}
