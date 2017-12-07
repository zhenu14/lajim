package com.smart.lajim.service;

import com.smart.lajim.dao.LoginLogDao;
import com.smart.lajim.dao.UserDao;
import com.smart.lajim.domain.LoginLog;
import com.smart.lajim.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;

    public User createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    public User findOne(Long userId) {
        return userDao.findOne(userId);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }
    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        User user =findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findRoles(user.getRoleIds().toArray(new Long[0]));
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        User user =findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findPermissions(user.getRoleIds().toArray(new Long[0]));
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }





//    private LoginLogDao loginLogDao;
//
//    @Autowired
//    void setLoginLogDao(LoginLogDao loginLogDao){
//        this.loginLogDao = loginLogDao;
//    }
//
//    public boolean hasMatchUser(String userName,String password){
//        int count = userDao.getMatchCount(userName,password);
//        return count > 0;
//    }
//
//    public User findUserByUserName(String UserName){
//        return userDao.findUserByUserName(UserName);
//    }
//
//    //事务注解 运行在事务环境中--在Spring事务管理器拦截切入表达式接入了@Transactional过滤
//    @Transactional
//    public void loginSuccess(User user){
//        user.setCredits(5 + user.getCredits());
//        LoginLog loginLog = new LoginLog();
//        loginLog.setUserId(user.getUserId());
//        loginLog.setIp(user.getLastIp());
//        loginLog.setLoginDate(user.getLastVisit());
//        userDao.updateLoginInfo(user);
//        loginLogDao.insertLoginLog(loginLog);
//    }
}
