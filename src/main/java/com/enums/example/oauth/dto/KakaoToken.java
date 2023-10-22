package com.enums.example.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @ToString
public class KakaoToken {
   private String token_type;
   private String access_token;
   private String expires_in;
   private String refresh_token;
   private String refresh_token_expires_in;
   private String scope;
}