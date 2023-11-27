package com.jxxbom.ordersystem.exception;

/**
 * 존재하지 않는 상품번호를 입력했을 때 발생하는 오류
 */
public class NotExistException extends Exception {
    public NotExistException() {
        super();
    }

    public NotExistException(String message) {
        super(message);
    }
}
