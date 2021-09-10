
package com.bs.hellooauth2jwt.controller;

import com.bs.hellooauth2jwt.domain.Member;
import com.bs.hellooauth2jwt.oauth.CustomUserDetail;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class Controller {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getName();
    }

    @GetMapping("/api/v1/member")
    public String member(){
        log.info("인증된 사용자가 api/member요청");
        return "환영합니다";
    }

    //

    @GetMapping("/api/v2/member")
    public Member memberV2(@AuthenticationPrincipal OAuth2User oAuth2User){
        CustomUserDetail customUserDetail = (CustomUserDetail)oAuth2User;
        return customUserDetail.getMember();
    }
}
