package com.jxxbom.ordersystem.menu;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.jxxbom.ordersystem.order.OrderService;
import com.jxxbom.ordersystem.stock.StockService;
import com.jxxbom.ordersystem.util.InputUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Main {

    public final OrderService orderService;
    public final StockService stockService;

    public void main() {
        
        // 데이터 읽기
        try {
            stockService.readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 메뉴 로드
        final String text = "입력(o[order]: 주문, q[quit]: 종료)";
        while (true) {
            System.out.print(text + " : ");
            Scanner sc = InputUtil.getScanner();
            String str = sc.nextLine();
            switch (str) {
                case "o":
                case "order":
                    order();
                    break;
                case "q":
                case "quit":
                    quit();
                    break;
                default:
                    System.out.println("존재하지 않는 명령어입니다.");
                    break;
            }
        }
    }

    public void order() {
        orderService.main();
    }

    public void quit() {
        System.out.println("고객님의 주문 감사합니다.");
        System.exit(0);
    }

}
