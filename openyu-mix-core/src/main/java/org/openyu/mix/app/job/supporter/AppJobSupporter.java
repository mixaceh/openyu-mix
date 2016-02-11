package org.openyu.mix.app.job.supporter;

import org.openyu.commons.quartz.supporter.BaseJobSupporter;
import org.openyu.mix.app.job.AppJob;

public abstract class AppJobSupporter extends BaseJobSupporter implements AppJob {

	public AppJobSupporter() {

	}

}
