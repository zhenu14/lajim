package com.smart.lajim.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.smart.lajim.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(final User user) {
        final String sql = "insert into sys_user(organization_id, username, password, salt, role_ids, locked) values(?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, user.getOrganizationId());
                psst.setString(count++, user.getUsername());
                psst.setString(count++, user.getPassword());
                psst.setString(count++, user.getSalt());
                psst.setString(count++, user.getRoleIdsStr());
                psst.setBoolean(count++, user.getLocked());
                return psst;
            }
        }, keyHolder);
        System.out.println("创建User成功： " + sql);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public User updateUser(User user) {
        String sql = "update sys_user set organization_id=?,username=?, password=?, salt=?, role_ids=?, locked=? where id=?";
        jdbcTemplate.update(
                sql,
                user.getOrganizationId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getRoleIdsStr(), user.getLocked(), user.getId());
        return user;
    }

    public void deleteUser(Long userId) {
        String sql = "delete from sys_user where id=?";
        jdbcTemplate.update(sql, userId);
    }

    public User findOne(Long userId) {
        String sql = "select id, organization_id, username, password, salt, role_ids as roleIdsStr, locked from sys_user where id=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public List<User> findAll() {
        String sql = "select id, organization_id, username, password, salt, role_ids as roleIdsStr, locked from sys_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
    }


    public User findByUsername(String username) {
        String sql = "select id, organization_id, username, password, salt, role_ids as roleIdsStr, locked from sys_user where username=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
        if(userList.size() == 0) {
            System.out.println("没有找到用户");
            return null;
        }
        return userList.get(0);
    }


//
//    public int getMatchCount(String userName,String password){
//        String sql = "SELECT count(*) FROM t_user"
//                + " WHERE user_name = ? AND password = ? ";
//        return jdbcTemplate.queryForObject(sql,new Object[]{userName,password},Integer.class);
//    }
//
//    public User findUserByUserName(final String userName) {
//        String sqlStr = " SELECT user_id,user_name,credits "
//                + " FROM t_user WHERE user_name =? ";
//        final User user = new User();
//        jdbcTemplate.query(sqlStr, new Object[] { userName },
//                new RowCallbackHandler() {
//                    public void processRow(ResultSet rs) throws SQLException {
//                        user.setUserId(rs.getInt("user_id"));
//                        user.setUserName(userName);
//                        user.setCredits(rs.getInt("credits"));
//                    }
//                });
//        return user;
//    }
//
//    public void updateLoginInfo(User user) {
//        String UPDATE_LOGIN_INFO_SQL = " UPDATE t_user SET " +
//                " last_visit=?,last_ip=?,credits=?  WHERE user_id =?";
//        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL, new Object[] { user.getLastVisit(),
//                user.getLastIp(),user.getCredits(),user.getUserId()});
//    }
}
