package com.ceos.springvote22nd.domain.vote.repository;

import com.ceos.springvote22nd.entity.TeamVote;
import com.ceos.springvote22nd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamVoteRepository extends JpaRepository<TeamVote, Long> {
    // 이미 투표했는지 검사하기 위함
    boolean existsByVoter(User voter);
}