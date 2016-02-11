package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.socklet.bootstrap.server.AcceptorBootstrap;

public class MasterBootstrapTest {

	@Test
	public void main() {
		AcceptorBootstrap.main(new String[] { "org/openyu/mix/bootstrap/server/applicationContext-master.xml" });
	}

}
