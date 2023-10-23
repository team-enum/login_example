package com.enums.example.oauth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enums.example.member.Member;
import com.enums.example.member.MemberService;
import com.enums.example.oauth.dto.KakaoToken;
import com.enums.example.oauth.dto.KakaoUserInfo;
import com.enums.example.security.MemberDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OauthKakaoController {
   
   private final OauthKakaoRequest kakaoOauth;
   
   @GetMapping("/kakao/oauth")
   @ResponseBody
   public Object receiveAuthorizeCode(@RequestParam String code){
      KakaoToken token = kakaoOauth.receiveToken(code);
      KakaoUserInfo userInfo = kakaoOauth.receiveUserInformation(token.getAccess_token());
      Member member = new Member();
      member.setUsername(userInfo.getNickname());
      member.setPassword(token.getAccess_token());
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("USER"));
      
      return userInfo;
   }

   // @GetMapping("/kakao/oauth/logout")
   // @ResponseBody
   // public String logout(){
   //    
   // }
}
