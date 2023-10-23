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
// @EnableWebSecurity : Spring Security를 활성화하고 웹 보안을 구성하기 위한 설정 클래스임을 나타냅니다.
public class WebSecurityConfig {
   
	// 비밀번호 인코딩을 위한 BCryptPasswordEncoder를 빈으로 생성하여 사용합니다.
	// 이것은 비밀번호를 안전하게 저장하고 인증 시 비밀번호를 비교하는 데 사용됩니다.
   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   // RESTful 서비스 호출을 위한 RestTemplate을 빈으로 생성합니다.
   @Bean
   public RestTemplate restTemplate(){
      return new RestTemplate();
   }

   // HttpSecurity 객체를 구성하고 보안 설정을 정의하는 메서드
   @Bean
   public SecurityFilterChain configure(HttpSecurity http) throws Exception{
      http
      // .authorizeHttpRequests((req) -> req ... :
      // HTTP 요청에 대한 권한 및 접근 권한을 설정합니다.
      // 예를 들어, /member/mypage 엔드포인트는 "USER" 권한이 필요하며,
      // /member/join 및 /member/login 엔드포인트는 익명 사용자에게 허용됩니다. 다른 모든 요청은 모든 사용자에게 허용됩니다.
         .authorizeHttpRequests((req) -> req
            .requestMatchers("/member/mypage**").hasAuthority("USER")
            .requestMatchers("/member/join**", "/member/login").anonymous()
            .anyRequest().permitAll())
      // .formLogin((formLogin -> formLogin ... :
      // 사용자가 로그인하는 방법과 로그인 성공 후의 동작을 설정합니다.
      // .loginPage("/member/login")은 로그인 페이지를 /member/login으로 설정하고,
      // .defaultSuccessUrl("/")은 로그인 성공 후에 홈 페이지로 이동합니다.
         .formLogin((formLogin -> formLogin
            .loginPage("/member/login")
            .defaultSuccessUrl("/")))
      // .logout((logout) -> logout ...: 로그아웃 설정을 정의합니다.
      // /member/logout 엔드포인트를 사용하여 로그아웃을 처리하고, 로그아웃 성공 시 홈 페이지로 이동하며, 세션을 무효화합니다.
         .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true));
      return http.build();
   }
}
