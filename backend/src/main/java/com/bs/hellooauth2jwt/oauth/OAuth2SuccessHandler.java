package com.bs.hellooauth2jwt.oauth;

import com.bs.hellooauth2jwt.jwt.JwtUtil;
import com.bs.hellooauth2jwt.jwt.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{
    private final JwtUtil jwtUtil;
    private final String redirectUrl = "http://localhost:3000/auth";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2Attributes userInfo = getUserInfo(oAuth2User);
        log.info("email={}",userInfo.getEmail());
        log.info("name={}",userInfo.getName());
        log.info("-----토큰발급--------------------");
        Token token = jwtUtil.generateToken(userInfo.getEmail(), userInfo.getName());
        log.info("token={}", token.getAccessToken());
        String ip = getRemoteIp(request);
        log.info("ip={}",ip);
        response.sendRedirect(redirectUrl+"?token="+token.getAccessToken());
    }

    private OAuth2Attributes getUserInfo(OAuth2User oAuth2User){
        Map<String,Object> data = oAuth2User.getAttributes();
        if(data.get("response") != null){
            return OAuth2Attributes.of("naver","response",data);
        }
        if(data.get("kakao_account")!=null){
            return OAuth2Attributes.of("kakao","id",data);
        }
        return OAuth2Attributes.of("google","google",data);
    }

    /**
     * ip수집
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return "http://"+ip;
    }

}
