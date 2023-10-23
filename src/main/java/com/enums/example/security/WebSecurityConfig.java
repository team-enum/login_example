package com.enums.example.security;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.
         authorizeHttpRequests(req -> req
            .requestMatchers("member/mypage").authenticated()
            .anyRequest().permitAll())
         .oauth2Login(oauth -> oauth
            .defaultSuccessUrl("/"))
         .csrf(c -> c.disable());
         
      return http.build();
   }

   private ClientRegistration clientRegistration(String clientId, String redirectUri){
      return CustomOauth2Provider.KAKAO.getBuilder("Kakao")
         .clientId(clientId)
         .redirectUri(redirectUri)
         .build();
   }

   @Bean
   public ClientRegistrationRepository clientRegistrationRepository(
      @Value("${oauth2.client.kakao.client-id}")String clientId, 
      @Value("${oauth2.client.kakao.redirect-uri}")String redirectUri){
      List<ClientRegistration> registrations = new ArrayList<>();
      registrations.add(clientRegistration(clientId, redirectUri));
      return new InMemoryClientRegistrationRepository(registrations);
   }

   
}
