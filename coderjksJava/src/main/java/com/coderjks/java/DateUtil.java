package com.coderjks.java;

import org.junit.Test;

import java.util.Date;

public class DateUtil {
    @Test
    public void dateFormatTest() {
        long a = 1562197502L * 1000L;
        Date date = new Date(a);
        System.out.println(date);
    }
}
