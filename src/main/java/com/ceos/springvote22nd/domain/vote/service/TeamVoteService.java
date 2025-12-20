package com.ceos.springvote22nd.domain.vote.service;

import com.ceos.springvote22nd.domain.user.exception.UserErrorCode;
import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.domain.vote.dto.request.TeamVoteRequestDTO;
import com.ceos.springvote22nd.domain.vote.dto.response.TeamVoteResponseDTO;
import com.ceos.springvote22nd.domain.vote.exception.VoteErrorCode;
import com.ceos.springvote22nd.domain.vote.repository.TeamVoteRepository;
import com.ceos.springvote22nd.entity.TeamVote;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.entity.enums.Team;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // TODO: 리팩토링 필요
    public List<TeamVoteResponseDTO> getTeamVoteCounts() {
        // DB에서 투표가 있는 팀들의 집계 데이터를 가져옴
        List<TeamVoteResponseDTO> voteCounts = teamVoteRepository.findVoteCounts();

        // Team Enum에 있는 모든 팀을 순회하며 초기화
        Map<String, Long> voteCountMap = new HashMap<>();
        for (Team team : Team.values()) {
            voteCountMap.put(team.toString(), 0L);
        }

        // DB에서 가져온 값을 덮어씀
        for (TeamVoteResponseDTO dto : voteCounts) {
            voteCountMap.put(dto.getTeamName(), dto.getVoteCount());
        }

        // Map을 다시 List로 변환하고, 득표수 내림차순 정렬
        return voteCountMap.entrySet().stream()
                .map(entry -> new TeamVoteResponseDTO(Team.valueOf(entry.getKey()), entry.getValue()))
                .sorted((a, b) -> Long.compare(b.getVoteCount(), a.getVoteCount())) // 득표수 많은 순
                .collect(Collectors.toList());
    }
}