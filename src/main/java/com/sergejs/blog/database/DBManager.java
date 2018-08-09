package com.sergejs.blog.database;

import com.sergejs.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class DBManager {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Post findById(int id) {
        return jdbcTemplate.queryForObject("select * from posts where id=?",
                new Object[] { id  },
                new BeanPropertyRowMapper<Post>(Post.class));

    }


}
