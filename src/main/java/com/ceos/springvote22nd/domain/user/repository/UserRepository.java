package com.ceos.springvote22nd.domain.user.repository;

import com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.entity.enums.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByPart(Part part);

    // 파트별 후보자, 득표수 한번에 내림차순으로 조회
    @Query("SELECT new com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO(" +
            "u.id, u.username, u.team, COUNT(v)) " +
            "FROM User u " +
            "LEFT JOIN PartLeaderVote v ON v.candidate = u " +
            "WHERE u.part = :part " +
            "GROUP BY u.id, u.username, u.team " +
            "ORDER BY COUNT(v) DESC")
    List<CandidateResponseDTO> findAllCandidatesWithVoteCount(@Param("part") Part part);
}
