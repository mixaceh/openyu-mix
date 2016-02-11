package org.openyu.mix.core.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.openyu.mix.core.service.CoreMessageType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class CoreMessageTypeTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	// round: 1.29 [+- 0.28], round.block: 1.26 [+- 0.28], round.gc: 0.00 [+-
	// 0.00], GC.calls: 3, GC.time: 0.04, time.total: 1.63, time.warmup: 0.01,
	// time.bench: 1.62
	public void values() {
		for (CoreMessageType entry : CoreMessageType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(CoreMessageType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	// round: 2.07 [+- 0.06], round.block: 0.04 [+- 0.02], round.gc: 0.00 [+-
	// 0.00], GC.calls: 4, GC.time: 0.07, time.total: 2.17, time.warmup: 0.01,
	// time.bench: 2.17
	public void checkDuplicate() {
		List<CoreMessageType> result = null;
		result = EnumHelper.checkDuplicate(CoreMessageType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}
}
