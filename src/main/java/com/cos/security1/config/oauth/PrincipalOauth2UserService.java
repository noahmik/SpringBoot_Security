package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : " + userRequest);

        // 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 추가적인 사용자 정보 처리 로직 구현
        System.out.println("oAuth2User : " + oAuth2User.getAttributes());

        // 필요시 DB에 사용자 정보 저장
        // 사용자 정보 가공 및 반환
        return oAuth2User;
    }
}