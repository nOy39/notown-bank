package org.a2lpo.bank.notownbank;

import org.a2lpo.bank.notownbank.controllers.rest.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotownBankApplicationTests {

	@Autowired
    private TestController testController;

	@Test
	public void test() throws Exception {
	    assertThat(testController).isNotNull();
	}

}
