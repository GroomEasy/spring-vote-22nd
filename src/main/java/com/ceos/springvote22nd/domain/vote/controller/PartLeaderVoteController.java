package com.ceos.springvote22nd.domain.vote.controller;

import com.ceos.springvote22nd.domain.common.dto.response.CommonResponse;
import com.ceos.springvote22nd.domain.vote.dto.request.VoteRequestDTO;
import com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO;
import com.ceos.springvote22nd.domain.vote.service.PartLeaderVoteService;
import com.ceos.springvote22nd.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes/part-leader")
@RequiredArgsConstructor
@Tag(name = "파트장 투표 API", description = "파트장 투표 관련 기능")
public class PartLeaderVoteController {

    private final PartLeaderVoteService partLeaderVoteService;

    @Operation(summary = "후보 목록 조회", description = "내 파트의 후보자들과 현재 득표수를 조회합니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CandidateResponseDTO>>> getCandidates(
            @AuthenticationPrincipal Long userId
    ) {
        List<CandidateResponseDTO> candidates = partLeaderVoteService.getPartLeaderCandidates(userId);
        return ResponseEntity.ok(CommonResponse.success(candidates));
    }


    @Operation(summary = "투표하기", description = "특정 후보에게 투표합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> vote(
            @AuthenticationPrincipal Long userId,
            @RequestBody VoteRequestDTO request
    ) {
        partLeaderVoteService.votePartLeader(userId, request);

        return ResponseEntity.ok(CommonResponse.success(null));
    }
}