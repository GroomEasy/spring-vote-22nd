package com.ceos.springvote22nd.domain.vote.dto.request;

import com.ceos.springvote22nd.entity.enums.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamVoteRequestDTO {

    @Schema(description = "투표할 팀 이름", example = "MENUAL")
    @NotNull(message = "팀을 선택해주세요.")
    private Team team;
}