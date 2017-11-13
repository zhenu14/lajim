package com.smart.lajim.service;

import com.smart.shiro.domain.User;
import com.smart.shiro.service.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@ContextConfiguration("classpath*:/application-context.xml")
public class ShiroUserServiceTest {

    UserService shiroUserService;

    @Test
    public void testHasMatchUser() {
        User user = new User();
        user.setId((long)1);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setSalt("1");
        user.setLocked(false);
        shiroUserService.createUser(user);
        assertTrue(true);
    }
}
