package com.bs.hellooauth2jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String email;
    private String name;

    @Builder
    public UserInfoDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
