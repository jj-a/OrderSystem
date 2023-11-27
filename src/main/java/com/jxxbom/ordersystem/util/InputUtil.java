package com.jxxbom.ordersystem.util;

import java.util.Scanner;

import org.antlr.v4.runtime.InputMismatchException;
import org.slf4j.Logger;

/**
 * 유틸 함수 정의
 */
public class InputUtil {
    static Scanner sc = new Scanner(System.in);
    protected static Logger logger = LoggerFactory.getLogger(InputUtil.class);

    /**
     * 문자열을 입력받는 메소드
     * @return 문자열. Null 리턴 가능
     * @throws IllegalArgumentException 올바른 문자열 형식이 아닌 경우 발생
     */
    public static String readString() {
        String str;
        while (true) {
            try {
                str = sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                throw new IllegalArgumentException();
            }
        }
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (str.isBlank()) {
            return null;
        }
        logger.info("input is maybe String : \"" + str + "\"");
        return str;
    }

    /**
     * 정수형(Long) 숫자값을 입력받는 메소드
     * @return 정수형 숫자. Null 리턴 가능
     * @throws IllegalArgumentException 양수 정수형 숫자가 아니거나 올바른 숫자가 아닌 경우, 문자열인 경우, 0인 경우 발생
     */
    public static Long readLong() {
        String str = readString();
        // if (" ".equals(str)) {
        //     return null;
        // }
        // if (str == null) {
        //     return ;
        // }
        String regex = "^[^0]\\d*"; // 양수인 정수를 체크하는 정규식
        if (regex.matches(str)) {
            throw new IllegalArgumentException();
        }
        Long num = Long.parseLong(str);
        logger.info("input is Long : " + num);
        return num;
    }

    /**
     * 정수형(Integer) 숫자값을 입력받는 메소드
     * @return 정수형 숫자. Null 리턴 가능
     * @throws IllegalArgumentException 양수 정수형 숫자가 아니거나 올바른 숫자가 아닌 경우, 문자열인 경우 발생
     */
    public static Integer readInt() {
        String str = readString();
        logger.info("\"" + str + "\"");
        if (str == null) {
            logger.info("test");
            return null;
        }
        String regex = "^[^0]\\d*"; // 양수인 정수를 체크하는 정규식
        if (regex.matches(str)) {
            throw new IllegalArgumentException();
        }
        Integer num = Integer.parseInt(str);
        logger.info("input is Integer : " + num);
        return num;
    }

    public static Scanner getScanner() {
        return sc;
    }

}