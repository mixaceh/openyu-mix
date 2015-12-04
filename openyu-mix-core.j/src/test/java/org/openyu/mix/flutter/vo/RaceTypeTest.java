package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.openyu.mix.flutter.vo.RaceType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.enumz.EnumHelper;

public class RaceTypeTest {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void values() {
		for (RaceType entry : RaceType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(RaceType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void checkDuplicate() {
		List<RaceType> result = null;
		result = EnumHelper.checkDuplicate(RaceType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void valueOf() {
		RaceType result = null;
		result = EnumHelper.valueOf(RaceType.class, 1);

		System.out.println(result + ", " + result.getValue());
		assertEquals(RaceType.YUAN, result);
		//
		result = RaceType.valueOf("HUMAN");
		System.out.println(result + ", " + result.getValue());
		assertEquals(RaceType.YUAN, result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void sumOf() {
		List<RaceType> list = new LinkedList<RaceType>();
		list.add(RaceType.YUAN);
		list.add(RaceType.YI);
		list.add(RaceType.DI);
		list.add(RaceType.MAN);
		//
		double result = 0;
		result = EnumHelper.sumOf(list);

		System.out.println(result);

		assertEquals(0, Double.compare(13, result));

	}
}
