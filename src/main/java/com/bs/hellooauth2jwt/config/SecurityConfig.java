package com.bs.hellooauth2jwt.config;

import com.bs.hellooauth2jwt.jwt.JwtAuthenticationFilter;
import com.bs.hellooauth2jwt.oauth.CustomOAuth2UserService;
import com.bs.hellooauth2jwt.oauth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                        .successHandler(successHandler)
                        .userInfoEndpoint().userService(oAuth2UserService);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
