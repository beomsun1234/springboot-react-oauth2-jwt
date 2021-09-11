package com.bs.hellooauth2jwt.controller;
import com.bs.hellooauth2jwt.domain.Member;
import com.bs.hellooauth2jwt.dto.UserInfoDto;
import com.bs.hellooauth2jwt.oauth.CustomUserDetail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getName();
    }

    @GetMapping("/api/v1/member")
    public String welcomMember(){
        log.info("인증된 사용자가 api/member요청");
        return "환영합니다";
    }

    //
    @GetMapping("/api/v2/member")
    public UserInfoDto getMemberInfo(@AuthenticationPrincipal OAuth2User oAuth2User){
        CustomUserDetail customUserDetail = (CustomUserDetail)oAuth2User;
        return UserInfoDto.builder().email(customUserDetail.getEmail()).name(customUserDetail.getName()).build();
    }

}
