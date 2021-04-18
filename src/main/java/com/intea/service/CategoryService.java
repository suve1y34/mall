package com.intea.service;

import com.intea.domain.repository.CategoryRepository;
import com.nimbusds.openid.connect.sdk.federation.policy.operations.ValueOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.DynamicAny.DynValueOperations;
import org.springframework.stereotype.Service;


import java.util.HashMap;

@Slf4j @RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
//    private final ValueOperations valueOperation;

/*    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getCategoryList() {
        return (HashMap<String, Object>) valueOperation.get(CATEGORY_LIST_KEY);
    }*/
}
