package com.smart.lajim.service;

import com.smart.lajim.dao.LoginLogDao;
import com.smart.lajim.dao.UserDao;
import com.smart.lajim.domain.LoginLog;
import com.smart.lajim.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired void setLoginLogDao(LoginLogDao loginLogDao){
        this.loginLogDao = loginLogDao;
    }

    public boolean hasMatchUser(String userName,String password){
        int count = userDao.getMatchCount(userName,password);
        return count > 0;
    }

    public User findUserByUserName(String UserName){
        return userDao.findUserByUserName(UserName);
    }

    //事务注解 运行在事务环境中--在Spring事务管理器拦截切入表达式接入了@Transactional过滤
    @Transactional
    public void loginSuccess(User user){
        user.setCredits(5 + user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }
}
