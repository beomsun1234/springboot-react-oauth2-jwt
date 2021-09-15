package com.bs.hellooauth2jwt.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("android/token")
    public String  token(@RequestBody Map<String, Object> attributes, HttpServletRequest request){
        log.info(request.getHeader("type"));
        log.info("attributes={}",
                attributes.get("profile"));
        Map<String, Object> data = (Map<String, Object>) attributes.get("profile");
        log.info("attributes={}", data.get("email"));
        return "token";
    }
}
