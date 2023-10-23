package com.enums.example.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enums.example.security.MemberDetails;

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
   public Member join(Member member) throws BadCredentialsException {
      if(checkForDuplicateUsername(member.getUsername())){
         throw new BadCredentialsException("아이디가 중복되었습니다.");
      }
      
      String encodedPassowrd = passwordEncoder.encode(member.getPassword());
      member.setPassword(encodedPassowrd);
      member.setCreateDate(LocalDateTime.now());

      return memberRepository.save(member);
   }

   // 로그인
   // 사용자 이름을 기반으로 사용자 정보를 데이터베이스에서 검색하고, 해당 사용자의 권한을 "USER"로 설정하여 Spring Security에서 사용자를 인증하는 데 사용됩니다.
   // loadUserByUsername 메서드는 사용자 이름(username)을 받아서 해당 사용자의 정보를 검색하고,
   // 그 정보를 기반으로 인증 객체(UserDetails)를 반환합니다.
   
   // loadUserByUsername(String username):
   // 이 메서드는 사용자 이름(username)을 받아 사용자 정보를 검색하고 해당 정보를 이용하여 UserDetails 객체를 반환합니다.
   @Override
   public UserDetails loadUserByUsername(String username) 
		   throws UsernameNotFoundException {
	  // 주어진 사용자 이름을 사용하여 사용자 정보를 데이터베이스나 다른 저장소에서 조회합니다. 
	  // findByUsername 메서드는 사용자 이름에 해당하는 회원 정보를 검색합니다.
      Member member = memberRepository.findByUsername(username);
      // 사용자 정보(member)가 검색되지 않으면, UsernameNotFoundException을 던져서 사용자를 찾을 수 없다는 예외를 발생시킵니다.
      // 이 예외는 Spring Security에서 사용자를 찾을 수 없을 때 던져집니다.
      if(member == null){
         throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
      }
      // 사용자의 권한을 나타내는 GrantedAuthority 객체의 목록을 생성합니다.
      // 이 코드에서는 "USER" 권한을 하나 생성하여 목록에 추가합니다.
      // 실제로는 사용자의 권한이 더 다양하거나 동적으로 설정될 수 있습니다.
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("USER"));
      // MemberDetails 클래스는 사용자의 세부 정보와 권한 정보를 포함하는 사용자 세부 정보 클래스입니다.
      // 이 클래스는 UserDetails 인터페이스를 구현하며, 사용자의 세부 정보와 권한 정보를 제공하는 역할을 합니다.
      // Member 객체와 권한 목록(authorities)을 사용하여 MemberDetails 객체를 생성하고 반환합니다.
      // 이렇게 생성된 MemberDetails 객체는 Spring Security에서 사용자 인증 및 권한 부여에 사용됩니다.
      return new MemberDetails(member, authorities);
   }

   
}
