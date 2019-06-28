package com.coderjks.java;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringTest {
    @Test
    public void splitTest() {
        String testString = "abcdefg : 12345";
        MatcherAssert.assertThat(testString.split(":")[0], CoreMatchers.is("abcdefg "));
        MatcherAssert.assertThat(testString.split(":")[0].trim(), CoreMatchers.is("abcdefg"));

        String testString2 = "abcdefg";
        MatcherAssert.assertThat(testString2.split(":")[0], CoreMatchers.is("abcdefg"));
    }
}
