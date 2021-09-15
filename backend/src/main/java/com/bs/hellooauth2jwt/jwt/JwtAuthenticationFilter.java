package com.bs.hellooauth2jwt.jwt;
import com.bs.hellooauth2jwt.domain.member.Member;
import com.bs.hellooauth2jwt.domain.member.MemberRepostitory;
import com.bs.hellooauth2jwt.oauth.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepostitory memberRepostitory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("----------------------------------------OncePerRequestFilter");

        String token = request.getHeader("Auth");
        if(token==null){
            filterChain.doFilter(request, response);
            return;
        }
        log.info("token={}", token);
        UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Auth");
        log.info("-------------토큰 확인중--------------------------");
        if (token != null && jwtUtil.verifyToken(token)) {
            String email = jwtUtil.getEmail(token);
            Member member = memberRepostitory.findByEmail(email).orElseThrow(IllegalAccessError::new);
            log.info("email={}", member.getEmail());
            CustomUserDetail customUserDetail = new CustomUserDetail(member);
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    customUserDetail, null, customUserDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            log.info("-------------인가------------------------------------");
            return newAuth;
        }
        return null;
    }

}
