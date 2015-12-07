package org.openyu.mix.bootstrap.server;

import org.junit.Test;

import org.openyu.socklet.bootstrap.server.ServerBootstrapBak;

public class Slave2BootstrapTest {

	@Test
	public void main() {
		ServerBootstrapBak
				.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-slave2.xml" });
	}

}
