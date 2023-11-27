package com.jxxbom.ordersystem;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.jxxbom.ordersystem.menu.Main;
import com.jxxbom.ordersystem.util.LoggerFactory;

import java.net.Socket;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan
public class OrderSystemApplication {
    protected static Logger logger = LoggerFactory.getLogger(OrderSystemApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderSystemApplication.class, args);
        Main mainMenu = context.getBean(Main.class);

        System.out.println("Hello, Customer!");

        try {
            Socket socket = new Socket("localhost", 6379);
            logger.info("Embedded Redis is running : 6379");
            socket.close();
        } catch (IOException e) {
            logger.error("Embedded Redis is NOT running : 6379");
        }

        mainMenu.main();
        context.close();
    }

}