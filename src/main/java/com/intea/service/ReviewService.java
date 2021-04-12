package com.intea.service;

import com.intea.domain.dto.ReviewReqDTO;
import com.intea.domain.dto.ReviewResDTO;
import com.intea.domain.entity.Product;
import com.intea.domain.entity.Review;
import com.intea.domain.entity.User;
import com.intea.domain.repository.ProductRepository;
import com.intea.domain.repository.ReviewRepository;
import com.intea.domain.repository.UserRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
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
        Product product = productRepo.findById(product_id);
        return product.getP_nm();
    }

    @Transactional
    public void makeReview(ReviewReqDTO reviewReqDTO) {
        User user = userRepo.findById(reviewReqDTO.getUser_id());
        Product product = productRepo.findById(reviewReqDTO.getProduct_id());

        reviewRepo.save(Review.builder()
                .user(user)
                .product(product)
                .title(reviewReqDTO.getTitle())
                .content(reviewReqDTO.getContent())
                .rate(reviewReqDTO.getRate())
                .build());

        productRepo.save(product);
    }

    public HashMap<String, Object> getReviewList(Long product_id) {
        List<ReviewResDTO> reviewList = new ArrayList<>();

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewList", reviewList);

        return resultMap;
    }

    public ReviewResDTO.ReviewDetailResDTO getReviewDetail(Long id) {
        Review review = reviewRepo.findById(id);

        return ReviewResDTO.ReviewDetailResDTO.builder()
                .content(review.getContent())
                .build();
    }
}
