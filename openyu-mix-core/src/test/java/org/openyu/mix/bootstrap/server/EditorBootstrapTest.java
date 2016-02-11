package org.openyu.mix.bootstrap.server;

import org.junit.Test;
import org.openyu.commons.bootstrap.EditorBootstrap;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class EditorBootstrapTest extends BaseTestSupporter {

	private static String[] configLocations = new String[] {
			"applicationContext-init.xml",//
			// "META-INF/applicationContext-commons-core-editor.xml",
			"META-INF/applicationContext-mix-core-editor.xml",//
	};

	@Test
	public void main() {
		//excel writeToXml
		EditorBootstrap.main(configLocations);
	}

}
