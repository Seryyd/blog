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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private static List<Post> posts;

    @Autowired
    private PostRepository repository = new PostRepositoryImpl(jdbcTemplate);

    @GetMapping("/admin/")
    public String admin(HttpServletRequest request, Model model){
        model.addAttribute("name", request.getParameter("name"));
        return "adminPanel";
    }
}
