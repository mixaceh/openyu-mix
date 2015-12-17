package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.socklet.bootstrap.server.AcceptorBootstrap;


public class SystemBootstrapTest {

	@Test
	public void main() {
		AcceptorBootstrap
				.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-system.xml" });
	}

}
