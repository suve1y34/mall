package com.intea.service;

import com.intea.config.SecurityUser;
import com.intea.config.auth.dto.OAuthAttributes;
import com.intea.config.auth.dto.SessionUser;
import com.intea.domain.dto.MemberRequestDto;
import com.intea.domain.entity.User;
import com.intea.domain.repository.UserRepository;
import com.intea.exception.DeletedUserException;
import com.intea.exception.NotExistUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // 사용자가 입력한 id를 통해 디비에 저장된 유저 객체를 가져와서 UserDetails 객체로 변환하여 돌려주는 메소드
    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        User user = userRepository.findByMemId(memId).orElseThrow(()
                -> new NotExistUserException("로그인에 실패하였습니다."));

        if (user.getDeleteYn().equals('Y')) {
            throw new DeletedUserException("존재하지 않는 회원입니다.");
        }

        if(nonNull(RequestContextHolder.getRequestAttributes())) {
            // 컨텍스트 홀더
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user", user.toResponseDto(user));
        }

        return new SecurityUser(user);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
