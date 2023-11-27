package com.jxxbom.ordersystem.configuration;

import java.io.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import redis.embedded.RedisServer;

// @Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.maxmemory}")
    private String redisMaxMemory;

    private RedisServer redisServer;

    /**
     * Redis server 시작
     * 
     * @throws IOException
     */
    @PostConstruct
    public void startRedis() throws IOException {
        int port = isRedisRunning() ? findAvailablePort() : redisPort;
        redisServer = RedisServer.builder()
            .port(port)
            .setting("maxmemory " + redisMaxMemory)
            .build();
        redisServer.start();
    }

    /**
     * Redis server 종료
     */
    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }

    /**
     * 사용 가능한 port 확인
     * @return
     * @throws IOException
     */
    public int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    /**
     * 해당 port를 사용중인 프로세스를 확인하는 sh 실행
     * 
     * @param redisPort
     * @return
     * @throws IOException
     */
    private Process executeGrepProcessCommand(int redisPort) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", redisPort);
        String[] shell = {"/bin/sh", "-c", command};

        return Runtime.getRuntime().exec(shell);
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     * 
     * @param process
     * @return
     */
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
        }
        
        return StringUtils.hasText(pidInfo.toString());
    }
}