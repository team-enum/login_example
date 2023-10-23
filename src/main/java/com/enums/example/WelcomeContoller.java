package com.enums.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeContoller {
   
   @GetMapping
   public String page(){
      return "welcome";
   }

   @GetMapping("/member/mypage")
   public String mypage(){
      return "mypage";
   }
}
