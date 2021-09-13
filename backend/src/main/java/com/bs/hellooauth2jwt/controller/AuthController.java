package com.bs.hellooauth2jwt.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RequestMapping("auth")
@RestController
public class AuthController {
    @GetMapping("success")
    public String  sucess(String token, HttpServletRequest request, HttpServletResponse response){
        log.info("로그인 성공");
        log.info("header={}", request.getHeader("Authorization"));
        return token;
    }

    @PostMapping("token")
    public String  token(Map<String, Object> attributes){
        log.info("attributes={}", attributes);
        return "token";
    }
}
