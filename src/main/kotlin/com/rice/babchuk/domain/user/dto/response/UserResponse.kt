package com.rice.babchuk.domain.user.dto.response

import com.rice.babchuk.domain.user.domain.enum.GenderType
import com.rice.babchuk.domain.user.domain.enum.SkillType
import io.swagger.v3.oas.annotations.media.Schema

data class UserResponse(
    @Schema(example = "0")
    val id: Long,
    @Schema(description = "학번 (grade + room + number 2자리)", example = "3307")
    val studentId: String,
    @Schema(description = "실명", example = "김은찬")
    val name: String,
    val profileImage: String?,
    @Schema(description = "DAuth role (예: STUDENT)")
    val role: String?,
    @Schema(example = "3")
    val grade: Int,
    @Schema(example = "3")
    val room: Int,
    @Schema(example = "7")
    val number: Int,
    @Schema(description = "축구 실력 (미설정시 null)")
    val skillType: SkillType?,
    @Schema(description = "성별 (미설정시 null)")
    val gender: GenderType?,
)
