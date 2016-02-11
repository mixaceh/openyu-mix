package org.openyu.mix.item.vo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.adapter.StringMaterialXmlAdapter;
import org.openyu.mix.item.vo.adapter.MaterialTypeXmlAdapter;
import org.openyu.mix.item.vo.impl.MaterialImpl;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;
import org.openyu.commons.lang.StringHelper;

/**
 * 材料數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "materialCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class MaterialCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 1507901194525580302L;

	private static MaterialCollector instance;

	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
	/**
	 * 材料類別
	 */
	@XmlJavaTypeAdapter(MaterialTypeXmlAdapter.class)
	private Set<MaterialType> materialTypes = new LinkedHashSet<MaterialType>();

	// --------------------------------------------------
	// 企劃編輯用
	// --------------------------------------------------
	/**
	 * 所有材料
	 */
	@XmlJavaTypeAdapter(StringMaterialXmlAdapter.class)
	private Map<String, Material> materials = new LinkedHashMap<String, Material>();

	// --------------------------------------------------

	public MaterialCollector() {
		setId(MaterialCollector.class.getName());
	}

	public synchronized static MaterialCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static MaterialCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(MaterialCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new MaterialCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.materialTypes = EnumHelper.valuesSet(MaterialType.class);

		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static MaterialCollector shutdownInstance() {
		if (instance != null) {
			MaterialCollector oldInstance = instance;
			instance = null;
			//
			if (oldInstance != null) {
				oldInstance.shutdown();
			}
		}
		return instance;
	}

	/**
	 * 單例重啟
	 * 
	 * @return
	 */
	public synchronized static MaterialCollector restartInstance() {
		if (instance != null) {
			instance.restart();
		}
		return instance;
	}

	/**
	 * 內部啟動
	 */
	@Override
	protected void doStart() throws Exception {

	}

	/**
	 * 內部關閉
	 */
	@Override
	protected void doShutdown() throws Exception {
		instance.materials.clear();
	}

	// --------------------------------------------------

	public Set<MaterialType> getMaterialTypes() {
		if (materialTypes == null) {
			materialTypes = new LinkedHashSet<MaterialType>();
		}
		return materialTypes;
	}

	public void setMaterialTypes(Set<MaterialType> materialTypes) {
		this.materialTypes = materialTypes;
	}

	public Map<String, Material> getMaterials() {
		if (materials == null) {
			materials = new LinkedHashMap<String, Material>();
		}
		return materials;
	}

	public void setMaterials(Map<String, Material> materials) {
		this.materials = materials;
	}

	// --------------------------------------------------
	/**
	 * 取得clone材料
	 * 
	 * @param id
	 * @return
	 */
	public Material getMaterial(String id) {
		Material result = null;
		if (id != null) {
			result = materials.get(id);
		}
		return (result != null ? (Material) result.clone() : null);
	}

	public Material createMaterial() {
		return createMaterial(null);
	}

	/**
	 * 
	 * 依照靜態資料,建構新的material,使用clone/或用建構new xxx()
	 * 
	 * @param id
	 * @return
	 */
	public Material createMaterial(String id) {
		Material result = null;
		// 來自靜態資料的clone副本
		result = getMaterial(id);
		// 若無靜態資料,則直接建構
		if (result == null) {
			result = new MaterialImpl();
			result.setId(id);
		}
		result.setUniqueId(Material.UNIQUE_ID_PREFIX + StringHelper.randomUnique());
		return result;
	}

	/**
	 * 依材料類別取得clone材料集合
	 * 
	 * @param materialType
	 * @return
	 */
	public List<Material> getMaterials(MaterialType materialType) {
		List<Material> result = new LinkedList<Material>();
		if (materialType != null) {
			for (Material material : materials.values()) {
				if (materialType.equals(material.getMaterialType())) {
					result.add(getMaterial(material.getId()));
				}
			}
		}
		return result;
	}

	/**
	 * 材料是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean containMaterial(String id) {
		boolean result = false;
		if (id != null) {
			result = materials.containsKey(id);
		}
		return result;
	}

	/**
	 * 材料是否存在
	 * 
	 * @param material
	 * @return
	 */
	public boolean containMaterial(Material material) {
		boolean result = false;
		if (material != null) {
			result = materials.containsKey(material.getId());
		}
		return result;
	}

	/**
	 * 取得所有材料id
	 * 
	 * @return
	 */
	public List<String> getMaterialIds() {
		List<String> result = new LinkedList<String>();
		for (String id : materials.keySet()) {
			result.add(id);
		}
		return result;
	}

}
