package com.enums.example.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enums.example.oauth.dto.KakaoToken;
import com.enums.example.oauth.dto.KakaoUserInfo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OauthKakaoController {
   
   private final OauthKakaoRequest kakaoOauth;
   
   @GetMapping("/kakao/oauth")
   @ResponseBody
   public Object receiveAuthorizeCode(@RequestParam String code){
      KakaoToken token = kakaoOauth.receiveToken(code);
      KakaoUserInfo kakaoUserInfo = kakaoOauth.receiveUserInformation(token.getAccess_token());
      return kakaoUserInfo;
   }

   // @GetMapping("/kakao/oauth/logout")
   // @ResponseBody
   // public String logout(){
   //    
   // }
}
