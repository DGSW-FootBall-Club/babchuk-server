package com.rice.babchuk.domain.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 응답 (DAuth/자체 공통)")
data class LoginResponse(
    @Schema(description = "밥축 자체 access token")
    val accessToken: String,

    @Schema(description = "밥축 자체 refresh token")
    val refreshToken: String,
)
