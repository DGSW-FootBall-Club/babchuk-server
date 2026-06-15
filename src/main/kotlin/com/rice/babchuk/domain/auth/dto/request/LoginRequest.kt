package com.rice.babchuk.domain.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "자체 로그인 요청")
data class LoginRequest(
    @field:NotBlank
    @Schema(description = "아이디", example = "testuser")
    val username: String,

    @field:NotBlank
    @Schema(description = "비밀번호")
    val password: String,
)
