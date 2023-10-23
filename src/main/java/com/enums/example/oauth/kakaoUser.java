package com.enums.example.oauth;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@SequenceGenerator(name="kakaouser_seq", allocationSize = 1, initialValue = 1)
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class kakaoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kakaouser_seq")
    private Long id;
    private String kakaoUserId; 
    private String username; 
    private String email; 
    private String nickname; 
    private String profileImage; 
    private String thumbnailImage; 

    
}
