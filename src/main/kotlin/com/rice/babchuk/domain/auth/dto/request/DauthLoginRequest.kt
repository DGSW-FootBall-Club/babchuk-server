package com.rice.babchuk.domain.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "DAuth 로그인 요청")
data class DauthLoginRequest(
    @field:NotBlank
    @Schema(description = "DAuth가 발급한 access token")
    val accessToken: String,
)
