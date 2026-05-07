package com.rice.babchuk.domain.user.dto.request

import com.rice.babchuk.domain.user.domain.enum.GenderType
import com.rice.babchuk.domain.user.domain.enum.SkillType
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateUserRequest(
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    val profileImage: String? = null,

    @Schema(description = "축구 실력", example = "BEGINNER")
    val skillType: SkillType? = null,

    @Schema(description = "성별", example = "MALE")
    val gender: GenderType? = null,
)
