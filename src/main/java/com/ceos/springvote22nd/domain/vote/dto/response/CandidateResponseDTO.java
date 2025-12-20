package com.ceos.springvote22nd.domain.vote.dto.response;

import com.ceos.springvote22nd.entity.enums.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateResponseDTO {
    private Long id;
    private String name;
    private String teamName;
    private Long voteCount;

    public CandidateResponseDTO(Long id, String name, Team team, Long voteCount) {
        this.id = id;
        this.name = name;
        this.teamName = team.toString();;
        this.voteCount = voteCount;
    }
}