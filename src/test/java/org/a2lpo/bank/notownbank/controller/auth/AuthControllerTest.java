package org.a2lpo.bank.notownbank.controller.auth;

import org.a2lpo.bank.notownbank.controllers.rest.auth.AuthController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoader() throws Exception {
        assertThat(authController).isNotNull();
    }
}
