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
   private final UserRepository userRepository;
   
   @GetMapping("/kakao/oauth")
   @ResponseBody
   public Object receiveAuthorizeCode(@RequestParam String code){
      KakaoToken token = kakaoOauth.receiveToken(code);
      KakaoUserInfo userInfo = kakaoOauth.receiveUserInformation(token.getAccess_token());
      
      // Kakao 사용자 ID를 기반으로 DB에서 사용자를 찾거나 새로운 사용자 생성
      kakaoUser user = userRepository.findByKakaoUserId(userInfo.getId().toString());
      if (user == null) {
          user = new kakaoUser();
          user.setKakaoUserId(userInfo.getId().toString());
      }

   // KakaoUserInfo정보 -> 카카오유저 엔티티 업데이트
      KakaoUserInfo.Properties properties = userInfo.getProperties();
      user.setUsername(properties.getNickname());
      user.setNickname(properties.getNickname());
      user.setProfileImage(properties.getProfile_image());
      user.setThumbnailImage(properties.getThumbnail_image());


      // User 엔티티 저장
      userRepository.save(user);
      
      return userInfo;
   }
   
   
   

   
   // @GetMapping("/kakao/oauth/logout")
   // @ResponseBody
   // public String logout(){
   //    
   // }
}
