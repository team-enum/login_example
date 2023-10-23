package com.enums.example.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enums.example.security.MemberDetails;

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
   public String mypageView(
         Authentication authentication,
         @AuthenticationPrincipal MemberDetails member, 
         Model model){
      model.addAttribute("member", member);
      return "member/mypage";
   }
}
