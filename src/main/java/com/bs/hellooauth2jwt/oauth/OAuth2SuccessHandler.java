package com.bs.hellooauth2jwt.oauth;

import com.bs.hellooauth2jwt.domain.Member;
import com.bs.hellooauth2jwt.domain.MemberRepostitory;
import com.bs.hellooauth2jwt.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final MemberRepostitory memberRepostitory;
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String token = Jwts.builder().setSubject("cos").setExpiration(new Date(System.currentTimeMillis() + 60000L * 600)).claim("email", oAuth2User.getName()).compact();
        //memberRepostitory.save(oAuth2User.toEntity());
        log.info("{}", token);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Auth", token);
        response.setHeader("Refresh", token);
        response.setContentType("application/json;charset=UTF-8");
        response.sendRedirect("/home");
    }
}
