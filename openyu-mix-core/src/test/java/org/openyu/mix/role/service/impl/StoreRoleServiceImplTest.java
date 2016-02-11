package org.openyu.mix.role.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.junit.Test;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.role.vo.impl.RoleImpl;
import org.openyu.mix.role.RoleTestSupporter;
import org.openyu.commons.io.IoHelper;
import org.openyu.commons.thread.ThreadHelper;

public class StoreRoleServiceImplTest extends RoleTestSupporter {

	@Test
	public void storeRole() {
		// 隨機
		Role role = randomRole();

		// create
		Serializable pk = roleService.insert(role);
		printInsert(0, pk);
		assertNotNull(pk);
		System.out.println("version: " + role.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// update
		boolean stored = storeRoleService.storeRole(true, role);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + role.getVersion());
		System.out.println("可以關DB了,要測試序列化檔案");
		ThreadHelper.sleep(5 * 1000);

		// 在這段等待時間內,把db關了,再儲存資料,就會發生錯誤,然後重試存db
		// 儲存到db, 若失敗, 則序列化到檔案, 目錄, file:custom/role/unsave
		// update2
		stored = storeRoleService.storeRole(true, role);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + role.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// update3
		stored = storeRoleService.storeRole(true, role);
		ThreadHelper.sleep(1 * 1000);
		printUpdate(0, stored);
		assertTrue(stored);
		System.out.println("version: " + role.getVersion());
		ThreadHelper.sleep(5 * 1000);

		// delete
		Role deletedEntity = roleService.delete(RoleImpl.class, role.getSeq());
		printDelete(0, deletedEntity);
		assertNotNull(deletedEntity);
	}

	@Test
	public void serializeRole() throws Exception {
		// 隨機
		Role role = randomRole();
		//
		String result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeRoleService.serializeRole(true, role);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);

		// 讀取序列化檔案
		InputStream io = new BufferedInputStream(new FileInputStream(result));
		System.out.println("available: " + IoHelper.available(io));
		//
		byte[] bytes = IoHelper.read(io);
		System.out.println("length: " + bytes.length);
		//
		// FST+DES+LZ4
		// custom/role/unsave\TEST_ROLE4481593nBJwVPYCo.ser
		// available: 3226
		// length: 3226
		//
		// FST+N/A+N/A
		// custom/role/unsave\TEST_ROLE4810267kIhxNjgcy.ser
		// available: 6552
		// length: 6552
		//
		// FST+DES+N/A
		// custom/role/unsave\TEST_ROLE4744199nMFemL0Um.ser
		// available: 6560
		// length: 6560
		//
		// JDK+DES+LZ4
		// custom/role/unsave\TEST_ROLE4744389ioo0KWsKZ.ser
		// available: 7833
		// length: 7833
	}

	@Test
	public void deserializeRole() throws Exception {
		// 隨機
		Role role = randomRole();
		String outName = storeRoleService.serializeRole(true, role);
		System.out.println(outName);
		//
		Role result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeRoleService.deserializeRole(role.getId());
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(role.getId(), result.getId());
	}

	@Test
	public void serialize_TEST_ROLE() {
		final String ROLE_ID = "TEST_ROLE";
		Role role = roleService.findRole(ROLE_ID);
		String result = null;
		//
		int count = 1;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeRoleService.serializeRole(true, role);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	public void deserialize_TEST_ROLE() throws Exception {
		final String ROLE_ID = "TEST_ROLE";
		//
		Role result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = storeRoleService.deserializeRole(ROLE_ID);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		//
		System.out.println(result);
		assertEquals(ROLE_ID, result.getId());
	}
}
