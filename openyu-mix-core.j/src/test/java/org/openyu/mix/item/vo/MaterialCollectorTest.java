package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.mix.item.vo.impl.MaterialImpl;
import org.openyu.commons.junit.supporter.BeanTestSupporter;

public class MaterialCollectorTest extends BeanTestSupporter {

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	public void writeToXml() {
		String result = null;
		MaterialCollector collector = MaterialCollector.getInstance(false);
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			// 布質類
			Material material = new MaterialImpl("M_COTTON_G001");// M_名稱
			material.setName("棉布");
			material.setMaterialType(MaterialType.CLOTH);
			material.setPrice(5000L);// 0.5w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			// 木質類
			material = new MaterialImpl("M_OAK_G001");
			material.setName("橡樹木板");
			material.setMaterialType(MaterialType.WOOD);
			material.setPrice(5000L);// 0.5w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			// 礦石類
			material = new MaterialImpl("M_GOLD_ORE_G001");
			material.setName("金礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(1 * 10000L);// 1w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			material = new MaterialImpl("M_SILVER_ORE_G001");
			material.setName("銀礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(8000L);// 0.8w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			material = new MaterialImpl("M_COPPER_ORE_G001");
			material.setName("銅礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(5000L);// 0.5w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			material = new MaterialImpl("M_IRON_ORE_G001");
			material.setName("鐵礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(3000L);// 0.3w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			material = new MaterialImpl("M_DIAMOND_ORE_G001");
			material.setName("鑽石礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(2 * 10000L);// 2w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			material = new MaterialImpl("M_CRYSTAL_ORE_G001");
			material.setName("水晶礦石");
			material.setMaterialType(MaterialType.ORE);
			material.setPrice(15000L);// 1.5w
			material.setCoin(1);
			collector.getMaterials().put(material.getId(), material);
			//
			result = collector.writeToXml(MaterialCollector.class, collector);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 1872 mills.
	// 100 times: 1786 mills.
	// 100 times: 1832 mills.
	public void readFromXml() {
		MaterialCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromXml(MaterialCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void writeToSerFromXml() {
		String result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = beanCollector.writeToSerFromXml(MaterialCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 100 times: 465 mills.
	// 100 times: 474 mills.
	// 100 times: 495 mills.
	public void readFromSer() {
		MaterialCollector result = null;
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = beanCollector.readFromSer(MaterialCollector.class);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 1000000 times: 399 mills.
	// 1000000 times: 398 mills.
	// 1000000 times: 401 mills.
	public void initialize() {
		boolean result = false;
		//
		int count = 1000000;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			MaterialCollector.getInstance().initialize();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		result = MaterialCollector.getInstance().isInitialized();
		System.out.println(result);
		assertTrue(result);
		//
		System.out.println(MaterialCollector.getInstance());
	}

	@Test
	// 1000000 times: 400 mills.
	// 1000000 times: 396 mills.
	// 1000000 times: 399 mills.
	public void getInstance() {
		MaterialCollector result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = MaterialCollector.getInstance();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	// 10000 times: 1587 mills.
	// 10000 times: 1583 mills.
	// 10000 times: 1683 mills.
	public void reset() {
		MaterialCollector collector = MaterialCollector.getInstance();
		//
		int count = 1;
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			collector.reset();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		assertFalse(collector.isInitialized());
		//
		assertEquals(0, collector.getMaterials().size());
	}

	@Test
	// 1000000 times: 396 mills.
	// 1000000 times: 393 mills.
	// 1000000 times: 434 mills.
	// verified
	public void getMaterialTypes() {
		Set<MaterialType> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = MaterialCollector.getInstance().getMaterialTypes();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	// 1000000 times: 443 mills.
	// 1000000 times: 400 mills.
	// 1000000 times: 403 mills.
	// verified
	public void getMaterials() {
		Map<String, Material> result = null;
		//
		int count = 1000000; // 100w
		long beg = System.currentTimeMillis();
		//
		for (int i = 0; i < count; i++) {
			result = MaterialCollector.getInstance().getMaterials();
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

}
