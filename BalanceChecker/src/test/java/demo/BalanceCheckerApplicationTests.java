package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.BalanceCheckerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BalanceCheckerApplication.class)
@WebAppConfiguration
public class BalanceCheckerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
