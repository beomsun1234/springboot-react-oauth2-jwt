package com.bs.hellooauth2jwt.oauth;

import com.bs.hellooauth2jwt.domain.AuthenticationProvider;
import com.bs.hellooauth2jwt.domain.Member;
import com.bs.hellooauth2jwt.domain.Role;
import lombok.Builder;
import lombok.Getter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.Map;

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
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authenticationProvider(AuthenticationProvider.GOOGLE)
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
