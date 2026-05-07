package com.rice.babchuk.domain.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DAuth 로그인 응답")
data class DauthLoginResponse(
    @Schema(description = "밥축 자체 access token")
    val accessToken: String,
)
