package com.ceos.springvote22nd.domain.auth.dto.response;

import com.ceos.springvote22nd.entity.enums.Part;
import com.ceos.springvote22nd.entity.enums.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private Team team;
    private Part part;
}
