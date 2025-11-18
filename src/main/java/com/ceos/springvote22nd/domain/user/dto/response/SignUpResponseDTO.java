package com.ceos.springvote22nd.domain.user.dto.response;

import com.ceos.springvote22nd.entity.enums.Part;
import com.ceos.springvote22nd.entity.enums.Team;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SignUpResponseDTO {

    private Long userId;
    private String username;
    private Team team;
    private Part part;
    private String email;

    private LocalDateTime createdAt;


}
