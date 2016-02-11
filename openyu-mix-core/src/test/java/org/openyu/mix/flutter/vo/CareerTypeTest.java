package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.openyu.mix.flutter.vo.CareerType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.enumz.EnumHelper;

public class CareerTypeTest {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void values() {
		for (CareerType entry : CareerType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(CareerType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void checkDuplicate() {
		List<CareerType> result = null;
		result = EnumHelper.checkDuplicate(CareerType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void valueOf() {
		CareerType result = null;
		result = EnumHelper.valueOf(CareerType.class, 1);

		System.out.println(result + ", " + result.getValue());
		assertEquals(CareerType.WARRIOR_1, result);
		//
		result = CareerType.valueOf("Archer");
		System.out.println(result + ", " + result.getValue());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void sumOf() {
		List<CareerType> list = new LinkedList<CareerType>();
		list.add(CareerType.WARRIOR_1);
		list.add(CareerType.ARCHER_1);
		//
		double result = 0;
		result = EnumHelper.sumOf(list);

		System.out.println(result);
		assertEquals(0, Double.compare(12, result));
	}
}
