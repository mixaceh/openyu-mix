package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.openyu.mix.flutter.vo.AttributeType;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.enumz.IntEnum;

public class AttributeTypeTest {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void values() {
		for (AttributeType entry : AttributeType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(AttributeType.values().length > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void checkDuplicate() {
		List<AttributeType> result = null;
		result = EnumHelper.checkDuplicate(AttributeType.class);

		System.out.println(result);// 空集合表示沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void valueOf() {
		AttributeType result = null;
		result = EnumHelper.valueOf(AttributeType.class, 1);

		System.out.println(result + ", " + result.getValue());
		assertEquals(AttributeType.HEALTH, result);
		//
		result = AttributeType.valueOf("MaxHealthPoint");
		System.out.println(result + ", " + result.getValue());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 0, concurrency = 100)
	public void sumOf() {
		List<AttributeType> list = new LinkedList<AttributeType>();
		list.add(AttributeType.HEALTH);
		list.add(AttributeType.MAX_HEALTH);
		list.add(AttributeType.MANA);
		list.add(AttributeType.MAX_MANA);
		//
		double result = 0;
		result = EnumHelper.sumOf(list);

		System.out.println(result);
		assertEquals(0, Double.compare(50, result));

		AttributeType attributeType = AttributeType.MANA;
		IntEnum intEnum = (IntEnum) attributeType;
		System.out.println(intEnum instanceof IntEnum);
	}
}
