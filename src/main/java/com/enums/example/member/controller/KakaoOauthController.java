package com.enums.example.member.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.enums.example.member.dto.KaKaoToken;

@Controller
public class KakaoOauthController {
   
   private final String receiveTokenURL = "https://kauth.kakao.com/oauth/token";
   private final String logoutURL = "https://kapi.kakao.com/v1/user/logout";
   private final String userURL = "https://kapi.kakao.com/v2/user/me";
   private final String clientId = "~~{restAPI Key}";
   private final String redirectUri = "http://localhost:9090/kakao/oauth";

   @GetMapping("/kakao/oauth")
   @ResponseBody
   public String receiveAuthorizeCode(@RequestParam String code){
      RestTemplate restTemplate = new RestTemplate();
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      
      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type", "authorization_code");
      params.add("client_id", clientId);
      params.add("redirect_uri", redirectUri);
      params.add("code", code);

      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
      ResponseEntity<KaKaoToken> kaKaoToken = restTemplate.exchange(receiveTokenURL, HttpMethod.POST, httpEntity, KaKaoToken.class);
      
      return kaKaoToken.toString();
   }

   @GetMapping("/kakao/oauth/logout")
   @ResponseBody
   public String logout(){
      RestTemplate restTemplate = new RestTemplate();
      MultiValueMap<String, String> headers = new HttpHeaders();
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      headers.add("Authorization", "Bearer ");
      
      HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
      ResponseEntity<String> logout = restTemplate.exchange(receiveTokenURL, HttpMethod.POST, httpEntity, String.class);
      return logout.toString();
   }
}
