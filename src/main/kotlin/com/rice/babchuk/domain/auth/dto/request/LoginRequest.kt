package com.rice.babchuk.domain.auth.dto.request
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "로그인 요청")
data class LoginRequest(
    @field:NotBlank
    @Schema(description = "사용자 ID", example = "testuser")
    val userid: String,

    @field:NotBlank
    @Schema(description = "비밀번호", example = "test123")
    val password: String
)