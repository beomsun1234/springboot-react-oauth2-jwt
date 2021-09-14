package com.bs.hellooauth2jwt.domain;

import lombok.Getter;

@Getter
public enum AuthenticationProvider {
    LOCAL, GOOGLE, KAKAO, NAVER;
}
