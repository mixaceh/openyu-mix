package org.openyu.mix.app;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.openyu.mix.chat.service.ChatSetService;
import org.openyu.mix.core.service.CoreMessageType;
import org.openyu.mix.core.service.CoreModuleType;
import org.openyu.mix.flutter.vo.Attribute;
import org.openyu.mix.flutter.vo.AttributeType;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.impl.ItemServiceImpl;
import org.openyu.mix.item.vo.Equipment;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.manor.vo.Land;
import org.openyu.commons.enumz.BooleanEnum;
import org.openyu.commons.enumz.CharEnum;
import org.openyu.commons.enumz.DoubleEnum;
import org.openyu.commons.enumz.FloatEnum;
import org.openyu.commons.enumz.IntEnum;
import org.openyu.commons.enumz.LongEnum;
import org.openyu.commons.enumz.StringEnum;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.StringHelper;
import org.openyu.socklet.connector.vo.JavaConnector;
import org.openyu.socklet.connector.vo.impl.JavaConnectorImpl;
import org.openyu.socklet.connector.vo.supporter.GenericReceiverSupporter;
import org.openyu.socklet.message.service.MessageService;
import org.openyu.socklet.message.service.ProtocolService;
import org.openyu.socklet.message.vo.Message;
import org.openyu.mix.role.service.RoleService;
import org.openyu.mix.role.service.RoleSetService;
import org.openyu.mix.role.service.impl.RoleServiceImpl;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.account.service.AccountService;
import org.openyu.mix.account.service.impl.AccountServiceImpl;
import org.openyu.mix.account.vo.Account;
import org.openyu.mix.app.AppTestSupporter;
import org.openyu.mix.flutter.vo.CareerType;
import org.openyu.mix.flutter.vo.RaceType;
import org.openyu.mix.flutter.vo.FaceType;
import org.openyu.mix.flutter.vo.GenderType;
import org.openyu.mix.flutter.vo.HairType;

public class AppTestSupporter extends BaseTestSupporter {
	// --------------------------------------------------
	// 本系統共用的service
	// --------------------------------------------------
	/**
	 * db
	 */
	protected static HibernateTransactionManager txManager;

	protected static SessionFactory sessionFactory;

	/**
	 * log db
	 */
	protected static HibernateTransactionManager logTxManager;

	protected static SessionFactory logSessionFactory;

	/**
	 * 訊息
	 */
	protected static MessageService messageService;

	/**
	 * 協定
	 */
	protected static ProtocolService protocolService;

	// ---------------------------------------------------
	// /**
	// * 帳號服務-1
	// */
	// protected static AccountService accountService;
	//
	// /**
	// * 道具服務-2
	// */
	// protected static ItemService itemService;
	//
	// /**
	// * 角色服務-3
	// */
	// protected static RoleService roleService;

	/**
	 * 角色集合服務
	 */
	protected static RoleSetService roleSetService;

	/**
	 * 聊天角色集合服務
	 */
	protected static ChatSetService chatSetService;

