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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class ApplicationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int currentPage = 0;
    private JdbcTemplate jdbcTemplate;
    private static List<Post> posts;
    private static int postCountInRepository;
    private static final int POSTPERPAGE = 3;

    @Autowired
    private PostRepository repository = new PostRepositoryImpl(jdbcTemplate);

    @PostConstruct
    public void init(){
        posts = repository.findAll();
        postCountInRepository = posts.size();
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "page", required = false, defaultValue = "0") String page, Model model){
        currentPage = Integer.parseInt(page);
        List<Post> postsToShow;
        logger.info("page number to display: " + currentPage);

        if (currentPage < postCountInRepository / POSTPERPAGE){
            logger.info("getting sublist from: " + (currentPage * POSTPERPAGE) + " to " + ((currentPage * POSTPERPAGE) +POSTPERPAGE));
            postsToShow = posts.subList(currentPage * POSTPERPAGE, (currentPage * POSTPERPAGE) +POSTPERPAGE);
        }else{
            logger.info("getting sublist from: " + (currentPage * POSTPERPAGE) + " to " + posts.size());
            postsToShow = posts.subList(currentPage * POSTPERPAGE, posts.size());
        }

        if(currentPage == 0){
            model.addAttribute("previous", "false");
            model.addAttribute("next", "true");
        }else if(currentPage == postCountInRepository / POSTPERPAGE){
            model.addAttribute("next", "false");
            model.addAttribute("previous", "true");
        }else{
            model.addAttribute("next", "true");
            model.addAttribute("previous", "true");
        }
        int nextPageNum = currentPage + 1;
        int prevPageNum = currentPage -1;
        model.addAttribute("next_page", String.valueOf(nextPageNum));
        model.addAttribute("previous_page", String.valueOf(prevPageNum));
        model.addAttribute("postList", postsToShow);
        return "main";
    }

    @GetMapping("/post")
    public String readPost(@RequestParam(value = "id", required = true) String id, Model model){
        Post post = repository.findOne(id);
        model.addAttribute("post", post);
        model.addAttribute("page", String.valueOf(currentPage));
        return "post";
    }

}
