package org.openyu.mix.activity.vo;

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

import org.openyu.mix.activity.vo.ActivityType;
import org.openyu.mix.activity.vo.adapter.ActivityTypeXmlAdapter;
import org.openyu.mix.activity.vo.adapter.StringActivityXmlAdapter;
import org.openyu.commons.collector.CollectorHelper;
import org.openyu.commons.collector.supporter.BaseCollectorSupporter;
import org.openyu.commons.enumz.EnumHelper;

/**
 * 1.處理靜態資料,放的是vo的資料,並非po資料
 * 
 */
// --------------------------------------------------
// jaxb
// --------------------------------------------------
@XmlRootElement(name = "activityCollector")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ActivityCollector extends BaseCollectorSupporter {

	private static final long serialVersionUID = 8426904646201490231L;

	private static ActivityCollector instance;
	// --------------------------------------------------
	// 此有系統值,只是為了轉出xml,並非給企劃編輯用
	// --------------------------------------------------
	/**
	 * 活動類別
	 */
	@XmlJavaTypeAdapter(ActivityTypeXmlAdapter.class)
	private Set<ActivityType> activityTypes = new LinkedHashSet<ActivityType>();

	// --------------------------------------------------
	// biz
	// --------------------------------------------------
	/**
	 * 所有活動 <id,activity>
	 */
	@XmlJavaTypeAdapter(StringActivityXmlAdapter.class)
	private Map<String, Activity> activitys = new LinkedHashMap<String, Activity>();

	// --------------------------------------------------

	public ActivityCollector() {
		setId(ActivityCollector.class.getName());
	}

	public synchronized static ActivityCollector getInstance() {
		return getInstance(true);
	}

	public synchronized static ActivityCollector getInstance(boolean start) {
		if (instance == null) {
			instance = CollectorHelper.readFromSer(ActivityCollector.class);
			// 此時有可能會沒有ser檔案,或舊的結構造成ex,只要再轉出一次ser,覆蓋原本ser即可
			if (instance == null) {
				instance = new ActivityCollector();
			}
			//
			if (start) {
				// 啟動
				instance.start();
			}
			// 此有系統值,只是為了轉出xml,並非給企劃編輯用
			instance.activityTypes = EnumHelper.valuesSet(ActivityType.class);
		}
		return instance;
	}

	/**
	 * 單例關閉
	 * 
	 * @return
	 */
	public synchronized static ActivityCollector shutdownInstance() {
		if (instance != null) {
			ActivityCollector oldInstance = instance;
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
	public synchronized static ActivityCollector restartInstance() {
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
		instance.activitys.clear();
	}

	// --------------------------------------------------
	// --------------------------------------------------

	public Set<ActivityType> getActivityTypes() {
		if (activityTypes == null) {
			activityTypes = new LinkedHashSet<ActivityType>();
		}
		return activityTypes;
	}

	public void setActivityTypes(Set<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}

	public Map<String, Activity> getActivitys() {
		if (activitys == null) {
			activitys = new LinkedHashMap<String, Activity>();
		}
		return activitys;
	}

	public void setActivitys(Map<String, Activity> activitys) {
		this.activitys = activitys;
	}

	// --------------------------------------------------
	/**
	 * 取得活動,不clone了,直接拿
	 * 
	 * @param id
	 * @return
	 */
	public Activity getActivity(String id) {
		Activity result = null;
		if (id != null) {
			result = activitys.get(id);
		}
		// return (result != null ? (Activity) result.clone() : null);
		return result;
	}

	/**
	 * 依活動類別取得活動集合,不clone了,直接拿
	 * 
	 * @param activityType
	 * @return
	 */
	public List<Activity> getActivitys(ActivityType activityType) {
		List<Activity> result = new LinkedList<Activity>();
		if (activityType != null) {
			for (Activity activity : activitys.values()) {
				if (activityType.equals(activity.getActivityType())) {
					// result.add(getActivity(activity.getId()));
					result.add(activity);
				}
			}
		}
		return result;
	}

	/**
	 * 活動是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean containActivity(String id) {
		boolean result = false;
		if (id != null) {
			result = activitys.containsKey(id);
		}
		return result;
	}

	/**
	 * 活動是否存在
	 * 
	 * @param activity
	 * @return
	 */
	public boolean containActivity(Activity activity) {
		boolean result = false;
		if (activity != null) {
			result = activitys.containsKey(activity.getId());
		}
		return result;
	}

	/**
	 * 取得所有活動id
	 * 
	 * @return
	 */
	public List<String> getActivityIds() {
		List<String> result = new LinkedList<String>();
		for (String id : activitys.keySet()) {
			result.add(id);
		}
		return result;
	}

	/**
	 * 動態加載只是為了測試用,非測試不用
	 * 
	 * @param newActivitys
	 */
	protected <T extends Activity> void addActivitys(List<T> newActivitys) {
		if (newActivitys != null) {
			for (Activity activity : newActivitys) {
				if (activity != null) {
					activitys.put(activity.getId(), activity);
				}
			}
		}
	}
}
