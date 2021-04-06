package com.intea.mapper;

import com.intea.domain.AttachEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachMapper {
    public int insAttach(List<AttachEntity> attachList);
    public AttachEntity selAttachDetail(Long i_file);
    public int delAttach(Long i_product);
    public List<AttachEntity> selAttachList(Long i_product);
    public int selAttachTotalCnt(Long i_product);
    public int undeleteAttach(List<Long> i_files);
}
