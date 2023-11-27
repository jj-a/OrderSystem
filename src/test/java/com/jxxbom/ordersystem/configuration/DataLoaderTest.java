package com.jxxbom.ordersystem.configuration;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataLoaderTest {

    @Test
    public void readFile() {

    boolean canRead = new File("classpath:/data.csv").canRead();
    System.out.println(canRead);

    // assert new File("./resources/data.csv").canRead();

        
    }
}
