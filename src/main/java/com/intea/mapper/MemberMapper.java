package com.intea.mapper;

import com.intea.domain.dto.MembersDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /*
        회원가입
        로그인
        회원정보보기 / 수정
     */
    
    public int insMember(MembersDTO param);
    public MembersDTO selMember(MembersDTO param);
    public MembersDTO selMyInfo(MembersDTO param);
    public int updMyInfo(MembersDTO param);

    public MembersDTO findMemId(MembersDTO param);
    public MembersDTO findMemPw(MembersDTO param);
    public int updMemPw(MembersDTO param);
    public void delMember(MembersDTO param);
}
