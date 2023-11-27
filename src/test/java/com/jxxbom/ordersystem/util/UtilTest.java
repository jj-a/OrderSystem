package com.jxxbom.ordersystem.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class UtilTest {

    @Test
    @DisplayName("readString() Test")
    void readString() {
        String str = InputUtil.readString();
        System.out.println(str);
    }

    @Test
    void test() {
        System.out.println("test");
    }

}
