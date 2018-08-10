package com.sergejs.blog.database;

import com.sergejs.blog.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public final class PostRepositoryImpl implements PostRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query("select * from posts", ROW_MAPPER);
    }

    @Override
    public Post findOne(String id) {
        Post post = null;
        try {
            return jdbcTemplate.queryForObject("select * from posts where id=?", new Object[]{id}, ROW_MAPPER);
        }catch (DataAccessException dataAccessException){
            LOGGER.debug("Couldn't find entity of type Post with id {}", id);
        }
        return post;
    }

    @Override
    public Post save(Post post) {
        if(post.getId() == null){
            String postID = UUID.randomUUID().toString();
            post.setId(postID);
            LOGGER.info("saving new Post to DB with generated id=" + postID);
             jdbcTemplate.update("insert into posts values (?, ?, ?, ?, ?, ?)",
                    post.getId(), post.getTimestamp(),
                    post.getAuthorId(),post.getTitle(),
                    post.getShortText(), post.getLongText());
        }else{
            LOGGER.info("updating Post in DB with id=" + post.getId());
             jdbcTemplate.update("update posts set time_stamp=?2, author_id=?3, title=?4, shortText=?5, longText=?6 where id=?1 ",
                    post.getId(), post.getTimestamp(),
                    post.getAuthorId(),post.getTitle(),
                    post.getShortText(), post.getLongText());
        }
        return findOne(post.getId());
    }


    @Override
    public int delete(String id) {
        LOGGER.info("Deleting Post in DB with id=" + id);
        return jdbcTemplate.update("delete from posts where id = ?", id );
    }



}
