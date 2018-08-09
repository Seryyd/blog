package com.sergejs.blog.controller;

import com.sergejs.blog.database.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int currentPage = 0;

    @Autowired
    DBManager repository;

    @GetMapping("/")
    public String index(){
        for(int i = 0; i < 5; i++) {
            logger.info("Post id 0000" + i, repository.findById(i));
        }
        return "hello";
    }


}
