package com.enums.example.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enums.example.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
   
   public Member findByUsername(String username);
   public Long countByUsername(String username);
}
