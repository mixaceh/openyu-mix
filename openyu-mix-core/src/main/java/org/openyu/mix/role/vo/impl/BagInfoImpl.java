package org.openyu.mix.role.vo.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openyu.mix.app.vo.supporter.AppInfoSupporter;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.item.vo.ItemType;
import org.openyu.mix.item.vo.adapter.IntegerItemXmlAdapter;
import org.openyu.mix.role.vo.BagInfo;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.adapter.IntegerTabXmlAdapter;
import org.openyu.commons.bean.supporter.BaseBeanSupporter;

//--------------------------------------------------
//jaxb
//--------------------------------------------------
@XmlRootElement(name = "bagInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class BagInfoImpl extends AppInfoSupporter implements BagInfo {

	private static final long serialVersionUID = -4684272767483660969L;

	/**
	 * 角色
	 */
	@XmlTransient
	private Role role;

	/**
	 * 包包頁
	 */
	@XmlJavaTypeAdapter(IntegerTabXmlAdapter.class)
	private Map<Integer, Tab> tabs = new LinkedHashMap<Integer, Tab>();

	public BagInfoImpl(Role role) {
		this.role = role;
		// 建構包包頁
		TabType[] tabTypes = BagInfo.TabType.values();
		for (TabType tabType : tabTypes) {
			// id=0,1,2,10
			int tabIndex = tabType.getValue();
			Tab tab = new TabImpl(tabIndex);
			// 只開放第1頁,任務頁,其他頁鎖定
			if (tabType == TabType._0 || tabType == TabType.QUEST) {
				tab.setLocked(false);
			} else {
				tab.setLocked(true);
			}
			addTab(tabIndex, tab);
		}
	}

	public BagInfoImpl() {
		this(null);
	}

	public Map<Integer, Tab> getTabs() {
		return tabs;
	}

	public void setTabs(Map<Integer, Tab> tabs) {
		this.tabs = tabs;
	}

	/**
	 * 包包頁數,已解鎖包包頁數量,如:3頁
	 * 
	 * @return
	 */
	public int getTabSize() {
		return getTabSize(false);
	}

	/**
	 * 包包頁數,已解鎖包包頁數量,如:3頁
	 * 
	 * @param ignoreLock
	 *            是否忽略鎖定
	 * @return
	 */
	public int getTabSize(boolean ignoreLocked) {
		int result = 0;
		TabType[] tabTypes = TabType.values();
		for (TabType tabType : tabTypes) {
			Tab tab = getTab(tabType, ignoreLocked);
			if (tab != null) {
				result += 1;
			}
		}
		return result;
	}

	/**
	 * 頁是否鎖定
	 * 
	 * @return
	 */
	public boolean isLocked(int index) {
		boolean result = true;
		Tab tab = getTab(index);
		if (tab != null) {
			result = tab.isLocked();
		}
		return result;
	}

	/**
	 * 鎖定包包頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType lock(int index) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(index);
			if (tab == null) {
				errorType = ErrorType.TAB_NOT_EXIST;
			} else {
				tab.setLocked(true);
			}
		}
		return errorType;
	}

	/**
	 * 解鎖包包頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType unLock(int index) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(index);
			if (tab == null) {
				errorType = ErrorType.TAB_NOT_EXIST;
			} else {
				tab.setLocked(false);
			}
		}
		return errorType;
	}

	/**
	 * 包包是否滿了
	 * 
	 * @return
	 */
	public boolean isFull() {
		boolean result = true;
		//
		TabType[] tabTypes = TabType.values();
		for (TabType tabType : tabTypes) {
			Tab tab = tabs.get(tabType.getValue());
			if (tab == null || !tab.isFull()) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * 任務頁
	 * 
	 * @return
	 */
	public Tab getQuestTab() {
		return getTab(TabType.QUEST.getValue());
	}

	public void setQuestTab(Tab tab) {
		setTab(TabType.QUEST.getValue(), tab);
	}

	/**
	 * 檢查是否超過包包頁索引
	 * 
	 * @param index
	 * @return
	 */
	protected ErrorType checkTabIndex(int index) {
		ErrorType errorType = ErrorType.NO_ERROR;
		if (!containTabType(index) || index < 0) {
			errorType = ErrorType.OVER_TAB_INDEX;
		}
		return errorType;
	}

	protected boolean containTabType(int index) {
		boolean result = false;
		TabType[] tabTypes = TabType.values();
		for (TabType entry : tabTypes) {
			if (entry.getValue() == index) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 加入包包頁
	 * 
	 * @param index
	 * @param tab
	 * @return
	 */
	public ErrorType addTab(int index, Tab tab) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			// 包包頁不存在
			if (tab == null) {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
			// 包包已有包包頁
			else if (containIndex(index)) {
				errorType = ErrorType.ALREADY_HAS_TAB;
			} else {
				tabs.put(index, tab);
			}
		}
		return errorType;
	}

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @return
	 */
	public Tab getTab(int index) {
		return getTab(index, false);
	}

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param index
	 * @param ignoreLock
	 *            是否忽略鎖定
	 * @return
	 */
	public Tab getTab(int index, boolean ignoreLocked) {
		Tab result = null;
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		//
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(index);
			if (tab != null) {
				// 檢查包包頁是否被鎖定
				errorType = tab.checkLocked(ignoreLocked);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					result = tab;
				}
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return result;
	}

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param tabType
	 * @return
	 */
	public Tab getTab(TabType tabType) {
		return getTab(tabType.getValue());
	}

	/**
	 * 取得包包頁
	 * 
	 * 當包包頁被鎖定,會傳回null,但實際上為非null,locked=true
	 * 
	 * @param tabType
	 * @param ignoreLock
	 *            是否忽略鎖定
	 * @return
	 */
	public Tab getTab(TabType tabType, boolean ignoreLocked) {
		return getTab(tabType.getValue(), ignoreLocked);
	}

	/**
	 * 移除包包頁
	 * 
	 * @param index
	 * @return
	 */
	public ErrorType removeTab(int index) {
		return removeTab(index, false);
	}

	/**
	 * 移除包包頁
	 * 
	 * @param index
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType removeTab(int index, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(index);
			if (tab != null) {
				errorType = tab.checkLocked(ignoreLocked);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					tabs.remove(index);
				}
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 清除所有包包頁
	 * 
	 * @return
	 */
	public ErrorType clearTab() {
		return clearTab(false);
	}

	/**
	 * 清除所有包包頁
	 * 
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType clearTab(boolean ignoreLocked) {
		ErrorType result = ErrorType.NO_ERROR;
		// 欲清除的包包頁索引
		List<Integer> removeIndexs = new LinkedList<Integer>();
		for (Map.Entry<Integer, Tab> entry : tabs.entrySet()) {
			Integer index = entry.getKey();
			Tab tab = entry.getValue();
			ErrorType errorType = tab.checkLocked(ignoreLocked);
			// 沒有被鎖定的包包頁
			if (ErrorType.NO_ERROR.equals(errorType)) {
				removeIndexs.add(index);
			} else {
				if (!ErrorType.AT_LEAST_ONE_TAB_LOCKED.equals(errorType)) {
					result = ErrorType.AT_LEAST_ONE_TAB_LOCKED;
				}
			}
		}
		//
		for (Integer index : removeIndexs) {
			tabs.remove(index);
		}
		return result;
	}

	/**
	 * 設定包包頁,取代掉原先包包上的tab
	 * 
	 * @param index
	 * @param tab
	 * @return
	 */
	public ErrorType setTab(int index, Tab tab) {
		return setTab(index, tab, false);
	}

	/**
	 * 設定包包頁,取代掉原先包包上的tab
	 * 
	 * @param index
	 * @param tab
	 * @param ignoreLocked
	 * @return
	 */
	public ErrorType setTab(int index, Tab tab, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(index);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab existTab = tabs.get(index);
			if (existTab != null) {
				// 檢查包包頁是否被鎖定
				errorType = existTab.checkLocked(ignoreLocked);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					tabs.put(index, tab);
				}
			} else {
				tabs.put(index, tab);
			}
		}
		return errorType;
	}

	/**
	 * 增加道具,若格子已有道具,是無法加新道具進去
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @return
	 */
	public ErrorType addItem(int tabIndex, int gridIndex, Item item) {
		return addItem(tabIndex, gridIndex, item, false);
	}

	/**
	 * 增加道具,若格子已有道具,是無法加新道具進去
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param item
	 * @param ignoreLocked
	 * @return
	 */
	public ErrorType addItem(int tabIndex, int gridIndex, Item item, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.addItem(gridIndex, item, ignoreLocked);
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType increaseAmount(int tabIndex, int gridIndex, int amount) {
		return increaseAmount(tabIndex, gridIndex, amount, false);
	}

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	public ErrorType increaseAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.increaseAmount(gridIndex, amount, ignoreLocked);
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public ErrorType increaseAmount(String uniqueId, int amount) {
		return increaseAmount(uniqueId, amount, false);
	}

	/**
	 * 增加數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType increaseAmount(String uniqueId, int amount, boolean ignoreLocked) {
		ErrorType errorType = ErrorType.NO_ERROR;
		int[] indexes = getIndex(uniqueId, true);// 先不管tab是否被鎖定
		if (indexes != null) {
			errorType = increaseAmount(indexes[0], indexes[1], amount, ignoreLocked);
		} else {
			// 道具不存在
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		return errorType;
	}

	/**
	 * 移除道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @return
	 */
	public ErrorType removeItem(int tabIndex, int gridIndex) {
		return removeItem(tabIndex, gridIndex, false);
	}

	/**
	 * 移除道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType removeItem(int tabIndex, int gridIndex, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.removeItem(gridIndex, ignoreLocked);
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 移除道具
	 * 
	 * @param uniqueId
	 * @return
	 */
	public ErrorType removeItem(String uniqueId) {
		return removeItem(uniqueId, false);
	}

	/**
	 * 移除道具
	 * 
	 * @param uniqueId
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType removeItem(String uniqueId, boolean ignoreLocked) {
		ErrorType errorType = ErrorType.NO_ERROR;
		int[] indexes = getIndex(uniqueId, true);// 先不管tab是否被鎖定
		if (indexes != null) {
			errorType = removeItem(indexes[0], indexes[1], ignoreLocked);
		} else {
			// 道具不存在
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		return errorType;
	}

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	public ErrorType decreaseAmount(int tabIndex, int gridIndex, int amount) {
		return decreaseAmount(tabIndex, gridIndex, amount, false);
	}

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType decreaseAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.decreaseAmount(gridIndex, amount, ignoreLocked);
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @return
	 */
	public ErrorType decreaseAmount(String uniqueId, int amount) {
		return decreaseAmount(uniqueId, amount, false);
	}

	/**
	 * 減少數量,格子需存在道具
	 * 
	 * @param uniqueId
	 * @param amount
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType decreaseAmount(String uniqueId, int amount, boolean ignoreLocked) {
		ErrorType errorType = ErrorType.NO_ERROR;
		int[] indexes = getIndex(uniqueId, true);// 先不管tab是否被鎖定
		if (indexes != null) {
			errorType = decreaseAmount(indexes[0], indexes[1], amount, ignoreLocked);
		} else {
			// 道具不存在
			errorType = ErrorType.ITEM_NOT_EXIST;
		}
		return errorType;
	}

	/**
	 * 清除指定包包頁內的道具
	 * 
	 * @param tabIndex
	 * @return
	 */
	public ErrorType clearItem(int tabIndex) {
		return clearItem(tabIndex, false);
	}

	/**
	 * 清除指定包包頁內的道具
	 * 
	 * @param tabIndex
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType clearItem(int tabIndex, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.clearItem(ignoreLocked);
			} else {
				errorType = ErrorType.TAB_NOT_EXIST;
			}
		}
		return errorType;
	}

	/**
	 * 清除所有包包頁內的道具
	 * 
	 * @return
	 */
	public ErrorType clearItem() {
		return clearItem(false);
	}

	/**
	 * 清除所有包包頁內的道具
	 * 
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType clearItem(boolean ignoreLocked) {
		ErrorType errorType = ErrorType.NO_ERROR;
		// 包包頁
		for (Integer tabIndex : tabs.keySet()) {
			errorType = clearItem(tabIndex, ignoreLocked);
			if (!ErrorType.NO_ERROR.equals(errorType)) {
				if (!ErrorType.TAB_EMPTY.equals(errorType)) {
					return errorType;
				} else {
					errorType = ErrorType.NO_ERROR;
				}
			}
		}
		return errorType;
	}

	/**
	 * 取得道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @return
	 */
	public Item getItem(int tabIndex, int gridIndex) {
		return getItem(tabIndex, gridIndex, false);
	}

	/**
	 * 取得道具
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param ignoreLock
	 *            是否忽略鎖定
	 * @return
	 */
	public Item getItem(int tabIndex, int gridIndex, boolean ignoreLocked) {
		Item result = null;
		Tab tab = getTab(tabIndex, ignoreLocked);
		if (tab != null) {
			result = tab.getItem(gridIndex, ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得道具,by uniqueId
	 * 
	 * @param uniqueId
	 * @return
	 */
	public Item getItem(String uniqueId) {
		return getItem(uniqueId, false);
	}

	/**
	 * 取得道具,by uniqueId
	 * 
	 * @param uniqueId
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public Item getItem(String uniqueId, boolean ignoreLocked) {
		Item result = null;
		int[] indexes = getIndex(uniqueId, ignoreLocked);
		if (indexes != null) {
			result = getItem(indexes[0], indexes[1], ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得道具,by id
	 * 
	 * @param id
	 * @return
	 */
	public List<Item> getItems(String id) {
		return getItems(id, false);
	}

	/**
	 * 取得道具,by id
	 * 
	 * @param id
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public List<Item> getItems(String id, boolean ignoreLocked) {
		List<Item> result = new LinkedList<Item>();
		for (Tab tab : tabs.values()) {
			List<Item> items = tab.getItems(id, ignoreLocked);
			if (items.size() > 0) {
				result.addAll(items);
			}
		}
		return result;
	}

	/**
	 * 取得道具,by itemType
	 * 
	 * @param itemType
	 * @return
	 */
	public List<Item> getItems(ItemType itemType) {
		return getItems(itemType, false);
	}

	/**
	 * 取得道具,by itemType
	 * 
	 * @param itemType
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public List<Item> getItems(ItemType itemType, boolean ignoreLocked) {
		List<Item> result = new LinkedList<Item>();
		for (Tab tab : tabs.values()) {
			List<Item> items = tab.getItems(itemType, ignoreLocked);
			if (items.size() > 0) {
				result.addAll(items);
			}
		}
		return result;
	}

	/**
	 * 取得所有道具
	 * 
	 * @return
	 */
	public List<Item> getItems() {
		return getItems(false);
	}

	/**
	 * 取得所有道具
	 * 
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public List<Item> getItems(boolean ignoreLocked) {
		List<Item> result = new LinkedList<Item>();
		for (Tab tab : tabs.values()) {
			if (ignoreLocked) {
				result.addAll(tab.getItems().values());
			} else {
				if (!tab.isLocked()) {
					result.addAll(tab.getItems().values());
				}
			}
		}
		return result;
	}

	/**
	 * 設定道具,取代掉原先index上的道具
	 * 
	 * @param index
	 * @param item
	 * @return
	 */
	public ErrorType setItem(int tabIndex, int gridIndex, Item item) {
		return setItem(tabIndex, gridIndex, item, false);
	}

	/**
	 * 設定道具,取代掉原先index上的道具
	 * 
	 * @param index
	 * @param item
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType setItem(int tabIndex, int gridIndex, Item item, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.setItem(gridIndex, item, ignoreLocked);
			}
		}
		return errorType;
	}

	/**
	 * 設定道具,取代掉原先index上的道具數量
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @return
	 */
	public ErrorType setItemAmount(int tabIndex, int gridIndex, int amount) {
		return setItemAmount(tabIndex, gridIndex, amount, false);
	}

	/**
	 * 設定道具,取代掉原先index上的道具數量
	 * 
	 * @param tabIndex
	 * @param gridIndex
	 * @param amount
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public ErrorType setItemAmount(int tabIndex, int gridIndex, int amount, boolean ignoreLocked) {
		// 檢查是否超過包包頁索引
		ErrorType errorType = checkTabIndex(tabIndex);
		if (ErrorType.NO_ERROR.equals(errorType)) {
			Tab tab = tabs.get(tabIndex);
			if (tab != null) {
				errorType = tab.setItemAmount(gridIndex, amount, ignoreLocked);
			}
		}
		return errorType;
	}

	/**
	 * 包包頁是否有此索引
	 * 
	 * @param index
	 * @return
	 */
	public boolean containIndex(int index) {
		return tabs.containsKey(index);
	}

	/**
	 * 取得包包頁索引
	 * 
	 * @return
	 */
	public List<Integer> getTabIndexs() {
		return getTabIndexs(false);
	}

	/**
	 * 取得包包頁索引
	 * 
	 * @param ignoreLocked
	 * @return
	 */
	public List<Integer> getTabIndexs(boolean ignoreLocked) {
		List<Integer> result = new LinkedList<Integer>();
		for (Tab tab : tabs.values()) {
			if (ignoreLocked || !tab.isLocked()) {
				result.add(tab.getId());
			}
		}
		return result;
	}

	/**
	 * 取得道具索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @return
	 */
	public int[] getIndex(String uniqueId) {
		return getIndex(uniqueId, false);
	}

	/**
	 * 取得道具索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param uniqueId
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public int[] getIndex(String uniqueId, boolean ignoreLocked) {
		int[] result = null;
		for (Tab tab : tabs.values()) {
			int gridIndex = tab.getIndex(uniqueId, ignoreLocked);
			if (gridIndex != -1) {
				result = new int[] { tab.getId(), gridIndex };
				break;
			}
		}
		return result;
	}

	/**
	 * 取得道具索引,by id
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @return
	 */
	public List<int[]> getIndexs(String id) {
		return getIndexs(id, false);
	}

	/**
	 * 取得道具索引,by id
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public List<int[]> getIndexs(String id, boolean ignoreLocked) {
		List<int[]> result = new LinkedList<int[]>();
		for (Tab tab : tabs.values()) {
			List<Integer> gridIndexes = tab.getIndexs(id, ignoreLocked);
			if (gridIndexes.size() > 0) {
				for (Integer gridIndex : gridIndexes) {
					int[] indexes = new int[] { tab.getId(), gridIndex };
					result.add(indexes);
				}
			}
		}
		return result;
	}

	/**
	 * 取得道具總計數量,by id
	 * 
	 * @param id
	 * @return
	 */
	public int getAmount(String id) {
		return getAmount(id, false);
	}

	/**
	 * 取得道具總計數量,by id
	 * 
	 * @param id
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public int getAmount(String id, boolean ignoreLocked) {
		int result = 0;
		for (Tab tab : tabs.values()) {
			result += tab.getAmount(id, ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得空格數量
	 * 
	 * @return
	 */
	public int getEmptySize() {
		return getEmptySize(false);
	}

	/**
	 * 取得空格數量
	 * 
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public int getEmptySize(boolean ignoreLocked) {
		int result = 0;
		for (Tab tab : tabs.values()) {
			result += tab.getEmptySize(ignoreLocked);
		}
		return result;
	}

	/**
	 * 取得一個空格子的index,無放入任何道具
	 * 
	 * @return
	 */
	public int[] getEmptyIndex() {
		return getEmptyIndex(false);
	}

	/**
	 * 取得一個空格子的index,無放入任何道具
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * @return
	 */
	public int[] getEmptyIndex(boolean ignoreLocked) {
		int[] result = null;
		for (Tab tab : tabs.values()) {
			int gridIndex = tab.getEmptyIndex(ignoreLocked);
			if (gridIndex != -1) {
				result = new int[] { tab.getId(), gridIndex };
				break;
			}
		}
		return result;
	}

	/**
	 * 取得還能放道具的索引
	 * 
	 * 已有道具,且數量未滿的格子索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public int[] getPutIndex(String id) {
		return getPutIndex(id, false);
	}

	/**
	 * 取得還能放道具的索引
	 * 
	 * 已有道具,且數量未滿的格子索引
	 * 
	 * [0]=tabIndex
	 * 
	 * [1]=gridIndex
	 * 
	 * @param id
	 * @param ignoreLocked
	 *            是否忽略鎖定
	 * 
	 * @return
	 */
	public int[] getPutIndex(String id, boolean ignoreLocked) {
		int[] result = null;
		for (Tab tab : tabs.values()) {
			int gridIndex = tab.getPutIndex(id, ignoreLocked);
			if (gridIndex != -1) {
				result = new int[] { tab.getId(), gridIndex };
				break;
			}
		}
		return result;
	}

	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.appendSuper(super.toString());
		builder.append("tabs", tabs);
		return builder.toString();
	}

	public Object clone() {
		BagInfoImpl copy = null;
		copy = (BagInfoImpl) super.clone();
		copy.tabs = clone(tabs);
		return copy;
	}

	// --------------------------------------------------
	// jaxb
	// --------------------------------------------------
	@XmlRootElement(name = "tab")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class TabImpl extends BaseBeanSupporter implements Tab {

		private static final long serialVersionUID = -2621119020950679808L;

		/**
		 * 頁籤id
		 */
		private int id;

		/**
		 * 是否鎖定
		 */
		private boolean locked = false;

		/**
		 * 道具
		 * 
		 * <gridIndex,道具>
		 */
		@XmlJavaTypeAdapter(IntegerItemXmlAdapter.class)
		private Map<Integer, Item> items = new LinkedHashMap<Integer, Item>();

		public TabImpl(int id) {
			this.id = id;
		}

		public TabImpl(TabType tabType) {
			this(tabType.getValue());
		}

		public TabImpl() {
			this(0);
		}

		/**
		 * 頁籤id
		 * 
		 * @return
		 */
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Map<Integer, Item> getItems() {
			return items;
		}

		public void setItems(Map<Integer, Item> items) {
			this.items = items;
		}

		/**
		 * 已放道具的格子數量,如:20格
		 */
		public int getItemSize() {
			int result = 0;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked();
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (int i = 0; i < MAX_GRID_SIZE; i++) {
					Item item = items.get(i);
					if (item != null) {
						result += 1;
					}
				}
			}
			return result;
		}

		/**
		 * 頁是否鎖定
		 * 
		 * @return
		 */
		public boolean isLocked() {
			return locked;
		}

		public void setLocked(boolean locked) {
			this.locked = locked;
		}

		/**
		 * 包包頁是否滿了
		 * 
		 * @return
		 */
		public boolean isFull() {
			boolean result = true;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked();
			if (errorType == ErrorType.TAB_LOCKED) {
				result = true;
			} else {
				for (int i = 0; i < MAX_GRID_SIZE; i++) {
					Item item = items.get(i);
					if (item == null) {
						result = false;
						break;
					}
				}
			}
			return result;
		}

		/**
		 * 檢查包包頁是否被鎖定
		 * 
		 * @return
		 */
		public ErrorType checkLocked() {
			return checkLocked(false);
		}

		/**
		 * 檢查包包頁是否被鎖定
		 * 
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType checkLocked(boolean ignoreLocked) {
			ErrorType errorType = ErrorType.NO_ERROR;
			if (locked && !ignoreLocked) {
				errorType = ErrorType.TAB_LOCKED;
			}
			return errorType;
		}

		/**
		 * 檢查是否超過格子索引
		 * 
		 * @param index
		 * @return
		 */
		protected ErrorType checkGridIndex(int index) {
			ErrorType errorType = ErrorType.NO_ERROR;
			if (index >= MAX_GRID_SIZE || index < 0) {
				errorType = ErrorType.OVER_GRID_INDEX;
			}
			return errorType;
		}

		/**
		 * 增加道具,若格子已有道具,是無法加新道具進去
		 * 
		 * @param index
		 * @param item
		 * @return
		 */
		public ErrorType addItem(int index, Item item) {
			return addItem(index, item, false);
		}

		/**
		 * 增加道具,若格子已有道具,是無法加新道具進去
		 * 
		 * @param index
		 * @param item
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType addItem(int index, Item item, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					// 道具不存在
					if (item == null) {
						errorType = ErrorType.ITEM_NOT_EXIST;
					}
					// 包包頁滿了
					else if (items.size() >= MAX_GRID_SIZE) {
						errorType = ErrorType.TAB_FULL;
					}
					// 格子已有道具
					else if (containIndex(index)) {
						errorType = ErrorType.ALREADY_HAS_ITEM;
					} else {
						items.put(index, item);
					}
				}
			}
			return errorType;
		}

		/**
		 * 增加道具數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		public ErrorType increaseAmount(int index, int amount) {
			return increaseAmount(index, amount, false);
		}

		/**
		 * 增加道具數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType increaseAmount(int index, int amount, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					Item item = items.get(index);
					// 道具不存在
					if (item == null) {
						errorType = ErrorType.ITEM_NOT_EXIST;
					}
					// 超過道具最大數量,maxAmount=0,無堆疊數量限制
					else {
						// 總計數量
						int totalAmount = item.getAmount() + amount;
						// 最大數量
						int maxAmount = item.getMaxAmount();
						// 當有堆疊數量限制時,maxAmount!=0
						if (maxAmount != 0 && totalAmount > maxAmount) {
							errorType = ErrorType.OVER_MAX_AMOUNT;
						} else {
							item.setAmount(totalAmount);
						}
					}
				}
			}
			return errorType;
		}

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @return
		 */
		public ErrorType increaseAmount(String uniqueId, int amount) {
			return increaseAmount(uniqueId, amount, false);
		}

		/**
		 * 增加數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType increaseAmount(String uniqueId, int amount, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				int index = getIndex(uniqueId, ignoreLocked);
				if (index == -1) {
					errorType = ErrorType.ITEM_NOT_EXIST;
				} else {
					errorType = increaseAmount(index, amount, ignoreLocked);
				}
			}
			return errorType;
		}

		/**
		 * 移除道具
		 * 
		 * @param index
		 * @return
		 */
		public ErrorType removeItem(int index) {
			return removeItem(index, false);
		}

		/**
		 * 移除道具
		 * 
		 * @param index
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType removeItem(int index, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					Item item = items.get(index);
					// 道具不存在
					if (item == null) {
						errorType = ErrorType.ITEM_NOT_EXIST;
					} else {
						items.remove(index);
					}
				}
			}
			return errorType;
		}

		/**
		 * 移除道具
		 * 
		 * @param uniqueId
		 * @return
		 */
		public ErrorType removeItem(String uniqueId) {
			return removeItem(uniqueId, false);
		}

		/**
		 * 移除道具
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType removeItem(String uniqueId, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				int index = getIndex(uniqueId, ignoreLocked);
				if (index == -1) {
					errorType = ErrorType.ITEM_NOT_EXIST;
				} else {
					errorType = removeItem(index, ignoreLocked);
				}
			}
			return errorType;
		}

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		public ErrorType decreaseAmount(int index, int amount) {
			return decreaseAmount(index, amount, false);
		}

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType decreaseAmount(int index, int amount, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					Item item = items.get(index);
					// 道具不存在
					if (item == null) {
						errorType = ErrorType.ITEM_NOT_EXIST;
					} else {
						int origAmount = item.getAmount();
						// 數量不足時
						if (origAmount < amount) {
							errorType = ErrorType.AMOUNT_NOT_ENOUGH;
						} else {
							// 剩餘數量
							int totalAmount = origAmount - amount;
							if (totalAmount == 0) {
								errorType = removeItem(index);
							} else {
								item.setAmount(totalAmount);
							}
						}
					}
				}
			}
			return errorType;
		}

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @return
		 */
		public ErrorType decreaseAmount(String uniqueId, int amount) {
			return decreaseAmount(uniqueId, amount, false);
		}

		/**
		 * 減少數量,格子需存在道具
		 * 
		 * @param uniqueId
		 * @param amount
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType decreaseAmount(String uniqueId, int amount, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				int index = getIndex(uniqueId, ignoreLocked);
				if (index == -1) {
					errorType = ErrorType.ITEM_NOT_EXIST;
				} else {
					errorType = decreaseAmount(index, amount, ignoreLocked);
				}
			}
			return errorType;
		}

		/**
		 * 清除所有道具
		 * 
		 * @return
		 */
		public ErrorType clearItem() {
			return clearItem(false);
		}

		/**
		 * 清除所有道具
		 * 
		 * @param ignoreLocked
		 *            是否忽略鎖定
		 * @return
		 */
		public ErrorType clearItem(boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				if (items.size() == 0) {
					errorType = ErrorType.TAB_EMPTY;
				} else {
					items.clear();
				}
			}
			return errorType;
		}

		/**
		 * 取得道具
		 * 
		 * @param index
		 * @return
		 */
		public Item getItem(int index) {
			return getItem(index, false);
		}

		/**
		 * 取得道具
		 * 
		 * @param index
		 * @param ignoreLock
		 *            是否忽略鎖定
		 * @return
		 */
		public Item getItem(int index, boolean ignoreLocked) {
			Item result = null;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					result = items.get(index);
				}
			}
			return result;
		}

		/**
		 * 取得道具,by uniqueId
		 * 
		 * @param uniqueId
		 * @return
		 */
		public Item getItem(String uniqueId) {

			return getItem(uniqueId, false);
		}

		/**
		 * 取得道具,by uniqueId
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		public Item getItem(String uniqueId, boolean ignoreLocked) {
			Item result = null;
			int index = getIndex(uniqueId, ignoreLocked);
			if (index != -1) {
				result = getItem(index, ignoreLocked);
			}
			return result;
		}

		/**
		 * 取得道具,by id
		 * 
		 * @param id
		 * @return
		 */
		public List<Item> getItems(String id) {
			return getItems(id, false);
		}

		/**
		 * 取得道具,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public List<Item> getItems(String id, boolean ignoreLocked) {
			List<Item> result = new LinkedList<Item>();
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Item item : items.values()) {
					if (item.getId().equals(id)) {
						result.add(item);
					}
				}
			}
			return result;
		}

		/**
		 * 取得道具,by itemType
		 * 
		 * @param itemType
		 * @return
		 */
		public List<Item> getItems(ItemType itemType) {
			return getItems(itemType, false);
		}

		/**
		 * 取得道具,by itemType
		 * 
		 * @param itemType
		 * @param ignoreLocked
		 * @return
		 */
		public List<Item> getItems(ItemType itemType, boolean ignoreLocked) {
			List<Item> result = new LinkedList<Item>();
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Item item : items.values()) {
					if (item.getItemType().equals(itemType)) {
						result.add(item);
					}
				}
			}
			return result;
		}

		/**
		 * 設定道具,取代掉原先index上的道具
		 * 
		 * @param index
		 * @param item
		 * @return
		 */
		public ErrorType setItem(int index, Item item) {
			return setItem(index, item, false);
		}

		/**
		 * 設定道具,取代掉原先index上的道具
		 * 
		 * @param index
		 * @param item
		 * @param ignoreLocked
		 * @return
		 */
		public ErrorType setItem(int index, Item item, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					items.put(index, item);
				}
			}
			return errorType;
		}

		/**
		 * 設定道具,取代掉原先index上的道具數量
		 * 
		 * @param index
		 * @param amount
		 * @return
		 */
		public ErrorType setItemAmount(int index, int amount) {
			return setItemAmount(index, amount, false);
		}

		/**
		 * 設定道具,取代掉原先index上的道具數量
		 * 
		 * @param index
		 * @param amount
		 * @param ignoreLocked
		 * @return
		 */
		public ErrorType setItemAmount(int index, int amount, boolean ignoreLocked) {
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				// 檢查是否超過格子索引
				errorType = checkGridIndex(index);
				if (ErrorType.NO_ERROR.equals(errorType)) {
					Item item = items.get(index);
					// 道具不存在
					if (item == null) {
						errorType = ErrorType.ITEM_NOT_EXIST;
					} else {
						item.setAmount(amount);
					}

				}
			}
			return errorType;
		}

		/**
		 * 格子是否有此索引
		 * 
		 * @param index
		 * @return
		 */
		public boolean containIndex(int index) {
			return items.containsKey(index);
		}

		/**
		 * 取得道具索引
		 * 
		 * @param uniqueId
		 * @return
		 */
		public int getIndex(String uniqueId) {
			return getIndex(uniqueId, false);
		}

		/**
		 * 取得道具索引
		 * 
		 * @param uniqueId
		 * @param ignoreLocked
		 * @return
		 */
		public int getIndex(String uniqueId, boolean ignoreLocked) {
			int result = -1;// 表示找不到此uniqueId的索引 = -1
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Map.Entry<Integer, Item> entry : items.entrySet()) {
					Integer index = entry.getKey();
					Item item = entry.getValue();
					if (item != null && item.getUniqueId().equals(uniqueId)) {
						result = index;
						break;
					}
				}
			}
			return result;
		}

		/**
		 * 取得道具索引,by id
		 * 
		 * @param id
		 * @return
		 */
		public List<Integer> getIndexs(String id) {
			return getIndexs(id, false);
		}

		/**
		 * 取得道具索引,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public List<Integer> getIndexs(String id, boolean ignoreLocked) {
			List<Integer> result = new LinkedList<Integer>();
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Map.Entry<Integer, Item> entry : items.entrySet()) {
					Integer index = entry.getKey();
					Item item = entry.getValue();
					if (item != null && item.getId().equals(id)) {
						result.add(index);
					}
				}
			}
			return result;
		}

		/**
		 * 取得道具總計數量,by id
		 * 
		 * @param id
		 * @return
		 */
		public int getAmount(String id) {
			return getAmount(id, false);
		}

		/**
		 * 取得道具總計數量,by id
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public int getAmount(String id, boolean ignoreLocked) {
			int result = 0;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Map.Entry<Integer, Item> entry : items.entrySet()) {
					Item item = entry.getValue();
					if (item != null && item.getId().equals(id)) {
						result += item.getAmount();
					}
				}
			}
			return result;
		}

		/**
		 * 取得空格數量
		 * 
		 * @return
		 */
		public int getEmptySize() {
			return getEmptySize(false);
		}

		/**
		 * 取得空格數量
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		public int getEmptySize(boolean ignoreLocked) {
			int result = 0;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (int i = 0; i < MAX_GRID_SIZE; i++) {
					Item item = items.get(i);
					if (item == null) {
						result += 1;
					}
				}
			}
			return result;
		}

		/**
		 * 取得一個空格子的index,無放入任何道具
		 * 
		 * @return
		 */
		public int getEmptyIndex() {
			return getEmptyIndex(false);
		}

		/**
		 * 取得一個空格子的index,無放入任何道具
		 * 
		 * @param ignoreLocked
		 * @return
		 */
		public int getEmptyIndex(boolean ignoreLocked) {
			int result = -1;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			//
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (int i = 0; i < MAX_GRID_SIZE; i++) {
					Item item = items.get(i);
					if (item == null) {
						result = i;
						break;
					}
				}
			}
			return result;
		}

		/**
		 * 取得還能放道具的索引
		 * 
		 * 已有道具,且數量未滿的格子索引
		 * 
		 * @param id
		 * @return
		 */
		public int getPutIndex(String id) {
			return getPutIndex(id, false);
		}

		/**
		 * 取得還能放道具的索引
		 * 
		 * 已有道具,且數量未滿的格子索引
		 * 
		 * @param id
		 * @param ignoreLocked
		 * @return
		 */
		public int getPutIndex(String id, boolean ignoreLocked) {
			int result = -1;
			// 檢查包包頁是否被鎖定
			ErrorType errorType = checkLocked(ignoreLocked);
			if (ErrorType.NO_ERROR.equals(errorType)) {
				for (Map.Entry<Integer, Item> entry : items.entrySet()) {
					Integer index = entry.getKey();
					Item item = entry.getValue();
					if (item != null && item.getId().equals(id)) {
						int origAmount = item.getAmount();
						int maxAmount = item.getMaxAmount();
						// 檢查是否小於最大數量,若小於則表示還能放
						// 當有堆疊數量限制時,maxAmount!=0
						if (maxAmount != 0 && origAmount < maxAmount) {
							result = index;
							break;
						}
						// 無堆疊數量限制,maxAmount==0
						else if (maxAmount == 0) {
							result = index;
							break;
						}
					}
				}
			}
			return result;
		}

		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this);
			builder.appendSuper(super.toString());
			builder.append("id", id);
			builder.append("locked", locked);
			builder.append("items", items);
			return builder.toString();
		}

		public boolean equals(Object object) {
			if (!(object instanceof TabImpl)) {
				return false;
			}
			if (this == object) {
				return true;
			}
			TabImpl other = (TabImpl) object;
			return new EqualsBuilder().append(id, other.getId()).isEquals();
		}

		public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
		}

		public Object clone() {
			TabImpl copy = null;
			copy = (TabImpl) super.clone();
			copy.items = clone(items);
			return copy;
		}
	}

}
