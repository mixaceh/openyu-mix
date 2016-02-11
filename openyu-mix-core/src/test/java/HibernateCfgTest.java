import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class HibernateCfgTest extends BaseTestSupporter {

	private static Configuration configuration;

	private static SessionFactory sessionFactory;

	protected static void createConfiguration() {
		if (configuration != null) {
			return;
		}
		configuration = new Configuration().configure("hibernate.cfg.xml");

		System.out.println("configuration: " + configuration);
		System.out.println("configuration.getProperties(): "
				+ configuration.getProperties());	}

	@Test
	public void configuration() {
		createConfiguration();
		//
		System.out.println(configuration);
		assertNotNull(configuration);
	}

	protected static void createSessionFactory() {
		if (sessionFactory != null) {
			return;
		}
		createConfiguration();
		//
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		System.out.println(sessionFactory);
	}

	@Test
	public void sessionFactory() throws Exception {
		createSessionFactory();
		//
		System.out.println(sessionFactory);
		assertNotNull(sessionFactory);
	}

	@Test
	public void openSession() throws Exception {
		createSessionFactory();
		//
		Session session = sessionFactory.openSession();
		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				System.out.println("connection: " + connection);
				System.out.println("autoCommit: " + connection.getAutoCommit());
				System.out.println("transactionIsolation: "
						+ connection.getTransactionIsolation());
			}
		});
		System.out.println("flushMode: " + session.getFlushMode());
		System.out.println(session);
		assertNotNull(session);
	}
}
