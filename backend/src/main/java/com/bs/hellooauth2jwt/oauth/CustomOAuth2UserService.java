package com.bs.hellooauth2jwt.oauth;


import com.bs.hellooauth2jwt.domain.member.Member;
import com.bs.hellooauth2jwt.domain.member.MemberRepostitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepostitory memberRepostitory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        /**
         * userNameAttributeName-> 어떤 소셜서비스든 그 서비스에서 각 계정마다의 유니크한 id값을 전달해주겠다는 의미입니다.
         *
         * 구글은 sub이라는 필드가 유니크 필드이며
         * 네이버는 id라는 필드가 유니크 필드입니다.
         */
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
        Member member = saveOrUpdate(oAuth2Attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2Attributes.getAttributes(),
                oAuth2Attributes.getNameAttributeKey()
        );
    }
    private Member saveOrUpdate(OAuth2Attributes attributes){
        Member user = memberRepostitory.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName()))
                .orElse(attributes.toEntity());
        return memberRepostitory.save(user);
    }
}
