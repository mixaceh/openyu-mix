package org.openyu.mix.bootstrap.client;

import org.junit.Test;
import org.openyu.socklet.bootstrap.client.ClientBootstrap;

public class Slave3ClientBootstrapTest {

	@Test
	public void main() {
		ClientBootstrap
				.main(new String[] { "org/openyu/mix/bootstrap/client/applicationContext-slave3-client.xml" });
	}

}
