package com.rice.babchuk.domain.auth.dto.request

import com.rice.babchuk.domain.auth.domain.enum.GenderType
import com.rice.babchuk.domain.auth.domain.enum.SkillType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "회원가입 요청")
data class SignUpRequest(

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    val profileImage: String?,

    @field:NotBlank
    @Schema(description = "사용자 ID", example = "testuser")
    val username: String,

    @field:NotBlank
    @Schema(description = "비밀번호", example = "test123")
    val password: String,

    @field:NotBlank
    @Schema(description = "닉네임", example = "테스트유저")
    val nickname: String,

    @field:NotNull
    @Schema(description = "등급", example = "3")
    val grade: Int,

    @field:NotNull
    @Schema(description = "축구 실력", example = "BEGINNER")
    val skillType: SkillType,

    @field:NotNull
    @Schema(description = "성별", example = "MALE")
    val gender: GenderType
)