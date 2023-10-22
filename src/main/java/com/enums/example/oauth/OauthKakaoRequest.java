package com.enums.example.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.enums.example.oauth.dto.KakaoToken;
import com.enums.example.oauth.dto.KakaoUserInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OauthKakaoRequest {
   
   private final RestTemplate restTemplate;

   @Value("${oauth.kakao.client_id}")
   private String clientId;
   @Value("${oauth.kakao.redirect_uri}")
   private String redirectUri;

   private final String tokenURL = "https://kauth.kakao.com/oauth/token";
   private final String logoutURL = "https://kapi.kakao.com/v1/user/logout";
   private final String userInfoURL = "https://kapi.kakao.com/v2/user/me";

   // 토큰 받기
   public KakaoToken receiveToken(String code){
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type", "authorization_code");
      params.add("client_id", clientId);
      params.add("redirect_uri", redirectUri);
      params.add("code", code);

      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
      ResponseEntity<KakaoToken> response = restTemplate.exchange(tokenURL, HttpMethod.POST, httpEntity, KakaoToken.class);
      return response.getBody();
   }

   // 사용자 정보
   public KakaoUserInfo receiveUserInformation(String accessToken){
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      headers.add("Authorization", "bearer " + accessToken);

      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
      ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(userInfoURL, HttpMethod.POST, httpEntity, KakaoUserInfo.class);
      return response.getBody();
   }

   // 로그아웃
   public String logout(String accessToken){
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      headers.add("Authorization", "bearer " + accessToken);

      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
      ResponseEntity<String> response = restTemplate.exchange(logoutURL, HttpMethod.POST, httpEntity, String.class);
      return response.getBody();
   }
}
