package com.ceos.springvote22nd.domain.vote.service;

import com.ceos.springvote22nd.domain.user.exception.UserErrorCode;
import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.domain.vote.dto.request.VoteRequestDTO;
import com.ceos.springvote22nd.domain.vote.exception.VoteErrorCode;
import com.ceos.springvote22nd.domain.vote.repository.PartLeaderVoteRepository;
import com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO;
import com.ceos.springvote22nd.entity.PartLeaderVote;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.entity.enums.Part;
import com.ceos.springvote22nd.global.config.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartLeaderVoteService {

    private final UserRepository userRepository;
    private final PartLeaderVoteRepository partLeaderVoteRepository;

    /**
     * 파트장 후보 리스트 조회
     */
    public List<CandidateResponseDTO> getPartLeaderCandidates(Long userId) {

        // userId로 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        // 자신이 해당하는 파트
        Part myPart = user.getPart();

        return userRepository.findAllCandidatesWithVoteCount(myPart);
    }

    public void votePartLeader(Long userId, VoteRequestDTO request) {

        // 투표자 조회
        User voter = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        // 후보자 조회
        User candidate = userRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        // 투표자와 후보자의 파트가 같아야 함
        if (voter.getPart() != candidate.getPart()) {
            throw new GlobalException(VoteErrorCode.INVALID_PART);
        }

        // 한 사람은 한번만 투표할 수 있음
        if (partLeaderVoteRepository.existsByVoter(voter)) {
            throw new GlobalException(VoteErrorCode.ALREADY_VOTED);
        }

        // 투표 엔티티 생성
        PartLeaderVote vote = PartLeaderVote.builder()
                .voter(voter)
                .candidate(candidate)
                .build();
        // 저장
        partLeaderVoteRepository.save(vote);
    }

}