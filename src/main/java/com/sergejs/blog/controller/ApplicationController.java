package com.sergejs.blog.controller;

import com.sergejs.blog.database.PostRepository;
import com.sergejs.blog.database.PostRepositoryImpl;
import com.sergejs.blog.entity.Post;
import com.sergejs.blog.utils.PostListDateComparator;
import com.sergejs.blog.utils.PostListTitleComparator;
import com.sergejs.blog.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ApplicationController {

    static List<Post> posts = new ArrayList<Post>();
    private static int currentPage = 0;
    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static int postCountInRepository;
    private static final int POSTPERPAGE = 5;
    @Autowired
    private final PostRepository repository = new PostRepositoryImpl(jdbcTemplate);

    @PostConstruct
    public void init(){
        updatePostList();
        postCountInRepository = posts.size();
    }

    public  void updatePostList(){
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
    public String readPost(@RequestParam(value = "id") String id, Model model) {
        Post post = repository.findOne(id);
        model.addAttribute("post", post);
        model.addAttribute("page", String.valueOf(currentPage));
        return "post";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        String error = request.getParameter("error");
        if (error != null) {
            if (error.equals("noUser")) {
                model.addAttribute("msg", "Wrong username or password");
            } else if (error.equals("emptyField")) {
                model.addAttribute("msg", "Please fill out all fields");
            }
        }
        return "login";
    }
    @PostMapping("/login")
    public String verify(@RequestParam(value = "username") String user,
                         @RequestParam(value = "password") String password) {
        if (!user.equals("") && !password.equals("")){
            if (user.equals("admin") && password.equals("admin")) {
                return "redirect:/admin/?name=" + user;
            } else {
                return "redirect:/login?error=noUser";
            }
        }else{
            return "redirect:/login?error=emptyField";
        }
    }

    @GetMapping("/admin/")
    public String admin(HttpServletRequest request, Model model) {
        logger.info("post size: " + posts.size());
        model.addAttribute("name", request.getParameter("name"));
        model.addAttribute("postList", posts);
        return "adminPanel";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "post_id") String id, Model model) {
        Post post = repository.findOne(id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("description", post.getShortText());
        model.addAttribute("content", post.getLongText());
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam(value = "post_id")String id){
        int changes = repository.delete(id);
        logger.info("deleted " + changes + "posts with id:" + id);
        updatePostList();
        return "redirect:/admin/";
    }
    @GetMapping("/create")
    public String writenewPost(){
        return "create";
    }
    @PostMapping("/create")
    public String addNewPost(@RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "description") String description){
        Post newPost = new Post(Utils.getTimeStamp(), "admin", title, description, content);
        repository.save(newPost);
        updatePostList();
        return "redirect:/admin/";
    }

    @PostMapping("/edit")
    public String saveEdit(@RequestParam(value = "title") String title,
                           @RequestParam(value = "content") String content,
                           @RequestParam(value = "description") String description,
                           @RequestParam(value = "id") String id) {
        Post editedPost = new Post(id, Utils.getTimeStamp(), "admin", title, description, content);
        logger.info(editedPost.toString());
        Post a = repository.save(editedPost);
        logger.info(a.toString());
        updatePostList();
        return "redirect:/admin/";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam(value = "field")String sortField){
        logger.info("before sort: " + posts.toString());
        if (sortField.equals("date")) {
            logger.info("sorting by date");
            Collections.sort(posts, new PostListDateComparator());
        }else if (sortField.equals("title")){
            logger.info("sorting by title");
            Collections.sort(posts, new PostListTitleComparator());
        }
        logger.info("after sort: " + posts.toString());
        updatePostList();
        return "redirect:/";
    }

}
