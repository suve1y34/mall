package com.intea.service;

import com.intea.constant.Const;
import com.intea.domain.*;
import com.intea.mapper.AdminMapper;
import com.intea.mapper.AttachMapper;
import com.intea.mapper.ProductMapper;
import com.intea.paging.PaginationInfo;
import com.intea.util.FileUtils;
import com.intea.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class AdminService {
    private AdminMapper aMapper;
    private ProductMapper productMapper;
    private AttachMapper atMapper;
    private SecurityUtils sUtils;
    private FileUtils fUtils;


    //상품 등록/수정
/*    public boolean regProduct(ProductDTO param) {
        int queryResult = 0;

        if(param.getI_product() == null) {
            queryResult = aMapper.insProduct(param);
        } else {
            queryResult = aMapper.updProduct(param);
            // 파일이 추가, 삭제, 변경된 경우
            if ("Y".equals(param.getChange_yn())) {
                atMapper.delAttach(param.getI_product());

                // fileIdxs에 포함된 idx를 가지는 파일의 삭제여부를 'N'으로 업데이트
                if (CollectionUtils.isEmpty(param.getI_file()) == false) {
                    atMapper.undeleteAttach(param.getI_file());
                }
            }
        }
        return (queryResult == Const.SUCCESS) ? true : false;
    }

    public boolean registerProduct(ProductDTO param, MultipartFile[] files) {
        int queryResult = 1;

        if (regProduct(param) == false) {
            return false;
        }

        List<AttachDTO> fileList = fUtils.uploadFiles(files, param.getI_product());
        if (CollectionUtils.isEmpty(fileList) == false) {
            queryResult = atMapper.insAttach(fileList);
            if (queryResult < 1) {
                queryResult = 0;
            }
        }

        return (queryResult > 0);
    }*/

    public List<AttachEntity> getAttachFileList(Long i_product) {

        int fileTotalCount = atMapper.selAttachTotalCnt(i_product);
        if (fileTotalCount < 1) {
            return Collections.emptyList();
        }
        return atMapper.selAttachList(i_product);
    }

    public AttachEntity getAttachDetail(Long i_file) {
        return atMapper.selAttachDetail(i_file);
    }

    //상품 삭제
    public boolean delProduct(Long i_product) {
        int result = 0;

        ProductEntity pd = productMapper.selProductDetail(i_product);

        if(pd != null && "N".equals(pd.getDelete_yn())) {
            result = aMapper.delProduct(i_product);
        }
        return (result == 1) ? true : false;
    }

    //주문 전체
    public List<OrdersEntity> selOrdersList(OrdersEntity param) {
        return aMapper.selOrdersList(param);
    }

    //주문 상세보기
    public OrdersEntity orderView(OrdersEntity param) {
        return aMapper.orderView(param);
    }

    //멤버 전체
    public List<MemberEntity> selMemberList(MemberEntity param) {
        List<MemberEntity> memList = Collections.emptyList();

        int totalCnt = aMapper.selMemberTotalCnt(param);

        PaginationInfo pInfo = new PaginationInfo(param);
        pInfo.setTotalRecordCnt(totalCnt);

        param.setPaginationInfo(pInfo);

        if(totalCnt > Const.ZERO) {
            memList = aMapper.selMemberList(param);
        }
        return memList;
    }
    
    //주문상태 변경
    public void updDelivery(OrdersEntity param) {
        aMapper.updDelivery(param);
    }

    //주문상태 변경 서브
    public void changeStock_sub(OrdersDetailEntity param) {
        aMapper.changeStock_sub(param);
    }

    //상품 수량 변경
    public void changeStock(ProductEntity param) {
        aMapper.changeStock(param);
    }
}
