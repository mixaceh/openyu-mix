import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.lang.RuntimeHelper;

public class ApplicationContextTest extends BaseTestSupporter {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		begTime = System.currentTimeMillis();
		// 計算所耗費的記憶體(bytes)
		RuntimeHelper.gc();
		// 原本的記憶體
		long memory = RuntimeHelper.usedMemory();
		applicationContext = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		endTime = System.currentTimeMillis();
		usedMemory = Math.max(usedMemory, (RuntimeHelper.usedMemory() - memory));
	}

	@Test
	public void applicationContext() {
		System.out.println(applicationContext);
	}

	@Test
	public void getResource() {
		Resource resource = applicationContext.getResource("applicationContext.xml");
		System.out.println(resource + " exist: " + resource.exists());
		//
		resource = applicationContext.getResource("WEB-INF/web.xml");
		System.out.println(resource + " exist: " + resource.exists());
	}

	@Test
	// count: 57, time: 0 mills., 55,889,232 bytes (55.89 MB) memory used

	// 2015/12/04
	// count: 275, time: 0 mills., 74,137,736 bytes (74.14 MB) memory used
	public void showBean() {
		printBean();
	}
}