	/**
	 * 客戶端
	 */
	protected static JavaConnector javaConnector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"applicationContext-init.xml",//
				"META-INF/applicationContext-commons-core.xml",//
				"applicationContext-message.xml",//
				"applicationContext-database.xml",//
				"applicationContext-database-log.xml",//
				// "applicationContext-schedule.xml",// 排程
				"META-INF/applicationContext-socklet-core.xml",//
				"org/openyu/mix/app/applicationContext-app.xml",//
		// biz
		// "org/openyu/mix/account/applicationContext-account.xml",//
		// "org/openyu/mix/item/applicationContext-item.xml",//
		// "org/openyu/mix/role/applicationContext-role.xml",//
				});
		// ---------------------------------------------------
		initialize();
		// ---------------------------------------------------
	}

	protected static void initialize() {
		txManager = (HibernateTransactionManager) applicationContext
				.getBean("txManager");
		sessionFactory = (SessionFactory) applicationContext
				.getBean("sessionFactory");
		logTxManager = (HibernateTransactionManager) applicationContext
				.getBean("logTxManager");
		logSessionFactory = (SessionFactory) applicationContext
				.getBean("logSessionFactory");
		//
		messageService = (MessageService) applicationContext
				.getBean("messageService");
		protocolService = (ProtocolService) applicationContext
				.getBean("protocolService");
		// ---------------------------------------------------
		javaConnector = new JavaConnectorImpl(CoreModuleType.class,
				CoreMessageType.class, protocolService);
		javaConnector.setReceiver(receiver);

		// // 帳號
		// accountService = (AccountService) applicationContext
		// .getBean("accountService");
		// // 道具
		// itemService = (ItemService)
		// applicationContext.getBean("itemService");
		// // 角色
		// roleService = (RoleService)
		// applicationContext.getBean("roleService");

		// 角色集合
		roleSetService = (RoleSetService) applicationContext
				.getBean("roleSetService");
		// 聊天角色集合
		chatSetService = (ChatSetService) applicationContext
				.getBean("chatSetService");
	}

	// --------------------------------------------------

	public static class BeanTest extends AppTestSupporter {
		@Test
		public void txManager() {
			System.out.println(txManager);
			assertNotNull(txManager);
		}

		@Test
		public void sessionFactory() {
			System.out.println(sessionFactory);
			assertNotNull(sessionFactory);
		}

		@Test
		public void logTxManager() {
			System.out.println(logTxManager);
			assertNotNull(logTxManager);
		}

		@Test
		public void logSessionFactory() {
			System.out.println(logSessionFactory);
			assertNotNull(logSessionFactory);
		}

		// --------------------------------------------------

		@Test
		public void messageService() {
			System.out.println(messageService);
			assertNotNull(messageService);
		}

		@Test
		public void protocolService() {
			System.out.println(protocolService);
			assertNotNull(protocolService);
		}

		@Test
		public void javaConnector() {
			System.out.println(javaConnector);
			assertNotNull(javaConnector);
		}

		// @Test
		// public void accountService() {
		// System.out.println(accountService);
		// assertNotNull(accountService);
		// }
		//
		// @Test
		// public void itemService() {
		// System.out.println(itemService);
		// assertNotNull(itemService);
		// }
		//
		// @Test
		// public void roleService() {
		// System.out.println(roleService);
		// assertNotNull(roleService);
		// }

		@Test
		public void roleSetService() {
			System.out.println(roleSetService);
			assertNotNull(roleSetService);
		}

		@Test
		public void chatSetService() {
			System.out.println(chatSetService);
			assertNotNull(chatSetService);
		}
	}

	/**
	 * 模擬角色
	 *
	 * @return
	 */
	public static Role mockRole() {
		final String ROLE_ID = "TEST_ROLE";
		final String ROLE_NAME = "測試角色";
		//
		Role result = mockRole(ROLE_ID, ROLE_NAME);
		Account account = mockAccount();
		result.setAccountId(account.getId());
		return result;
	}

	/**
	 * 模擬角色2
	 *
	 * @return
	 */
	public static Role mockRole2() {
		final String ROLE_ID = "TEST_ROLE_2";
		final String ROLE_NAME = "測試角色_2";
		//
		Role result = mockRole(ROLE_ID, ROLE_NAME);
		Account account = mockAccount2();
		result.setAccountId(account.getId());
		return result;
	}

	/**
	 * 模擬角色3
	 *
	 * @return
	 */
	public static Role mockRole3() {
		final String ROLE_ID = "TEST_ROLE_3";
		final String ROLE_NAME = "測試角色_3";
		Role result = mockRole(ROLE_ID, ROLE_NAME);
		Account account = mockAccount3();
		result.setAccountId(account.getId());
		return result;
	}

	/**
	 * 模擬角色
	 *
	 * @param roleId
	 * @param name
	 * @return
	 */
	public static Role mockRole(String roleId, String name) {

		Role result = null;
		// 1.spring會造成 circular reference
		// result = roleService.createRole(roleId, name, RaceType.RONG,
		// CareerType.WARRIOR_1, GenderType.FEMALE, HairType.SHORT,
		// FaceType.CUTE);

		// 2.用此方式建構
		// result = new RoleImpl();
		// result.setId(roleId);
		// result.setName(name);
		// result.setValid(true);
		// //
		// Industry industry = IndustryCollector.getInstance().getIndustry(
		// RaceType.RONG, CareerType.WARRIOR_1);
		// result.setIndustryId(industry.getId());// 行業
		// result.setLevel(1);// 等級,有事件觸發
		// result.setExp(0L);// 經驗
		// result.setGold(0L);// 金幣
		// result.setValid(true);// 啟用
		// result.setVipType(VipType._0);// vip=0,有事件觸發
		// // 外觀
		// result.setGenderType(GenderType.FEMALE);
		// result.setHairType(HairType.SHORT);
		// result.setFaceType(FaceType.CUTE);
		//
		// // 地圖
		// result.setMap("map01");
		//
		// // 座標
		// result.setCoordinate(300 * 32, 200 * 32);
		//
		// //
		// result.setEnterTime(System.currentTimeMillis());// 上線時間
		// result.setAcceptor(randomSlave());// acceptor
		// result.setConnected(true);// 是否已連線

		// 3.改用new的方式,不使用spring
		RoleService roleService = new RoleServiceImpl();
		result = roleService.createRole(roleId, name, RaceType.RONG,
				CareerType.WARRIOR_1, GenderType.FEMALE, HairType.SHORT,
				FaceType.CUTE);
		// 加到mem
		roleSetService.addRole(result);
		return result;
	}

	/**
	 * 模擬帳戶
	 *
	 * @return
	 */
	public static Account mockAccount() {
		final String ACCOUNT_ID = "TEST_ACCOUNT";
		final String ACCOUNT_NAME = "測試帳戶";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 *
	 * @return
	 */
	public static Account mockAccount2() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_2";
		final String ACCOUNT_NAME = "測試帳戶_2";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 *
	 * @return
	 */
	public static Account mockAccount3() {
		final String ACCOUNT_ID = "TEST_ACCOUNT_3";
		final String ACCOUNT_NAME = "測試帳戶_3";
		return mockAccount(ACCOUNT_ID, ACCOUNT_NAME);
	}

	/**
	 * 模擬帳戶
	 *
	 * @param accountId
	 * @return
	 */
	public static Account mockAccount(String accountId, String name) {
		Account result = null;
		// 1.spring會造成 circular reference
		// Account result = accountService.createAccount(accountId, name);

		// 2.用此方式建構
		// result = new AccountImpl();
		// result.setId(accountId);
		// result.setName(name);
		// result.setValid(true);
		// result.setCoin(100);
		// result.setAccuCoin(5000);// vip6

		// 3.改用new的方式,不使用spring
		AccountService accountService = new AccountServiceImpl();
		result = accountService.createAccount(accountId, name);

		return result;
	}

	/**
	 * 模擬道具,裝備
	 *
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public static Item mockItem() {
		final String ITEM_ID = "T_POTION_HP_G001";
		return mockItem(ITEM_ID, 1);
	}

	/**
	 * 模擬道具,裝備
	 *
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public static Item mockItem(String itemId, int amount) {
		Item result = null;

		// 1.spring會造成 circular reference
		// Item result = itemService.createItem(itemId, amount);

		// 2.用此方式建構
		// result = new ThingImpl();
		// result.setId(itemId);
		// result.setUniqueId("T_" + randomUnique());
		// result.setAmount(amount);

		// 3.改用new的方式,不使用spring
		ItemService itemService = new ItemServiceImpl();
		result = itemService.createItem(itemId, amount);
		return result;
	}

	/**
	 * 訊息
	 * 
	 * @param message
	 */
	public static String toString(Message message) {
		StringBuilder buff = new StringBuilder();

		// message
		buff.append("from [" + message.getSender() + "]");

		// message.append(categoryType + ", ");

		// //來源模組
		// if (message.getSrcModule() != null)
		// {
		// //message.append(", (" + srcModule.intValue() + ") " + srcModule);
		// buff.append(", " + message.getSrcModule());
		// }
		// 訊息編號,類別
		if (message.getMessageType() != null) {
			buff.append(" (" + message.getMessageType().getValue() + ") "
					+ message.getMessageType());
		}

		// content
		if (message.getSender() == null) {
			StringBuilder content = new StringBuilder();
			List<byte[]> contents = message.getContents();
			for (int i = 0; i < contents.size(); i++) {
				Object object = null;
				//
				Class<?> clazz = message.getClass(i);
				if (BooleanEnum.class.equals(clazz)
						|| Boolean.class.equals(clazz)
						|| boolean.class.equals(clazz)) {
					object = message.getBoolean(i);
				}
				if (CharEnum.class.equals(clazz)
						|| Character.class.equals(clazz)
						|| char.class.equals(clazz)) {
					object = message.getChar(i);
				} else if (StringEnum.class.equals(clazz)
						|| String.class.equals(clazz)) {
					object = message.getString(i);
				} else if (IntEnum.class.equals(clazz)
						|| Integer.class.equals(clazz)
						|| int.class.equals(clazz)) {
					object = message.getInt(i);
				} else if (LongEnum.class.equals(clazz)
						|| Long.class.equals(clazz) || long.class.equals(clazz)) {
					object = message.getLong(i);
				} else if (FloatEnum.class.equals(clazz)
						|| Float.class.equals(clazz)
						|| float.class.equals(clazz)) {
					object = message.getFloat(i);
				} else if (DoubleEnum.class.equals(clazz)
						|| Double.class.equals(clazz)
						|| double.class.equals(clazz)) {
					object = message.getDouble(i);
				} else if (byte[].class.equals(clazz)) {
					object = message.getByteArray(i);
				}
				//
				content.append(object);
				if (i < contents.size() - 1) {
					content.append(StringHelper.COMMA);
				}
			}

			// 內容,TEST_ROLE,測試角色,50000,1,200000,0
			if (content.length() > 0) {
				buff.append(StringHelper.LF);
				buff.append(content);
			}
		}
		//
		return buff.toString();
	}

	/**
	 * 顯示訊息
	 * 
	 * @param message
	 */
	public static void print(Message message) {
		System.out.println(toString(message));
	}

	/**
	 * 訊息接收者
	 */
	protected static MessageReceiver receiver = new MessageReceiver();

	public static class MessageReceiver extends GenericReceiverSupporter {

		private static final long serialVersionUID = 7239217136206387301L;

		public void receive(Message message) {
			print(message);
		}
	}

	/**
	 * 顯示道具
	 * 
	 * @param item
	 */
	public static void printItem(Item item) {
		if (item != null) {
			StringBuilder buff = new StringBuilder();
			buff.append(item.getId());
			buff.append(", ");
			buff.append(item.getUniqueId());
			buff.append(", ");
			buff.append(item.getName());
			buff.append(", ");
			buff.append(item.getAmount());
			// 裝備
			if (item instanceof Equipment) {
				Equipment equipment = (Equipment) item;
				buff.append(", ");
				buff.append(equipment.getEnhanceLevel());
				for (Map.Entry<AttributeType, Attribute> entry : equipment
						.getAttributeGroup().getAttributes().entrySet()) {
					// 屬性
					buff.append(", ");
					buff.append(entry.getKey());
					buff.append(", ");
					buff.append(entry.getValue().getFinal());
				}
			}
			// 土地
			else if (item instanceof Land) {
				Land land = (Land) item;
				buff.append(", ");
				buff.append(land.getEnhanceLevel());
			}
			System.out.println(buff);
		} else {
			System.out.println(item);
		}
	}

	public static String randomSlave() {
		return "slave" + randomInt(1, 10);
	}

}
