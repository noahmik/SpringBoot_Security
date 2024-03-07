package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/**").authenticated() // /user/** 패턴은 인증 필요
                                .requestMatchers("/manager/**").hasRole("ADMIN") // /manager/** 패턴은 ADMIN 권한 필요
                                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** 패턴은 ADMIN 권한 필요
                                .anyRequest().permitAll() // 그 외의 요청은 모두 허용
                )
                .formLogin(withDefaults());

        return http.build();
    }
}
