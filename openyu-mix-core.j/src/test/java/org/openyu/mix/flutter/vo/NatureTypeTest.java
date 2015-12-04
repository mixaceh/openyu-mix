package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class NatureTypeTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void values() {
		for (NatureType entry : NatureType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(NatureType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void checkDuplicate() {
		List<NatureType> result = null;
		result = EnumHelper.checkDuplicate(NatureType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void valueOf() {
		NatureType result = null;
		result = EnumHelper.valueOf(NatureType.class, 1);

		System.out.println(result + ", " + result.getValue());
		assertEquals(NatureType.HOLY, result);
		//
		result = NatureType.valueOf("HOLY");
		System.out.println(result + ", " + result.getValue());
		assertEquals(NatureType.HOLY, result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void sumOf() {
		List<NatureType> list = new LinkedList<NatureType>();
		list.add(NatureType.HOLY);
		list.add(NatureType.DARK);
		list.add(NatureType.WATER);
		list.add(NatureType.FIRE);
		//
		double result = 0;
		result = EnumHelper.sumOf(list);

		assertEquals(0, Double.compare(10, result));
	}
}
