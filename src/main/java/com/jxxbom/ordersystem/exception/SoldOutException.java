package com.jxxbom.ordersystem.exception;

/**
 * 재고 부족으로 발생하는 오류
 */
public class SoldOutException extends Exception {
    public SoldOutException() {
        super();
    }

    public SoldOutException(String message) {
        super(message);
    }
}
