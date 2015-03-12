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
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class NatureTypeTest extends BaseTestSupporter
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
		for (NatureType entry : NatureType.values())
		{
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(NatureType.values().length > 0);
	}

	@Test
	//1000000 times: 590 mills. 
	//1000000 times: 585 mills. 
	//1000000 times: 591 mills. 
	//verified	
	public void unique()
	{
		List<NatureType> result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.checkUnique(NatureType.class);
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
		NatureType result = null;
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.valueOf(NatureType.class, 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result + ", " + result.getValue());
		assertEquals(NatureType.HOLY, result);
		//		
		result = NatureType.valueOf("HOLY");
		System.out.println(result + ", " + result.getValue());
		assertEquals(NatureType.HOLY, result);
	}

	@Test
	//1000000 times: 120 mills. 
	//1000000 times: 114 mills. 
	//1000000 times: 120 mills. 
	//verified
	public void sumOf()
	{
		List<NatureType> list = new LinkedList<NatureType>();
		list.add(NatureType.HOLY);
		list.add(NatureType.DARK);
		list.add(NatureType.WATER);
		list.add(NatureType.FIRE);
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

		assertEquals(0, Double.compare(10, result));
	}
}
