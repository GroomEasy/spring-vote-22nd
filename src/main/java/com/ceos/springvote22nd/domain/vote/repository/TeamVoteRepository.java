package com.ceos.springvote22nd.domain.vote.repository;

import com.ceos.springvote22nd.domain.vote.dto.response.TeamVoteResponseDTO;
import com.ceos.springvote22nd.entity.TeamVote;
import com.ceos.springvote22nd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamVoteRepository extends JpaRepository<TeamVote, Long> {
    // 이미 투표했는지 검사하기 위함
    boolean existsByVoter(User voter);

    // 팀별 득표수 조회 (투표가 있는 팀만 조회됨)
    @Query("SELECT new com.ceos.springvote22nd.domain.vote.dto.response.TeamVoteResponseDTO(v.team, COUNT(v)) " +
            "FROM TeamVote v " +
            "GROUP BY v.team")
    List<TeamVoteResponseDTO> findVoteCounts();
}