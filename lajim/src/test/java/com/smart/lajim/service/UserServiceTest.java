package com.smart.lajim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.smart.lajim.domain.User;
import static org.testng.Assert.*;

@ContextConfiguration("classpath*:/application-context.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
    private UserService userService;

	@Test
	@Rollback(false)
    public void test1(){
	    List<Long> list = new ArrayList<Long>();
	    list.add(Long.parseLong("1"));
	    User user = new User();
	    user.setUsername("qqq");
	    user.setPassword("qqq");
	    user.setSalt("1");
	    user.setRoleIds(list);
	    user.setOrganizationId(Long.parseLong("1"));
	    user.setLocked(false);
		userService.createUser(user);
	    assertNotNull("1");
    }

//	@Test
//	public void testHasMatchUser() {
//		boolean b1 = userService.hasMatchUser("admin", "admin");
//		boolean b2 = userService.hasMatchUser("admin", "1111");
//		assertTrue(b1);
//		assertTrue(!b2);
//	}
//
//	@Test
//     public void testFindUserByUserName()throws Exception{
//        for(int i =0; i< 100;i++) {
//            User user = userService.findUserByUserName("admin");
//            assertEquals(user.getUserName(), "admin");
//        }
//    }
//
//
//	@Test
//	public void testAddLoginLog() {
//		User user = userService.findUserByUserName("admin");
//		user.setUserId(1);
//		user.setUserName("admin");
//		user.setLastIp("192.168.12.7");
//		user.setLastVisit(new Date());
//		userService.loginSuccess(user);
//	}
}
