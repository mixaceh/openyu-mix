package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.socklet.bootstrap.server.ServerBootstrap;

public class AccountBootstrapTest {

	@Test
	public void main() {
		ServerBootstrap
				.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-account.xml" });
	}
}
