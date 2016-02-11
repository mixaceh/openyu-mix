package org.openyu.mix.util;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.util.ConfigHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

/**
 * 使用spring注入
 */
public class ConfigHelperSpringTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	private static ConfigHelper configHelper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext-init.xml",//
				});

		configHelper = (ConfigHelper) applicationContext
				.getBean("configHelper");
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	public void getter() {
		// src\test\config\data\conf\config.xml
		System.out.println(ConfigHelper.getConfigurationFile());
		//
		System.out.println(ConfigHelper.getJsonDir());
		System.out.println(ConfigHelper.getKeyDir());
		System.out.println(ConfigHelper.getSerDir());
		System.out.println(ConfigHelper.getXmlDir());
		System.out.println(ConfigHelper.getExcelDir());
		//
		System.out.println(ConfigHelper.getOutputDir());
		System.out.println(ConfigHelper.getDownloadDir());
		System.out.println(ConfigHelper.getUploadDir());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	// round: 0.00
	public void vipType() {
		Map<String, String> result = null;

		int count = 10000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = ConfigHelper.getMap("vipCollector.vipType");
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}
}
