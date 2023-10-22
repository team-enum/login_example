package com.enums.example.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enums.example.member.domain.Member;
import com.enums.example.member.dto.MemberDTO;
import com.enums.example.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
   
   private final MemberService memberService;

   // 회원가입
   @GetMapping("/join")
   public String joinView(@ModelAttribute MemberDTO memberDTO){
      return "member/join";
   }

   @PostMapping("/join")
   public String joinMember(@Valid MemberDTO memberDTO, BindingResult bindingResult) throws RuntimeException{
      Member member = new Member();
      member.setUsername(memberDTO.getUsername());
      member.setPassword(memberDTO.getPassword());
      member.setRealname(memberDTO.getRealname());
      memberService.join(member);
      return "redirect:/member/login";
   }

   // 로그인
   @GetMapping("/login")
   public String loginView(){
      return "member/login";
   }

   // 마이페이지
   @GetMapping("/mypage")
   public String mypageView(){
      return "member/mypage";
   }
}
