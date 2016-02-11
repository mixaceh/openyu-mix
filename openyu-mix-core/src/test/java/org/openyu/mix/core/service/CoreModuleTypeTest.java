package org.openyu.mix.core.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.openyu.mix.core.service.CoreModuleType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class CoreModuleTypeTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	// round: 0.28 [+- 0.09], round.block: 0.28 [+- 0.09], round.gc: 0.00 [+-
	// 0.00], GC.calls: 3, GC.time: 0.04, time.total: 0.41, time.warmup: 0.01,
	// time.bench: 0.40
	public void values() {
		for (CoreModuleType entry : CoreModuleType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(CoreModuleType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void checkDuplicate() {
		List<CoreModuleType> result = null;
		result = EnumHelper.checkDuplicate(CoreModuleType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}
}
