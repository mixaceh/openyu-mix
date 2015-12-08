package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.socklet.bootstrap.server.ServerBootstrap;

public class LoginBootstrapTest {

	@Test
	public void main() {
		ServerBootstrap.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-login.xml" });
	}

}
