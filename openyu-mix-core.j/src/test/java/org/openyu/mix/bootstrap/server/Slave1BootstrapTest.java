package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.socklet.bootstrap.server.ServerBootstrap;

public class Slave1BootstrapTest {

	@Test
	public void main() {
		ServerBootstrap.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-slave1.xml" });
	}

}
