package com.ceos.springvote22nd.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Part {
    @Schema(description = "기획")
    PM,

    @Schema(description = "디자이너")
    DESIGNER,

    @Schema(description = "프론트엔드")
    FRONTEND,

    @Schema(description = "백엔드")
    BACKEND
}
