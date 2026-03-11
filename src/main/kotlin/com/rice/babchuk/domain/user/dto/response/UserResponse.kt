package com.rice.babchuk.domain.user.dto.response

import com.rice.babchuk.domain.auth.domain.enum.GenderType
import com.rice.babchuk.domain.auth.domain.enum.SkillType
import io.swagger.v3.oas.annotations.media.Schema

data class UserResponse(
    val profileImage: String,
    var nickname: String,
    @Schema(description = "학년반번호", example = "3307")
    var grade: Int,
    var skillType: SkillType,
    var gender: GenderType
)