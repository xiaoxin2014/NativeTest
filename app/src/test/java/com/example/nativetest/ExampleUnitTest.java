package com.example.nativetest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        String s = "[{\"RsCode\":3,\"RsMsg\":null,\"RsData\":true,\"RsNote\":null,\"RsDetail\":null}]";
        System.out.println( s.substring(1,s.length()-1));
    }
}