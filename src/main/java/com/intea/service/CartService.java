package com.intea.service;

import com.intea.domain.dto.CartReqDTO;
import com.intea.domain.dto.CartResDTO;
import com.intea.domain.dto.PagingDTO;
import com.intea.domain.entity.Cart;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.User;
import com.intea.domain.repository.CartRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.CheckReviewAuthorityException;
import com.intea.exception.NotExistCartException;
import com.intea.exception.NotExistUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class CartService {
    private UserRepository userRepo;
    private ProductRepository productRepo;
    private CartRepository cartRepo;

    public void getCart(CartReqDTO cartReqDTO) {
        Optional<User> user = userRepo.findById(cartReqDTO.getUser_id());

        if(!user.isPresent()) {
            throw new NotExistUserException("존재하지 않는 회원입니다.");
        }

        Optional<Product> productOptional = productRepo.findById(cartReqDTO.getProduct_id());

        if(!productOptional.isPresent()) {
            throw new NotExistUserException("존재하지 않는 상품입니다.");
        }

        Product product = productOptional.get();

        cartRepo.save(Cart.builder()
                .user(user.get())
                .product(product)
                .build());
    }

    public HashMap<String, Object> getCartList(Long user_id, int page, Pageable pageable) {
        int realPage = page - 1;
        pageable = PageRequest.of(realPage, 5);

        Page<Cart> cartList = cartRepo.findAllByUser_idOrderByInsert_timeDesc(user_id, pageable);

        if(cartList.getTotalElements() > 0) {
            List<CartResDTO> cartResDTOList = new ArrayList<>();

            for(Cart cart : cartList) {
                cartRepo.add(cart.toResponseDTO());
            }

            PageImpl<CartResDTO> cartLists = new PageImpl<>(cartResDTOList, pageable, cartList.getTotalElements());

            PagingDTO cartPagingDTO = new PagingDTO();
            cartPagingDTO.setPagingInfo(cartLists);

            List<Cart> carts = cartRepo.findAllByUser_id(user_id);

            int chkoutPrice = 0;

            for(Cart cart : carts) {
                chkoutPrice += cart.getProduct().getPrice() * cart.getCount();
            }

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("cartList", cartLists);
            resultMap.put("cartPagingDTO", cartPagingDTO);
            resultMap.put("chkoutPrice", chkoutPrice);

            return resultMap;
        }
        return null;
    }

    public void delCart(Long id) {
        Optional<Cart> cartOpt = cartRepo.findById(id);

        if(!cartOpt.isPresent()) {
            throw new NotExistCartException("장바구니에 상품이 존재하지 않습니다.");
        }

        cartRepo.delete(cartOpt.get());
    }

    public int chkReviewAuthority(HashMap<String, Object> paramMap) {
        Long user_id = Long.parseLong(paramMap.get("userId").toString());
        Long product_id = Long.parseLong(paramMap.get("productId").toString());

        List<Cart> cartList = cartRepo.findAllByUser_idAndProduct_id(user_id, product_id);

        if (cartList.size() > 0) {
            for (Cart cart : cartList) {
                if (cart.getorders() != null) {
                    return 1;
                }
            }
        }

        throw new CheckReviewAuthorityException("해당상품 결제를 완료한 회원만 리뷰를 작성할 수 있습니다.");
    }
}
