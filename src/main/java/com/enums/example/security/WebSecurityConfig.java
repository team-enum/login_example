package com.enums.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
   
   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   @Bean
   public RestTemplate restTemplate(){
      return new RestTemplate();
   }

   @Bean
   public SecurityFilterChain configure(HttpSecurity http) throws Exception{
      http
         .authorizeHttpRequests((req) -> req
            .requestMatchers("/member/mypage**").hasAuthority("USER")
            .requestMatchers("/member/join**", "/member/login").anonymous()
            .anyRequest().permitAll())
         .formLogin((formLogin -> formLogin
            .loginPage("/member/login")
            .defaultSuccessUrl("/")))
         .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true));
      return http.build();
   }
}
