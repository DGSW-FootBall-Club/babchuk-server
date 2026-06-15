package com.rice.babchuk.domain.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "자체 회원가입 요청")
data class SignUpRequest(
    @field:NotBlank
    @field:Size(min = 4, max = 50)
    @Schema(description = "아이디 (4자 이상)", example = "testuser")
    val username: String,

    @field:NotBlank
    @field:Size(min = 8)
    @Schema(description = "비밀번호 (8자 이상)", example = "test1234")
    val password: String,

    @field:NotBlank
    @Schema(description = "실명", example = "김은찬")
    val name: String,

    @field:NotNull
    @Schema(example = "3")
    val grade: Int,

    @field:NotNull
    @Schema(example = "3")
    val room: Int,

    @field:NotNull
    @Schema(example = "7")
    val number: Int,

    @Schema(description = "프로필 이미지 URL (선택)")
    val profileImage: String? = null,
)
