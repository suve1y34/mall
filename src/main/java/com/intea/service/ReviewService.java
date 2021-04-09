package com.intea.service;

import com.intea.domain.repository.ReviewRepository;
import com.intea.domain.repository.UserRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepo;
    private final ReviewRepository reviewRepo;
}
