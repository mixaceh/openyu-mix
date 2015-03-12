package org.openyu.mix.activity.service.impl;

import org.junit.Test;

import org.openyu.mix.activity.ActivityTestSupporter;

import org.openyu.mix.role.vo.Role;

public class ActivityServiceImplTest extends ActivityTestSupporter {

	@Test
	/**
	 * 連線
	 */
	public void connect() {
		Role role = mockRole();
		//
		activityService.roleConnect(role.getId(), null);
	}

}
