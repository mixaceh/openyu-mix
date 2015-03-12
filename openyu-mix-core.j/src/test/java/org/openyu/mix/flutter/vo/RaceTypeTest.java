package org.openyu.mix.flutter.vo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.commons.enumz.EnumHelper;

public class RaceTypeTest
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
		for (RaceType entry : RaceType.values())
		{
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(RaceType.values().length > 0);
	}

	@Test
	//1000000 times: 590 mills. 
	//1000000 times: 585 mills. 
	//1000000 times: 591 mills. 
	//verified	
	public void unique()
	{
		List<RaceType> result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.checkUnique(RaceType.class);
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
		RaceType result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.valueOf(RaceType.class, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result + ", " + result.getValue());
		assertEquals(RaceType.YUAN, result);
		//		
		result = RaceType.valueOf("HUMAN");
		System.out.println(result + ", " + result.getValue());
		assertEquals(RaceType.YUAN, result);
	}

	@Test
	//1000000 times: 120 mills. 
	//1000000 times: 114 mills. 
	//1000000 times: 120 mills. 
	//verified
	public void sumOf()
	{
		List<RaceType> list = new LinkedList<RaceType>();
		list.add(RaceType.YUAN);
		list.add(RaceType.YI);
		list.add(RaceType.DI);
		list.add(RaceType.MAN);
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

		assertEquals(0, Double.compare(13, result));
		
	}
}
