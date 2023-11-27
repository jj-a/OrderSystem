package com.jxxbom.ordersystem.order;

import java.util.Map;

import com.jxxbom.ordersystem.product.Product;

import java.util.List;

/**
 * 주문하기 메뉴
 */
public interface OrderService {
    public void main();
    public void shopping();
    public void receipt(List<Product> orderList);
    public void order(Map<String, Integer> cart);
}
