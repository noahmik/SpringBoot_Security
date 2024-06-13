package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 필터체인 등록
public class SecurityConfig {
    @Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/**").authenticated() // /user/** 패턴은 인증 필요
                                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") // /manager/** 패턴은 ADMIN 또는 MANAGER 권한 필요
                                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** 패턴은 ADMIN 권한 필요
                                .anyRequest().permitAll() // 그 외의 요청은 모두 허용
                )
                .formLogin(form ->
                        form
                                .loginPage("/loginForm")
                                .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/loginForm") // 접근 거부 시 /login으로 리다이렉션
                );

        return http.build();
    }
}
