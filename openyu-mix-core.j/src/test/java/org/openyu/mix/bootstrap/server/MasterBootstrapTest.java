package org.openyu.mix.bootstrap.server;

import org.junit.Test;

import org.openyu.socklet.bootstrap.server.ServerBootstrapBak;

public class MasterBootstrapTest {

	@Test
	public void main() {
		ServerBootstrapBak
				.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-master.xml" });
	}

}
