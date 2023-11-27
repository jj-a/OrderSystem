package com.jxxbom.ordersystem.order;

import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxxbom.ordersystem.exception.NotExistException;
import com.jxxbom.ordersystem.exception.SoldOutException;
import com.jxxbom.ordersystem.product.Product;
import com.jxxbom.ordersystem.product.ProductRepository;
import com.jxxbom.ordersystem.util.InputUtil;
import com.jxxbom.ordersystem.util.LoggerFactory;

import lombok.RequiredArgsConstructor;

/**
 * 주문하기 메뉴
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void main() {
        showProductList();
        shopping();
        logger.info("주문 메뉴를 종료합니다.");
    }

    /**
     * 장바구니 담기
     * <p/>
     * 상품번호와 수량 입력 (복수 입력 가능 / 공백(space) 입력 시 장바구니 담기 종료)
     */
    public void shopping() {
        Map<String, Integer> cart = new HashMap<String, Integer>();
        boolean continueShopping = true;
        while (continueShopping) {
            String pid = "";
            Integer quantity = 0;

            // 상품번호 입력
            while (continueShopping) {
                try {
                    System.out.print("상품번호 : ");
                    pid = InputUtil.readString();
                    if (pid == null) {
                        continueShopping = false;
                        logger.info("장바구니 담기를 끝냅니다.");
                        break;
                    }
                    boolean isExists = productRepository.existsById(pid);
                    if (!isExists) {
                        throw new NotExistException();
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    logger.info(e + "");
                    System.out.println("상품번호를 제대로 입력해주세요.");
                } catch (NotExistException e) {
                    logger.info(e + "");
                    System.out.println("존재하지 않는 상품입니다.");
                }
            }

            // 수량 입력
            while (continueShopping) {
                try {
                    System.out.print("수량 : ");
                    quantity = InputUtil.readInt();
                    logger.info("\"" + quantity + "\"");
                    if (quantity == null) {
                        continueShopping = false;
                        logger.info("장바구니 담기를 끝냅니다.");
                    } else if (quantity == 0) {
                        throw new IllegalArgumentException();
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    logger.info(e + "");
                    System.out.println("수량을 제대로 입력해주세요.");
                }
            }

            // 장바구니에 상품 담기
            if (continueShopping) {
                if (quantity != null && cart.containsKey(pid)) {
                    cart.replace(pid, cart.get(pid) + quantity);
                } else {
                    cart.put(pid, quantity);
                }
            }
        }

        order(cart);
        logger.info("shopping 끝");
    }

    // private void checkInput(String input) {
    // // String input = Util.readString();
    // if (input.equals(" ") || input.equals(null)) {

    /**
     * 상품 리스트 출력
     */
    public void showProductList() {
        List<Product> productList = productRepository.findAll();
        System.out.println("상품번호\t상품명\t\t\t\t판매가격\t재고수");
        productList.forEach(System.out::println);

    }

    /**
     * 주문하기
     * <p/>
     * 품절 상품 확인 후 주문 내역을 출력
     * 
     * @param cart
     */
    @Transactional
    public void order(Map<String, Integer> cart) {
        logger.info(cart.toString());
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }
        // 품절 상품 확인
        boolean status = true;
        List<Product> orderList = new ArrayList<Product>();
        for (String pid : cart.keySet()) {
            int quantity = cart.get(pid);
            // Optional<Product> optionalProduct = productRepository.findById(pid);
            // Product product = optionalProduct.get();
            Product product = productRepository.findById(pid).orElseThrow();
            try {
                if (product.getQuantity() < quantity) {
                    throw new SoldOutException();
                }

                // 장바구니에 상품을 추가하고 재고에서 구매 수량만큼 제거
                Product cartToProduct = Product.builder()
                        .pid(pid)
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(quantity)
                        .build();
                orderList.add(cartToProduct);
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);

            } catch (SoldOutException e) {
                logger.info(e + "");
                System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
                status = false;
                return;
            }
        }
        // 주문 내역
        if (status) {
            receipt(orderList);
        }
    }

    /**
     * 주문 내역 출력
     */
    public void receipt(List<Product> orderList) {
        DecimalFormat df = new DecimalFormat("###,###원");
        final long DEFAULT_DELEVERY_CHARGE = 2500;
        long totalPrice = 0L;
        long deliveryCharge = 0L;

        System.out.println("주문 내역:");
        System.out.println("-------------------------");
        for (Product item : orderList) {
            totalPrice += item.getPrice() * item.getQuantity();
            System.out.print(item.getName() + " - ");
            System.out.println(item.getQuantity() + "개");
        }
        System.out.println("-------------------------");
        System.out.println("주문금액: " + df.format(totalPrice));
        if (totalPrice < 50000) {
            deliveryCharge += DEFAULT_DELEVERY_CHARGE;
            System.out.println("배송비: " + deliveryCharge);
        }
        System.out.println("-------------------------");
        System.out.print("지불금액: ");
        String formatPrice = df.format(totalPrice + deliveryCharge);
        System.out.println(formatPrice);
        System.out.println("-------------------------");
    }

}
