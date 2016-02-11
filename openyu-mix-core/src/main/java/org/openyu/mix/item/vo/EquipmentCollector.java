package org.openyu.mix.item.vo;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.item.vo.adapter.EquipmentLevelTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentPositionTypeXmlAdapter;
import org.openyu.mix.item.vo.adapter.EquipmentSeriesTypeXmlAdapter;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;

/**
 * 裝備數據
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "equipmentCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class EquipmentCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = -8177863933077620473L;

	private static EquipmentCollector instance;
	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
	/**
	 * 等級類別
	 */
	@XmlJavaTypeAdapter(EquipmentLevelTypeXmlAdapter.class)
	private Set<LevelType> levelTypes = new LinkedHashSet<LevelType>();

	/**
	 * 位置類別
	 */
	@XmlJavaTypeAdapter(EquipmentPositionTypeXmlAdapter.class)
	private Set<PositionType> positionTypes = new LinkedHashSet<PositionType>();

	/**
	 * 系列類別
	 */
	@XmlJavaTypeAdapter(EquipmentSeriesTypeXmlAdapter.class)
	private Set<SeriesType> seriesTypes = new LinkedHashSet<SeriesType>();

	// --------------------------------------------------
	// 企劃編輯用
	// --------------------------------------------------
	
	
	// --------------------------------------------------
	
	public EquipmentCollector() {
		setId(EquipmentCollector.class.getName());
	}

	public synchronized static EquipmentCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static EquipmentCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(EquipmentCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new EquipmentCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.levelTypes = new LinkedHashSet<LevelType>(Arrays.asList(LevelType.values()));
			instance.positionTypes = new LinkedHashSet<PositionType>(Arrays.asList(PositionType.values()));
			instance.seriesTypes = new LinkedHashSet<SeriesType>(Arrays.asList(SeriesType.values()));
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static EquipmentCollector shutdownInstance() {
		if (instance != null) {
			EquipmentCollector oldInstance = instance;
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
	public synchronized static EquipmentCollector restartInstance() {
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

	}

	// --------------------------------------------------

	/**
	 * 取得等級類別
	 * 
	 * @return
	 */
	public Set<LevelType> getLevelTypes() {
		if (levelTypes == null) {
			levelTypes = new LinkedHashSet<LevelType>();
		}
		return levelTypes;
	}

	public void setLevelTypes(Set<LevelType> levelTypes) {
		this.levelTypes = levelTypes;
	}

	/**
	 * 取得位置類別
	 * 
	 * @return
	 */
	public Set<PositionType> getPositionTypes() {
		if (positionTypes == null) {
			positionTypes = new LinkedHashSet<PositionType>();
		}
		return positionTypes;
	}

	public void setPositionTypes(Set<PositionType> positionTypes) {
		this.positionTypes = positionTypes;
	}

	/**
	 * 取得系列類別
	 * 
	 * @return
	 */
	public Set<SeriesType> getSeriesTypes() {
		if (seriesTypes == null) {
			seriesTypes = new LinkedHashSet<SeriesType>();
		}
		return seriesTypes;
	}

	public void setSeriesTypes(Set<SeriesType> seriesTypes) {
		this.seriesTypes = seriesTypes;
	}

}
