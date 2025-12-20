package com.ceos.springvote22nd.domain.vote.controller;

import com.ceos.springvote22nd.domain.common.dto.response.CommonResponse;
import com.ceos.springvote22nd.domain.vote.dto.request.TeamVoteRequestDTO;
import com.ceos.springvote22nd.domain.vote.dto.response.TeamVoteResponseDTO;
import com.ceos.springvote22nd.domain.vote.service.TeamVoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes/team")
@RequiredArgsConstructor
@Tag(name = "팀 투표 API", description = "팀 투표 관련 기능")
public class TeamVoteController {

    private final TeamVoteService teamVoteService;

    @Operation(summary = "팀 투표 하기", description = "특정 팀에게 투표합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> voteTeam(
            @AuthenticationPrincipal Long userId,
            @RequestBody TeamVoteRequestDTO request
    ) {
        teamVoteService.voteTeam(userId, request);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    // 팀 투표 순위 조회
    @Operation(summary = "팀 투표 순위 조회", description = "모든 팀의 득표수를 내림차순으로 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TeamVoteResponseDTO>>> getTeamVotes() {
        List<TeamVoteResponseDTO> result = teamVoteService.getTeamVoteCounts();
        return ResponseEntity.ok(CommonResponse.success(result));
    }
}