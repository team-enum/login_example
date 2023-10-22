package com.enums.example.member.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enums.example.member.domain.Member;
import com.enums.example.member.repository.MemberRepository;
import com.enums.example.security.SecurityMember;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
   
   private final MemberRepository memberRepository;
   private final PasswordEncoder passwordEncoder;

   // 중복 확인
   public boolean checkForDuplicateUsername(String username){
      return memberRepository.countByUsername(username) > 0;
   }

   // 회원가입
   @Transactional
   public Member join(Member member) throws RuntimeException {
      if(checkForDuplicateUsername(member.getUsername())){
         throw new RuntimeException("아이디가 중복되었습니다.");
      }
      
      String encodedPassowrd = passwordEncoder.encode(member.getPassword());
      member.setPassword(encodedPassowrd);
      member.setCreateDate(LocalDateTime.now());

      return memberRepository.save(member);
   }

   // 로그인
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Member member = memberRepository.findByUsername(username);
      if(member == null){
         throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
      }
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("USER"));
   
      return new SecurityMember(member, authorities);
   }

   
}
