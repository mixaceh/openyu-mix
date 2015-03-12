package org.openyu.mix.bootstrap.client;

import org.junit.Test;
import org.openyu.socklet.bootstrap.client.ClientBootstrap;

public class SystemClientBootstrapTest {

	@Test
	public void main() {
		ClientBootstrap
				.main(new String[] { "org/openyu/mix/bootstrap/client/applicationContext-system-client.xml" });
	}

}
