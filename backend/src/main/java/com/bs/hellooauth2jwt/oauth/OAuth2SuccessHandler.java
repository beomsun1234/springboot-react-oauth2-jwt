package com.bs.hellooauth2jwt.oauth;

import com.bs.hellooauth2jwt.jwt.JwtUtil;
import com.bs.hellooauth2jwt.jwt.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        Token token = jwtUtil.generateToken(email, name);
        log.info("{}", token.getAccessToken());
        String ip = getRemoteIp(request);
        log.info("사용자port={}",request.getServerPort());
        log.info("ip={}",ip);
        response.addHeader("Authorization", "Bearer " +  token.getAccessToken());
        String targetUrl = "/auth/success?token="+token.getAccessToken();
        //response.sendRedirect(ip+":3000/auth?token="+token.getAccessToken());
        response.sendRedirect( "http://localhost:3000/auth?token="+token.getAccessToken());
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
