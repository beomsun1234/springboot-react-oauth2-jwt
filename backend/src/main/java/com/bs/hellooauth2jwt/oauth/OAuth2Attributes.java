package com.bs.hellooauth2jwt.oauth;

import com.bs.hellooauth2jwt.domain.member.AuthenticationProvider;
import com.bs.hellooauth2jwt.domain.member.Member;
import com.bs.hellooauth2jwt.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private AuthenticationProvider authenticationProvider;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String picture,AuthenticationProvider authenticationProvider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authenticationProvider = authenticationProvider;
    }
    public static OAuth2Attributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if("kakao".equals(registrationId)){
            log.info("----카카오 로그인-------------------");
            log.info("userNameAttributeName={}",userNameAttributeName);
            return ofKakao(userNameAttributeName,attributes);
        }
        if("naver".equals(registrationId)){
            log.info("-----네이버 로그인-----------------");
            log.info("userNameAttributeName={}",userNameAttributeName);
            return ofNaver(userNameAttributeName, attributes);
        }
        log.info("----구글 로그인-------------------");
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authenticationProvider(AuthenticationProvider.GOOGLE)
                .build();
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuth2Attributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authenticationProvider(AuthenticationProvider.KAKAO)
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // JSON형태이기 떄문에 Map을 통해서 데이터를 가져온다.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2Attributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authenticationProvider(AuthenticationProvider.NAVER)
                .build();
    }


    /**
     * toEntity()
     * User 엔티티 생성
     * OAuthAttributes에서 엔티티 생성 시점 = 처음 가입 시
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스 생성
     * @return
     */
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.ROLE_USER)
                .picture(picture)
                .authenticationProvider(authenticationProvider)
                .build();
    }
}
