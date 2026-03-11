package com.rice.babchuk.domain.user.mapper

import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.dto.response.UserResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            profileImage = user.profileImage,
            nickname = user.nickname,
            grade = user.grade,
            skillType = user.skillType,
            gender = user.gender
        )
    }
}