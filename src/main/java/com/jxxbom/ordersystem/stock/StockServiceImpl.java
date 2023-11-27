package com.jxxbom.ordersystem.stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxbom.ordersystem.configuration.DataLoader;
import com.jxxbom.ordersystem.product.Product;
import com.jxxbom.ordersystem.product.ProductRepository;
import com.jxxbom.ordersystem.util.LoggerFactory;

import lombok.RequiredArgsConstructor;

/**
 * 종료 메뉴
 */
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;
    private final DataLoader dataLoader;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 상품(Product)의 초기 데이터를 csv 파일로부터 읽어옴
     * @throws IOException
     */
    @Transactional
    public void readData() throws IOException {
        String[] row = null;
        boolean isHeader = true;

        List<Product> productList = new ArrayList<Product>();
        while ((row = dataLoader.nextRead()) != null) {
            // 첫번째 header row 제외
            if (isHeader) {
                isHeader = false;
                continue;
            }
            Product product = Product.builder()
                    .pid(row[0])
                    .name(row[1])
                    .price(Long.parseLong(row[2]))
                    .quantity(Integer.parseInt(row[3]))
                    .build();
            productList.add(product);
        }
        if (productList.size() > 0) {
            productRepository.saveAll(productList);
        }
    }

}
