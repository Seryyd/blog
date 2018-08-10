package com.sergejs.blog.utils;

import com.sergejs.blog.entity.Post;

import java.util.Comparator;

public class PostListDateComparator implements Comparator<Post> {

    @Override
    public int compare(Post post1, Post post2) {
        return  post1.getTimestamp().compareTo(post2.getTimestamp());
    }
}
