package org.openyu.mix.manor.service.adapter;

import org.junit.Test;

import org.openyu.mix.manor.ManorTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;

public class ManorChangeAdapterTest extends ManorTestSupporter
{

	@Test
	public void vipTypeChanged()
	{
		Role role = mockRole();
		//加入監聽
		role.addBeanChangeListener(manorChangeAdapter);

		//vip
		role.setVipType(VipType._0);
		role.setVipType(VipType._2);
		role.setVipType(VipType._4);

		//沒改變
		role.setVipType(VipType._4);
		role.setVipType(VipType._4);
		role.setVipType(VipType._4);

		//移除監聽
		role.removeBeanChangeListener(manorChangeAdapter);
	}
}
