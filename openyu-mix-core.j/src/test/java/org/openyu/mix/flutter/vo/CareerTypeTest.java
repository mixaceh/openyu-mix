package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.commons.enumz.EnumHelper;

public class CareerTypeTest {

	@Test
	// verified
	public void values() {
		for (CareerType entry : CareerType.values()) {
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(CareerType.values().length > 0);
	}

	@Test
	// 1000000 times: 590 mills.
	// 1000000 times: 585 mills.
	// 1000000 times: 591 mills.
	// verified
	public void unique() {
		List<CareerType> result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = EnumHelper.checkUnique(CareerType.class);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);// null表沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	// 1000000 times: 18 mills.
	// 1000000 times: 18 mills.
	// 1000000 times: 18 mills.
	// verified
	public void valueOf() {
		CareerType result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = EnumHelper.valueOf(CareerType.class, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result + ", " + result.getValue());
		assertEquals(CareerType.WARRIOR_1, result);
		//
		result = CareerType.valueOf("Archer");
		System.out.println(result + ", " + result.getValue());
	}

	@Test
	// 1000000 times: 120 mills.
	// 1000000 times: 114 mills.
	// 1000000 times: 120 mills.
	// verified
	public void sumOf() {
		List<CareerType> list = new LinkedList<CareerType>();
		list.add(CareerType.WARRIOR_1);
		list.add(CareerType.ARCHER_1);
		//
		double result = 0;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = EnumHelper.sumOf(list);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(0, Double.compare(12, result));

	}
}
