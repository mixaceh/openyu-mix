package org.openyu.mix.bootstrap.server;

import org.junit.Test;

import org.openyu.socklet.bootstrap.server.StandaloneBootstrapBak;

public class StandaloneBootstrapTest {

	@Test
	public void main() {
		StandaloneBootstrapBak
				.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-slave1.xml" });
	}

}
