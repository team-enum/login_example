package com.enums.example.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enums.example.member.Member;

public class MemberDetails implements UserDetails{

   private final Member member;
   private final Set<GrantedAuthority> authorities;

   public MemberDetails(Member member, Collection<? extends GrantedAuthority> authorities){
      this.member = member;
      this.authorities = Set.copyOf(authorities);
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   @Override
   public String getPassword() {
      return member.getPassword();
   }

   @Override
   public String getUsername() {
      return member.getUsername();
   }

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
