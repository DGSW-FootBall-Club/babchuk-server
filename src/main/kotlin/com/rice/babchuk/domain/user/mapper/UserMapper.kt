package com.rice.babchuk.domain.user.mapper

import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.dto.response.UserResponse
import org.springframework.stereotype.Component

@Component
object UserMapper {

    fun toUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            studentId = user.studentId,
            name = user.name,
            profileImage = user.profileImage,
            role = user.role,
            grade = user.grade,
            room = user.room,
            number = user.number,
            skillType = user.skillType,
            gender = user.gender,
        )
    }
}
