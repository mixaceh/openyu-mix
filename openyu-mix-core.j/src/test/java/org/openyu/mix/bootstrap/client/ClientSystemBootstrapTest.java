package org.openyu.mix.bootstrap.client;

import org.junit.Test;
import org.openyu.commons.thread.ThreadHelper;
import org.openyu.socklet.bootstrap.client.ClientBootstrap;

public class ClientSystemBootstrapTest {

	@Test
	public void main() {
		ClientBootstrap.main(new String[] { "org/openyu/mix/bootstrap/client/applicationContext-client-system.xml" });
		//
		if (ClientBootstrap.isStarted()) {
			ThreadHelper.loop(50);
		}
	}

}
