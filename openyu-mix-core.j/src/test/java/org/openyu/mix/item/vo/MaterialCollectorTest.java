package org.openyu.mix.item.vo;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.mix.item.vo.MaterialType;
import org.openyu.mix.item.vo.impl.MaterialImpl;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class MaterialCollectorTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式xml後,不應再使用,以免覆蓋掉正式的xml
	 */
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToXml() {
		String result = null;
		MaterialCollector collector = MaterialCollector.getInstance(false);
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
		result = CollectorHelper.writeToXml(MaterialCollector.class, collector);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromXml() {
		MaterialCollector result = null;
		//
		result = CollectorHelper.readFromXml(MaterialCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToSerFromXml() {
		String result = null;
		//
		result = CollectorHelper.writeToSerFromXml(MaterialCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void readFromSer() {
		MaterialCollector result = null;
		//
		result = CollectorHelper.readFromSer(MaterialCollector.class);
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getInstance() {
		MaterialCollector result = null;
		//
		result = MaterialCollector.getInstance();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void shutdownInstance() {
		MaterialCollector instance = MaterialCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = MaterialCollector.shutdownInstance();
		assertNull(instance);
		// 多次,不會丟出ex
		instance = MaterialCollector.shutdownInstance();
		assertNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void restartInstance() {
		MaterialCollector instance = MaterialCollector.getInstance();
		System.out.println(instance);
		assertNotNull(instance);
		//
		instance = MaterialCollector.restartInstance();
		assertNotNull(instance);
		// 多次,不會丟出ex
		instance = MaterialCollector.restartInstance();
		assertNotNull(instance);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getMaterialTypes() {
		Set<MaterialType> result = null;
		//
		result = MaterialCollector.getInstance().getMaterialTypes();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void getMaterials() {
		Map<String, Material> result = null;
		//
		result = MaterialCollector.getInstance().getMaterials();
		//
		System.out.println(result);
		assertTrue(result.size() > 0);
	}
}
