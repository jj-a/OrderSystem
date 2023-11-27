package com.jxxbom.ordersystem.util;

import org.slf4j.Logger;

public class LoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return (Logger) org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return (Logger) org.slf4j.LoggerFactory.getLogger(name);
    }

    public static Logger getDefaultLogger() {
        return getLogger("DEFAULT");
    }
}
