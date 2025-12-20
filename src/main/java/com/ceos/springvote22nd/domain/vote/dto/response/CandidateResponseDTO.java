package com.ceos.springvote22nd.domain.vote.dto.response;

import com.ceos.springvote22nd.entity.enums.Part;
import com.ceos.springvote22nd.entity.enums.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidateResponseDTO {
    private Long id;
    private Part part;
    private String name;
    private String teamName;
    private Long voteCount;

    public CandidateResponseDTO(Long id, Part part, String name, Team team, Long voteCount) {
        this.id = id;
        this.part = part;
        this.name = name;
        this.teamName = team.toString();;
        this.voteCount = voteCount;
    }
}