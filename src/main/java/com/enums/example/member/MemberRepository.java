package com.enums.example.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
   
   public Member findByUsername(String username);
   public Long countByUsername(String username);
}
