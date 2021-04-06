package com.intea.mapper;

import com.intea.domain.CategoryEntity;
import com.intea.domain.ProductEntity;
import com.intea.domain.ShopDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    /*
        상품 보기(1차,2차 카테고리)
        상품 상세보기
        상품 갯수(1차,2차 카테고리)
     */
    public List<CategoryEntity> category();

    public List<ShopDTO> selAllProduct(ShopDTO param);
    public List<ShopDTO> selNewProduct(ShopDTO param);
    public List<ShopDTO> selProductListBigCt(Long ref_category);
    public List<ShopDTO> selProductListSmallCt(String c_code);

    public ShopDTO selProductDetail(Long i_product);

    public int selProductTotalCnt(ProductEntity param);
    public int productCntBigCt(Long i_category);
    public int productCntSmallCt(Long i_category);
}
