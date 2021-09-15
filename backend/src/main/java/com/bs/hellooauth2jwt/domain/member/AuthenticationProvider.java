package com.bs.hellooauth2jwt.domain.member;

import lombok.Getter;

@Getter
public enum AuthenticationProvider {
    LOCAL, GOOGLE, KAKAO, NAVER;
}
