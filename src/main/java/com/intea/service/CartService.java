package com.intea.service;

import com.intea.domain.dto.CartRequestDto;
import com.intea.domain.dto.CartResponseDto;
import com.intea.domain.dto.PagingDto;
import com.intea.domain.entity.Cart;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.ProductDisPrice;
import com.intea.domain.entity.User;
import com.intea.domain.repository.CartRepository;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.CheckReviewAuthorityException;
import com.intea.exception.NotExistCartException;
import com.intea.exception.NotExistUserException;
import com.intea.exception.ProductLimitCountException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CartService {
    private UserRepository userRepo;
    private ProductRepository productRepo;
    private CartRepository cartRepo;

    //카트담기
    public void getCart(CartRequestDto cartRequestDto) {
        Optional<User> user = userRepo.findById(cartRequestDto.getUserId());

        if(!user.isPresent()) {
            throw new NotExistUserException("존재하지 않는 회원입니다.");
        }

        Optional<Product> productOptional = productRepo.findById(cartRequestDto.getProductId());

        if(!productOptional.isPresent()) {
            throw new NotExistUserException("존재하지 않는 상품입니다.");
        }

        Product product = productOptional.get();

        if(product.getLimitCnt() < cartRequestDto.getCount()) throw new ProductLimitCountException("재고가 없는 상품입니다.");

        cartRepo.save(Cart.builder()
                .user(user.get())
                .product(product)
                .build());
    }

    //리스트 뿌리기
    @Transactional
    public HashMap<String, Object> getCartList(UUID userId, int page, Pageable pageable) {
        int realPage = page - 1;
        pageable = PageRequest.of(realPage, 5);

        Page<Cart> cartList = cartRepo.findAllByUserIdAndUseYnOrderByInsertTimeDesc(userId, 'Y', pageable);

        if(cartList.getTotalElements() > 0) {
            List<CartResponseDto> cartResDtoList = new ArrayList<>();

            for(Cart cart : cartList) {
                cartResDtoList.add(cart.toResponseDto(getDisPrice(cart)));
            }

            PageImpl<CartResponseDto> cartLists = new PageImpl<>(cartResDtoList, pageable, cartList.getTotalElements());

            PagingDto cartPagingDto = new PagingDto();
            cartPagingDto.setPagingInfo(cartLists);

            List<Cart> carts = cartRepo.findAllByUserIdAndUseYnOrderByInsertTimeDesc(userId, 'Y');
            int chkoutPrice = 0;
            List<Long> cartIdList = new ArrayList<>();

            for(Cart cart : carts) {
                cartIdList.add(cart.getId());
                int salePrice = (int)((((float) 100 - (float)getDisPrice(cart)) / (float)100) * cart.getProduct().getPrice());
                chkoutPrice += cart.getProduct().getPrice() * cart.getCount();
            }

            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("cartList", cartLists);
            resultMap.put("cartPagingDTO", cartPagingDto);
            resultMap.put("chkoutPrice", chkoutPrice);

            return resultMap;
        }
        return null;
    }

    //카트 삭제
    @Transactional
    public void delCart(Long id) {
        Optional<Cart> cartOpt = cartRepo.findById(id);

        if(!cartOpt.isPresent()) {
            throw new NotExistCartException("존재하지 않는 장바구니 입니다.");
        }

        cartRepo.delete(cartOpt.get());
    }

    //리뷰 권한 체크
    @Transactional
    public int chkReviewAuthority(HashMap<String, Object> paramMap) {
        UUID userId = UUID.fromString(paramMap.get("userId").toString());
        Long productId = Long.parseLong(paramMap.get("productId").toString());

        List<Cart> cartList = cartRepo.findAllByUserIdAndProductId(userId, productId);

        if (cartList.size() > 0) {
            for (Cart cart : cartList) {
                if (cart.getOrders() != null) {
                    return 1;
                }
            }
        }

        throw new CheckReviewAuthorityException("해당상품을 주문하신 회원만 리뷰를 작성할 수 있습니다.");
    }

    @Transactional
    public int getDisPrice(Cart cart) {
        int disPrice = 0;

        if(cart.getProduct().getProductDisPrcList().size() > 0) {
            List<ProductDisPrice> disprcList
                    = cart.getProduct().getProductDisPrcList().stream().filter(productDisPrice -> LocalDateTime.now().isAfter(productDisPrice.getStartDate())
                    && LocalDateTime.now().isBefore(productDisPrice.getEndDate())).sorted().limit(1).collect(Collectors.toList());
            if(disprcList.size() > 0) {
                disPrice = disprcList.get(0).getDisPrice();
            }
        }
        return disPrice;
    }
}
