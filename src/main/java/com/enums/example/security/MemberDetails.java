package com.enums.example.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enums.example.member.Member;

// MemberDetails 클래스는 사용자의 세부 정보와 권한 정보를 포함하고,
// Spring Security에서 사용자를 인증하고 권한을 부여하는 데 사용됩니다.
// 이 클래스를 통해 사용자 정보와 권한 정보를 제공할 수 있어서 Spring Security가 사용자를 식별하고 권한 부여를 수행할 수 있습니다.
public class MemberDetails implements UserDetails{
   // 사용자의 관련 정보 (예: 사용자 이름, 비밀번호)를 포함
   private final Member member;
   // 사용자의 권한 정보를 가지고 있는 Set입니다.
   // GrantedAuthority는 사용자의 권한을 나타내는 Spring Security의 인터페이스입니다.
   private final Set<GrantedAuthority> authorities;

   // 생성자는 Member 객체와 권한 목록을 받아 MemberDetails 객체를 초기화합니다.
   public MemberDetails(Member member, Collection<? extends GrantedAuthority> authorities){
      this.member = member;
      this.authorities = Set.copyOf(authorities);
   }

   // 사용자의 권한 목록을 반환합니다.
   // 이 메서드는 authorities 필드를 반환하고, 사용자의 권한을 나타내는 GrantedAuthority 객체의 컬렉션을 제공합니다.
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   // 사용자의 비밀번호를 반환합니다. 이 메서드는 member 객체에서 비밀번호를 가져옵니다.
   @Override
   public String getPassword() {
      return member.getPassword();
   }

   // 사용자의 사용자 이름을 반환합니다. 이 메서드는 member 객체에서 사용자 이름을 가져옵니다.
   @Override
   public String getUsername() {
      return member.getUsername();
   }

   // isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled():
   // 이러한 메서드는 사용자의 계정 상태에 대한 정보를 반환합니다.
   // 일반적으로 true를 반환하여 계정이 만료되지 않았고 잠겨 있지 않으며 자격 증명이 만료되지 않았고 사용 가능한 상태임을 나타냅니다.
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
   
}
