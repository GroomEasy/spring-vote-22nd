package com.ceos.springvote22nd.domain.vote.service;

import com.ceos.springvote22nd.domain.user.exception.UserErrorCode;
import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.domain.vote.dto.request.TeamVoteRequestDTO;
import com.ceos.springvote22nd.domain.vote.exception.VoteErrorCode;
import com.ceos.springvote22nd.domain.vote.repository.TeamVoteRepository;
import com.ceos.springvote22nd.entity.TeamVote;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamVoteService {

    private final UserRepository userRepository;
    private final TeamVoteRepository teamVoteRepository;

    @Transactional
    public void voteTeam(Long userId, TeamVoteRequestDTO request) {
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        // 중복 투표 확인
        if (teamVoteRepository.existsByVoter(user)) {
            throw new GlobalException(VoteErrorCode.ALREADY_VOTED);
        }

        // 본인 팀에는 투표 불가
        if (user.getTeam() == request.getTeam()) {
             throw new GlobalException(VoteErrorCode.INVALID_TEAM_VOTE);
        }

        // 저장
        TeamVote vote = TeamVote.builder()
                .voter(user)
                .team(request.getTeam())
                .build();

        teamVoteRepository.save(vote);
    }
}