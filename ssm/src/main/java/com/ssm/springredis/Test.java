package com.ssm.springredis;

import com.ssm.springredis.mapper.RoleMapper;
import com.ssm.springredis.pojo.Role;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
//		tesSpring();
//		testSqlSessionTemplate();
		testRoleMapper() ;
    }

    public static void tesSpring() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-springredis.xml");
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        Long id = 1L;
        String sql = "select id, rolename, note from t_role where id = " + id;
        Role role = jdbcTemplate.queryForObject(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rownum) throws SQLException {
                Role result = new Role();
                result.setId(rs.getLong("id"));
                result.setRoleName(rs.getString("rolename"));
                result.setNote(rs.getString("note"));
                return result;
            }
        });
        System.out.println(role.getRoleName());
    }

    public static void testSqlSessionTemplate() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-springredis.xml");
        //ctx为Spring IoC容器
        SqlSessionTemplate sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class);
        Role role = new Role();
        role.setRoleName("role_name_sqlSessionTemplate");
        role.setNote("note_sqlSessionTemplate");
        sqlSessionTemplate.insert("com.ssm.springredis.mapper.RoleMapper.insertRole", role);
        Long id = role.getId();
        sqlSessionTemplate.selectOne("com.ssm.springredis.mapper.RoleMapper.getRole", id);
        role.setNote("update_sqlSessionTemplate");
        sqlSessionTemplate.update("com.ssm.springredis.mapper.RoleMapper.updateRole", role);
        sqlSessionTemplate.delete("com.ssm.springredis.mapper.RoleMapper.deleteRole", id);
    }
    public static void testRoleMapper() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-springredis.xml");
        RoleMapper roleMapper = ctx.getBean(RoleMapper.class);
        Role role = roleMapper.getRole(1L);
        System.out.println(role.getRoleName());
    }
}
