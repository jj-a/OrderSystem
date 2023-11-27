package com.jxxbom.ordersystem.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;

/**
 * "상품"에 대한 Entity 정의
 */
@Entity
@RedisHash("product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    private String pid;
    private String name;
    private Long price;
    private Integer quantity;

    @Override
    public String toString() {
        List<String> values = Arrays.asList(pid+"\t", name, String.valueOf(price), String.valueOf(quantity));
        String str = values.stream().collect(Collectors.joining("\t"));
        return str;
    }

}