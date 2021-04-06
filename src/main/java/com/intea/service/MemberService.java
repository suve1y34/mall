package com.intea.service;

import com.intea.config.SecurityUser;
import com.intea.domain.entity.Members;
import com.intea.domain.repository.MembersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private MembersRepository membersRepo;

    @Override
    public UserDetails loadUserByUsername(String mem_id) throws UsernameNotFoundException {
        Optional<Members> optional = membersRepo.findById(mem_id);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(mem_id + " 사용자 없음");
        } else {
            Members members = optional.get();
            return new SecurityUser(members);
        }
    }
/*    private MemberMapper mMapper;
    private PasswordEncoder pwEncoder;
    private SecurityUtils sUtils;

    public int insMember(MemberEntity param) {

        if(param.getMem_id() == null || param.getMem_id().length() < Const.MINIMUM) {
            return Const.FAIL;
        }

        if(StringUtils.isNotBlank(param.getPw())) {
            String encodedPw = pwEncoder.encode(param.getPw());
            param.setPw(encodedPw);
        }

        return mMapper.insMember(param);
    }

    public int selMember(MemberEntity param, HttpServletRequest req) {
        MemberEntity dbMem = mMapper.selMember(param);

        if(dbMem == null) {
            return Const.NO_ID;
        }
        String cryptPw = dbMem.getPw();
        String encodedPw = sUtils.getHashPw(param.getPw(), cryptPw);
        if(!encodedPw.equals(dbMem.getPw())) {
            return Const.NO_PW;
        }

        param.setPw(null);

        req.getSession().setAttribute(Const.LOGIN_USER, dbMem);
        return Const.SUCCESS;
    }

    public int getMyInfo(MemberEntity param) {
        MemberEntity loginMem = mMapper.selMyInfo(param);

        if(loginMem == null) {
            return Const.FAIL;
        }

        return Const.SUCCESS;
    }

    public int updMyInfo(MemberEntity param) {
        int result = mMapper.updMyInfo(param);

        if(param.getI_mem() == null) {
            result = Const.FAIL;
            return result;
        } else {
            result = Const.SUCCESS;
        }

        return result;
    }

    public void signout(HttpSession hs) {
        hs.invalidate();
    }

    public int chkId(MemberEntity param) {
        MemberEntity dbData = mMapper.selMember(param);
        if(dbData == null) {
            return Const.FAIL;
        }
        return Const.SUCCESS;
    }

    public String findId(MemberEntity param) {
        MemberEntity dbMem = mMapper.findMemId(param);
        if(dbMem == null) {
            return null;
        }
        param.setMem_id(dbMem.getMem_id());
        return param.getMem_id();
    }

    public int chkPw(MemberEntity param) {
        MemberEntity dbMem = mMapper.findMemPw(param);
        if(dbMem == null) {
            return Const.NO_ID;
        }
        return Const.SUCCESS;
    }

    public int changePw(MemberEntity param) {
        if(param.getPw() == null || param.getPw().length() < Const.MINIMUM) {
            return Const.FAIL;
        }
        String encodedPw = pwEncoder.encode(param.getPw());
        param.setPw(encodedPw);

        mMapper.updMemPw(param);
        return Const.SUCCESS;
    }

    public void delMember(MemberEntity param) {
        mMapper.delMember(param);
    }*/
}
