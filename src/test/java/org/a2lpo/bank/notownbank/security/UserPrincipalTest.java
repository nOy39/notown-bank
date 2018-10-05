package org.a2lpo.bank.notownbank.security;

import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPrincipalTest {


    private UserPrincipal userPrincipal;

    @Test
    public void create() {
        User user = new User();
        user.setId(1l);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setEmail("email@imail.com");
        UserPrincipal userPrincipal = this.userPrincipal.create(user);
        Assert.assertNotNull(userPrincipal);
    }
}
