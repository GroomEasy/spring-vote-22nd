package com.ceos.springvote22nd.domain.user.dto.request;

import com.ceos.springvote22nd.entity.enums.Part;
import com.ceos.springvote22nd.entity.enums.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpRequestDTO {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm;

    @NotNull(message = "본인이 속한 팀을 선택해주세요.")
    @Schema(description = "소속 팀", implementation = Team.class)
    private Team team;

    @NotNull(message = "본인이 속한 파트를 선택해주세요.")
    @Schema(description = "소속 파트", implementation = Part.class)
    private Part part;

    // 비밀번호 일치 검증 메서드
    @Schema(hidden = true)
    @JsonIgnore
    public boolean isPasswordMatch() {
        return password != null && password.equals(passwordConfirm);
    }
}
