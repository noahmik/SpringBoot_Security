package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 필터체인 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // security 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/**").authenticated() // /user/** 패턴은 인증 필요
                                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") // /manager/** 패턴은 ADMIN 또는 MANAGER 권한 필요
                                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** 패턴은 ADMIN 권한 필요
                                .anyRequest().permitAll() // 그 외의 요청은 모두 허용
                )
                .formLogin(form ->
                        form
                                .loginPage("/loginForm")
                                .loginProcessingUrl("/login") // "/login" 주소 호출되면 시큐리티가 낚아채서 로그인 진행해줌
                                .defaultSuccessUrl("/") // 로그인 되면 메인 페이지로 이동
                                .permitAll()
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .loginPage("/loginForm")
                                .defaultSuccessUrl("/", true) // OAuth2 로그인 성공 후 리디렉션 URL 설정
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(principalOauth2UserService) // OAuth2 사용자 서비스 설정
                                )
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/loginForm") // 접근 거부 시 /login으로 리다이렉션
                );

        return http.build();
    }
}
