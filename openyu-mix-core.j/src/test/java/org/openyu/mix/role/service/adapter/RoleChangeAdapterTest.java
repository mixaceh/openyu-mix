package org.openyu.mix.role.service.adapter;

import org.junit.Test;

import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.vip.vo.VipType;

public class RoleChangeAdapterTest extends RoleTestSupporter
{

	@Test
	public void levelChanged()
	{
		Role role = mockRole();
		//加入監聽
		role.addBeanChangeListener(roleChangeAdapter);

		//indusry
		role.setIndustryId("HUMAN_WARRIOR_1");//設定行業

		//level
		System.out.println("lv= " + role.getLevel() + ", " + role.getAttributeGroup());
		role.setLevel(1);
		System.out.println("lv= " + role.getLevel() + ", " + role.getAttributeGroup());
		role.setLevel(2);
		System.out.println("lv= " + role.getLevel() + ", " + role.getAttributeGroup());
		role.setLevel(60);
		System.out.println("lv= " + role.getLevel() + ", " + role.getAttributeGroup());
		role.setLevel(200);//maxLv=60,已超過max,最高就是lv=60的屬性
		System.out.println("lv= " + role.getLevel() + ", " + role.getAttributeGroup());

		//沒改變
		role.setLevel(200);
		role.setLevel(200);
		role.setLevel(200);

		//移除監聽
		role.removeBeanChangeListener(roleChangeAdapter);
	}

	@Test
	public void fameChanged()
	{
		Role role = mockRole();
		//加入監聽
		role.addBeanChangeListener(roleChangeAdapter);

		//fame
		role.setFame(100);
		role.setFame(200);
		role.setFame(900);

		//沒改變
		role.setFame(900);
		role.setFame(900);
		role.setFame(900);

		//移除監聽
		role.removeBeanChangeListener(roleChangeAdapter);
	}

	@Test
	public void vipTypeChanged()
	{
		Role role = mockRole();
		//加入監聽
		role.addBeanChangeListener(roleChangeAdapter);

		//vip
		role.setVipType(VipType._0);
		role.setVipType(VipType._1);
		role.setVipType(VipType._2);

		//沒改變
		role.setVipType(VipType._2);
		role.setVipType(VipType._2);
		role.setVipType(VipType._2);

		//移除監聽
		role.removeBeanChangeListener(roleChangeAdapter);
	}
}
