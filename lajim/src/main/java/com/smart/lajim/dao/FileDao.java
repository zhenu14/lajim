package com.smart.lajim.dao;

import java.util.List;
import java.util.Map;

import com.smart.lajim.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean insertFile(File file) {
        try {
            String INSERT_FILE_SQL = " insert into t_files(filename,filepath,createtime,createuser)values(?,?,?,?)";
            Object args[] = {file.getFilepath(), file.getFilepath(), file.getCreatetime(), file.getCreateuser()};
            int temp = jdbcTemplate.update(INSERT_FILE_SQL, args);
            if (temp > 0) {
                System.out.println("插入成功！");
                return true;
            } else {
                System.out.println("插入失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List listAllFiles() {
        String sql = "SELECT id,filename,filepath,createtime,createuser FROM t_files ";
        RowMapper<File> rowMapper = new BeanPropertyRowMapper<File>(File.class);
        List<File> files = jdbcTemplate.query(sql, rowMapper);
        return files;
    }

    public int getFileCount() {
        String sql = "SELECT count(*) FROM t_files";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List list(String sql, int start, int limit) {
        Object[] args = new Object[]{};
        args = ArrayUtils.add(args, args.length, start);
        args = ArrayUtils.add(args, args.length, limit);
        RowMapper<File> rowMapper = new BeanPropertyRowMapper<File>(File.class);
        List<File> files = jdbcTemplate.query(sql, rowMapper,args);
        for(File file : files) {
            System.out.println(file.getCreatetime());
        }
        return files;
    }
}
