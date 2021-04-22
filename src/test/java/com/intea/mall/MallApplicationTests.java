package com.intea.mall;

import com.intea.domain.entity.Orders;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.User;
import com.intea.domain.repository.OrdersRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallApplicationTests {

/*    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Test
    public void insert() {
        for(int i=0; i<10; i++) {
            User user = User.builder()
                    .memId("member" + i)
                    .email("suve1y" + i + "@gmail.com")
                    .name("인" + i)
                    .phone("0109758012" + i)
                    .postCode("4114" + i)
                    .address("대구 동구 어쩌구")
                    .deAddress("어떤 건물")
                    .picture("picture")
                    .build();
            userRepository.save(user);
        }

        for(int i=0; i<10; i++) {
            Product product = Product.builder()
                    .productNm("상품" + i)
                    .price(20000)
                    .titleImg("img")
                    .largeCat("1")
                    .smallCat("2")
                    .totalCnt(3)
                    .build();
            productRepository.save(product);
        }

       for(int i=0; i<10; i++) {
            Orders orders = Orders.builder()

        }
    }*/

    @Test
    void contextLoads() {
    }

}
