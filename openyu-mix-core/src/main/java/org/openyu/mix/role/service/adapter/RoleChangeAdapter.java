package org.openyu.mix.role.service.adapter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleField;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.lang.event.EventAttach;
import org.openyu.commons.service.event.BeanChangeEvent;
import org.openyu.commons.service.event.supporter.BeanChangeAdapter;

/**
 * 角色模組
 * 
 * field改變監聽器
 */
public class RoleChangeAdapter extends BeanChangeAdapter
{
	@Autowired
	@Qualifier("itemService")
	protected transient ItemService itemService;

	@Autowired
	@Qualifier("roleService")
	protected transient RoleService roleService;

	public RoleChangeAdapter()
	{}

	public void beanChanged(BeanChangeEvent beanChangeEvent)
	{
		Object source = beanChangeEvent.getSource();
		//角色
		if (source instanceof Role)
		{
			Role role = (Role) source;
			String fieldName = beanChangeEvent.getFieldName();
			boolean connected = role.isConnected();//角色是否已連線

			//等級改變
			if (RoleField.LEVEL.equals(fieldName))
			{
				@SuppressWarnings("unchecked")
				EventAttach<Integer, Integer> eventAttach = ((EventAttach<Integer, Integer>) beanChangeEvent
						.getAttach());
				//System.out.println(beanChangeEvent.getFieldEnum() + ", " + eventAttach);

				Double diffValue = eventAttach.getDiffValue();
				//改變前=改變後,所以是沒改變
				if (diffValue == 0d)
				{
					return;
				}
				//

				//重新計算所有屬性
				roleService.calcAttrubutes(role);

				if (connected)
				{
					//成名等級
					roleService.sendFamousLevel(role, diffValue.intValue());

					//同步欄位
					roleService.sendSyncRoleField(role.getId(), fieldName, role.getLevel());
				}
			}
			//聲望改變
			else if (RoleField.FAME.equals(fieldName))
			{
				@SuppressWarnings("unchecked")
				EventAttach<Integer, Integer> eventAttach = ((EventAttach<Integer, Integer>) beanChangeEvent
						.getAttach());
				//System.out.println(beanChangeEvent.getFieldEnum() + ", " + eventAttach);

				Double diffValue = eventAttach.getDiffValue();
				//改變前=改變後,所以是沒改變
				if (diffValue == 0d)
				{
					return;
				}

				if (connected)
				{
					//同步欄位
					roleService.sendSyncRoleField(role.getId(), fieldName, role.getFame());
				}
			}
			//vipType改變
			else if (RoleField.VIP_TYPE.equals(fieldName))
			{
				@SuppressWarnings("unchecked")
				EventAttach<VipType, VipType> eventAttach = ((EventAttach<VipType, VipType>) beanChangeEvent
						.getAttach());
				//System.out.println(beanChangeEvent.getFieldEnum() + ", " + eventAttach);

				VipType oldValue = eventAttach.getOldValue();
				Double diffValue = eventAttach.getDiffValue();
				//改變前=改變後,所以是沒改變
				if (diffValue == 0d && oldValue != null)
				{
					return;
				}

				//改變所有的包包頁是否鎖定
				itemService.changeBagInfoLocked(connected, role);

				if (connected)
				{
					//同步欄位
					roleService.sendSyncRoleField(role.getId(), fieldName, role.getVipType());
				}
			}
			else
			{
				//just for pretty
			}
		}
	}
}
