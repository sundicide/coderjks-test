package com.coderjks.java;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringTest {

    @Test
    public void splitTest() {
        String testString = "abcdefg : 12345";
        assertThat(testString.split(":")[0], is("abcdefg "));
        assertThat(testString.split(":")[0].trim(), is("abcdefg"));

        String testString2 = "abcdefg";
        assertThat(testString2.split(":")[0], is("abcdefg"));
    }
}