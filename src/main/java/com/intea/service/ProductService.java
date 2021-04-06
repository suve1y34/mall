package com.intea.service;

import com.intea.domain.CategoryEntity;
import com.intea.domain.ShopDTO;
import com.intea.mapper.ProductMapper;
import com.intea.paging.PaginationInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private ProductMapper productMapper;

    public List<CategoryEntity> category() {
        List<CategoryEntity> ctList = Collections.emptyList();

        ctList = productMapper.category();
        return ctList;
    }

/*    public List<ShopDTO> getProductList(ShopDTO param) {
        List<ShopDTO> pdList = Collections.emptyList();

        int totalCnt = productMapper.selProductTotalCnt(param);

        PaginationInfo pInfo = new PaginationInfo(param);
        pInfo.setTotalRecordCnt(totalCnt);

        param.setPaginationInfo(pInfo);

        if(totalCnt > Const.ZERO) {
            pdList = productMapper.selAllProduct(param);
        }

        return pdList;
    }*/

    public List<ShopDTO> getProductListByCt(String c_code, ShopDTO param) {
        List<ShopDTO> pdList = Collections.emptyList();

        if(c_code.equals("ALL")) {
            pdList = productMapper.selAllProduct(param);
        } else {
            pdList = productMapper.selProductListSmallCt(c_code);
        }
        int totalCnt = productMapper.selProductTotalCnt(param);
        PaginationInfo pInfo = new PaginationInfo(param);
        param.setPaginationInfo(pInfo);

        return pdList;
    }

/*    public List<ShopDTO> getProductListByCt(CategoryEntity ct, ShopDTO param) {
        List<ShopDTO> pdList = Collections.emptyList();

        if(ct.getCt_level() == Const.ZERO) {
            pdList = productMapper.selAllProduct(param);
        } else {
            pdList = productMapper.selProductListSmallCt(ct.getI_category());
        }
        return pdList;
    }*/

    //새상품 조회
    public List<ShopDTO> selNewProduct(ShopDTO param) {
        List<ShopDTO> pdList = productMapper.selNewProduct(param);
        return pdList;
    }

    public ShopDTO selProductDetail(Long i_product) {
        return productMapper.selProductDetail(i_product);
    }
}
