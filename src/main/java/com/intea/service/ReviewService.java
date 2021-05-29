package com.intea.service;

import com.intea.domain.dto.PagingDto;
import com.intea.domain.dto.ReviewRequestDto;
import com.intea.domain.dto.ReviewResponseDto;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.Review;
import com.intea.domain.entity.User;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.ReviewRepository;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.NotExistProductException;
import com.intea.exception.NotExistReviewException;
import com.intea.exception.NotExistUserException;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepo;
    private final ReviewRepository reviewRepo;
    private final ProductRepository productRepo;

    public String insReview(Long product_id) {
        Product product = productRepo.findById(product_id).orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));
        return product.getProductNm();
    }

    @Transactional
    public void makeReview(ReviewRequestDto reviewRequestDto) {
        User user = userRepo.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));
        Product product = productRepo.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));

        reviewRepo.save(Review.builder()
                .user(user)
                .product(product)
                .title(reviewRequestDto.getTitle())
                .content(reviewRequestDto.getContent())
                .rate(reviewRequestDto.getRate())
                .build());

        productRepo.save(product);
    }

    public HashMap<String, Object> getReviewList(Long product_id, int page) {
        int realPage = page - 1;
        Pageable pageable = PageRequest.of(realPage, 3);

        Page<Review> reviewPage = reviewRepo.findAllByProductIdOrderByInsertTimeDesc(product_id, pageable);

        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewPage) {
            reviewResponseDtoList.add(review.toResponseDto());
        }

        PageImpl<ReviewResponseDto> reviewResponseDtos
                = new PageImpl<>(reviewResponseDtoList, pageable, reviewPage.getTotalElements());

        PagingDto reviewPagingDto = new PagingDto();
        reviewPagingDto.setPagingInfo(reviewResponseDtos);

        // 평점 평균 조회해서 맵에 추가
        Product product = productRepo.findById(product_id)
                .orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewList", reviewResponseDtos);
        resultMap.put("reviewPagingDTO", reviewPagingDto);

        return resultMap;
    }

    public ReviewResponseDto.ReviewDetailResponseDto getReviewDetail(Long id) {
        Review review = reviewRepo.findById(id).orElseThrow(()
                -> new NotExistReviewException("존재하지 않는 게시글입니다."));;

        return ReviewResponseDto.ReviewDetailResponseDto.builder()
                .content(review.getContent())
                .build();
    }
}
