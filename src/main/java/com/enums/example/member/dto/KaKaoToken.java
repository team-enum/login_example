package com.enums.example.member.dto;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class KaKaoToken {
   private String token_type;
   private String access_token;
   private String expires_in;
   private String refresh_token;
   private String refresh_token_expires_in;
   private String scope;
}
