package com.sergejs.blog.controller;

import com.sergejs.blog.database.PostRepository;
import com.sergejs.blog.database.PostRepositoryImpl;
import com.sergejs.blog.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ApplicationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int currentPage = 0;
    private JdbcTemplate jdbcTemplate;
    private int postCountInRepository;

    @Autowired
    private PostRepository repository = new PostRepositoryImpl(jdbcTemplate);


    @GetMapping("/")
    public String index(Model model){
        List<Post> posts = repository.findAll();
        postCountInRepository = posts.size();

        return "main";
    }
}
