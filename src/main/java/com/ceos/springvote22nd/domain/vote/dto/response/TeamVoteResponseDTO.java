package com.ceos.springvote22nd.domain.vote.dto.response;

import com.ceos.springvote22nd.entity.enums.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamVoteResponseDTO {
    private String teamName;
    private Long voteCount;

    public TeamVoteResponseDTO(Team team, Long voteCount) {
        this.teamName = team.toString();
        this.voteCount = voteCount;
    }
}