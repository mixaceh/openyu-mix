package org.openyu.mix.item.service;

import org.openyu.mix.app.service.AppService;
import org.openyu.mix.item.vo.Equipment;

public interface EquipmentService extends AppService
{

	/**
	 * 穿裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param roleId
	 * @param equipment
	 * @return
	 */
	boolean putEquipment(boolean sendable, String roleId, Equipment equipment);

	/**
	 * 脫裝備,計算所有屬性
	 * 
	 * @param sendable
	 * @param roleId
	 * @param equipment
	 * @return
	 */
	boolean takeEquipment(boolean sendable, String roleId, Equipment equipment);
}
