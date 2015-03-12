package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.enumz.IntEnum;

public class AttributeTypeTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	//verified
	public void values()
	{
		for (AttributeType entry : AttributeType.values())
		{
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(AttributeType.values().length > 0);
	}

	@Test
	//1000000 times: 590 mills. 
	//1000000 times: 585 mills. 
	//1000000 times: 591 mills. 
	//verified	
	public void unique()
	{
		List<AttributeType> result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.checkUnique(AttributeType.class);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);//null表沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}

	@Test
	//1000000 times: 18 mills. 
	//1000000 times: 18 mills. 
	//1000000 times: 18 mills. 
	//verified
	public void valueOf()
	{
		AttributeType result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.valueOf(AttributeType.class, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result + ", " + result.getValue());
		assertEquals(AttributeType.HEALTH, result);
		//		
		result = AttributeType.valueOf("MaxHealthPoint");
		System.out.println(result + ", " + result.getValue());
	}

	@Test
	//1000000 times: 120 mills. 
	//1000000 times: 114 mills. 
	//1000000 times: 120 mills. 
	//verified
	public void sumOf()
	{
		List<AttributeType> list = new LinkedList<AttributeType>();
		list.add(AttributeType.HEALTH);
		list.add(AttributeType.MAX_HEALTH);
		list.add(AttributeType.MANA);
		list.add(AttributeType.MAX_MANA);
		//
		double result = 0;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.sumOf(list);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(0, Double.compare(50, result));

		AttributeType attributeType = AttributeType.MANA;
		IntEnum intEnum = (IntEnum) attributeType;
		System.out.println(intEnum instanceof IntEnum);
	}
}
