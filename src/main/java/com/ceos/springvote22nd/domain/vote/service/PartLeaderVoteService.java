package com.ceos.springvote22nd.domain.vote.service;

import com.ceos.springvote22nd.domain.user.repository.UserRepository;
import com.ceos.springvote22nd.domain.vote.repository.PartLeaderVoteRepository;
import com.ceos.springvote22nd.domain.vote.dto.response.CandidateResponseDTO;
import com.ceos.springvote22nd.entity.User;
import com.ceos.springvote22nd.entity.enums.Part;
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

    public List<CandidateResponseDTO> getPartLeaderCandidates(User user) {

        // 자신이 해당하는 파트
        Part myPart = user.getPart();

        return userRepository.findAllCandidatesWithVoteCount(myPart);
    }


}