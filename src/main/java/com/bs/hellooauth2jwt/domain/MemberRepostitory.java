package com.bs.hellooauth2jwt.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepostitory extends JpaRepository<Member,Long> {

    Optional<Member>findByEmail(String email);
}
