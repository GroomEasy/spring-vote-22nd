package com.ceos.springvote22nd.domain.vote.repository;

import com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO;
import com.ceos.springvote22nd.entity.PartLeaderVote;
import com.ceos.springvote22nd.entity.enums.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface PartLeaderVoteRepository extends JpaRepository<PartLeaderVote, Long> {

    // 후보자가 받은 표 세는 용도
    int countByCandidate(User candidate);

    // 이미 투표했는지 여부 확인하는 용도
    boolean existsByVoter(User voter);

}
