package com.rice.babchuk.domain.user.dto.request

import com.rice.babchuk.domain.auth.domain.enum.SkillType
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateUserRequest(
    val profileImage: String? = null,
    val nickname: String? = null,
    @Schema(example = "3307")
    val grade: Int? = null,
    val skillType: SkillType? = null
)