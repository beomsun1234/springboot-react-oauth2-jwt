package com.bs.hellooauth2jwt.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepostitory extends JpaRepository<Member,Long> {
}
