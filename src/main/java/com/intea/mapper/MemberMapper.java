package com.intea.mapper;

import com.intea.domain.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /*
        회원가입
        로그인
        회원정보보기 / 수정
     */
    
    public int insMember(MemberEntity param);
    public MemberEntity selMember(MemberEntity param);
    public MemberEntity selMyInfo(MemberEntity param);
    public int updMyInfo(MemberEntity param);

    public MemberEntity findMemId(MemberEntity param);
    public MemberEntity findMemPw(MemberEntity param);
    public int updMemPw(MemberEntity param);
    public void delMember(MemberEntity param);
}
