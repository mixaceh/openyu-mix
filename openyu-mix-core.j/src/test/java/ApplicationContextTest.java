import java.text.Collator;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class ApplicationContextTest {

	protected static ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}

	@Test
	public void applicationContext() {
		System.out.println(applicationContext);
	}

	@Test
	public void getResource() {
		Resource resource = applicationContext
				.getResource("applicationContext.xml");
		System.out.println(resource + " exist: " + resource.exists());
	}

	@Test
	public void beans() {
		System.out.println(applicationContext);
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames, Collator.getInstance(java.util.Locale.ENGLISH));
		//
		System.out.println("=========================================");
		System.out.println("Spring beans");
		System.out.println("=========================================");
		for (int i = 0; i < beanNames.length; i++) {
			System.out.println(beanNames[i]);
		}

	}
}
