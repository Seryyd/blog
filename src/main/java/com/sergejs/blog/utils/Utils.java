package com.sergejs.blog.utils;

import java.sql.Timestamp;

public class Utils {
    public static String getTimeStamp() {
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        return timeStamp.toString();
    }
}
