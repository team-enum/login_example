package com.enums.example.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@SequenceGenerator(name = "member_seq", initialValue = 1, allocationSize = 1)
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
   @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
   private Long id;
   @Column(unique = true)
   private String username;
   private String password;
   private String realname;
   private LocalDateTime createDate;
   
}
