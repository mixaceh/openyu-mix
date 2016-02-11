package org.openyu.mix.manor.service.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.RoleField;
import org.openyu.mix.vip.vo.VipType;
import org.openyu.commons.lang.event.EventAttach;
import org.openyu.commons.service.event.BeanChangeEvent;
import org.openyu.commons.service.event.supporter.BeanChangeAdapter;

/**
 * 莊園模組
 * 
 * field改變監聽器
 */
public class ManorChangeAdapter extends BeanChangeAdapter {
	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorChangeAdapter.class);

	@Autowired
	@Qualifier("manorService")
	protected transient ManorService manorService;

	public ManorChangeAdapter() {
	}

	public void beanChanged(BeanChangeEvent beanChangeEvent) {
		Object source = beanChangeEvent.getSource();
		// 角色
		if (source instanceof Role) {
			Role role = (Role) source;
			String fieldName = beanChangeEvent.getFieldName();
			boolean connected = role.isConnected();// 角色是否已連線

			// vipType改變
			if (RoleField.VIP_TYPE.equals(fieldName)) {
				@SuppressWarnings("unchecked")
				EventAttach<VipType, VipType> eventAttach = ((EventAttach<VipType, VipType>) beanChangeEvent
						.getAttach());
				// System.out.println(beanChangeEvent.getFieldEnum() + ", " +
				// eventAttach);

				VipType oldValue = eventAttach.getOldValue();
				Double diffValue = eventAttach.getDiffValue();
				// 改變前=改變後,所以是沒改變
				if (diffValue == 0d && oldValue != null) {
					return;
				}

				// 改變所有的農場頁是否鎖定
				manorService.changeManorPenLocked(connected, role);
			} else {
				// just for pretty
			}
		}
	}
}
