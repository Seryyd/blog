package com.sergejs.blog.database;

import com.sergejs.blog.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public interface PostRepository {

    RowMapper<Post> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Post(resultSet.getString("id"),
            resultSet.getString("time_stamp"),
            resultSet.getString("author_id"),
            resultSet.getString("title"),
            resultSet.getString("shortText"),
            resultSet.getString("longText"));

    List<Post> findAll();

    Post findOne(String id);

    Post save(Post person);

    int delete(String id);
}

