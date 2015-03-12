package org.openyu.mix.core.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class CoreModuleTypeTest extends BaseTestSupporter
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
		for (CoreModuleType entry : CoreModuleType.values())
		{
			System.out.println(entry + ", " + entry.getValue());
		}
		assertTrue(CoreModuleType.values().length > 0);
	}

	@Test
	//1000000 times: 261 mills. 
	//1000000 times: 259 mills. 
	//1000000 times: 252 mills. 
	//verified	
	public void unique()
	{
		List<CoreModuleType> result = null;
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = EnumHelper.checkUnique(CoreModuleType.class);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);//null表沒有重複,若有重複,則傳會重複的值
		assertTrue(result.size() == 0);
	}
}
