package com.intea.service;

import com.intea.common.Const;
import com.intea.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import java.util.HashMap;

@Slf4j @RequiredArgsConstructor
@Service
public class CategoryService {
    private final ValueOperations valueOperations;

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getCategoryList() {
        return (HashMap<String, Object>) valueOperations.get(Const.CATEGORY_LIST_KEY);
    }
}
