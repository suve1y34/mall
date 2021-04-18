package com.intea.service;

import com.intea.domain.dto.PagingDTO;
import com.intea.domain.dto.ReviewReqDTO;
import com.intea.domain.dto.ReviewResDTO;
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
        Product product = productRepo.findById(product_id).orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));
        return product.getP_nm();
    }

/*    public String uploadReviewImage(MultipartFile file, String dirName) throws IOException {
        // S3와 연결할 client 얻기
        AmazonS3 s3Client = awss3Utils.getS3Client();

        // S3에 저장할 파일 경로 얻기
        String saveFilePath = UploadFileUtils.getSaveFilePath(file, dirName);

        return awss3Utils.putObjectToS3AndGetUrl(s3Client, saveFilePath, file);
    }*/

    @Transactional
    public void makeReview(ReviewReqDTO reviewReqDTO) {
        User user = userRepo.findById(reviewReqDTO.getUser_id())
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 회원입니다."));
        Product product = productRepo.findById(reviewReqDTO.getProduct_id())
                .orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));

        reviewRepo.save(Review.builder()
                .user(user)
                .product(product)
                .title(reviewReqDTO.getTitle())
                .content(reviewReqDTO.getContent())
                .rate(reviewReqDTO.getRate())
                .build());

        productRepo.save(product);
    }

    public HashMap<String, Object> getReviewList(Long product_id, int page) {
        int realPage = page - 1;
        Pageable pageable = PageRequest.of(realPage, 3);

        Page<Review> reviewPage = reviewRepo.findAllByProductIdOrderByInsert_timeDesc(product_id, pageable);

        List<ReviewResDTO> reviewResponseDtoList = new ArrayList<>();

        for (Review review : reviewPage) {
            reviewResponseDtoList.add(review.toResponseDTO());
        }

        PageImpl<ReviewResDTO> reviewResponseDtos
                = new PageImpl<>(reviewResponseDtoList, pageable, reviewPage.getTotalElements());

        PagingDTO reviewPagingDTO = new PagingDTO();
        reviewPagingDTO.setPagingInfo(reviewResponseDtos);

        // 평점 평균 조회해서 맵에 추가
        Product product = productRepo.findById(product_id)
                .orElseThrow(() -> new NotExistProductException("존재하지 않는 상품입니다."));

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewList", reviewResponseDtos);
        resultMap.put("reviewPagingDTO", reviewPagingDTO);

        return resultMap;
    }

    public ReviewResDTO.ReviewDetailResDTO getReviewDetail(Long id) {
        Review review = reviewRepo.findById(id).orElseThrow(()
                -> new NotExistReviewException("존재하지 않는 게시글입니다."));;

        return ReviewResDTO.ReviewDetailResDTO.builder()
                .content(review.getContent())
                .build();
    }
}
