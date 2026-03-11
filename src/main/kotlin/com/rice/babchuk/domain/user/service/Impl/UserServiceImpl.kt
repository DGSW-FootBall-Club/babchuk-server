package com.rice.babchuk.domain.user.service.Impl

import com.rice.babchuk.domain.user.dto.response.UserResponse
import com.rice.babchuk.domain.user.error.UserError
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.domain.user.service.UserService
import com.rice.babchuk.global.error.CustomException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun getMyInfo(userId: UUID): UserResponse {

        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(UserError.USER_NOT_FOUND_BY_ID) }

        return UserResponse(
            profileImage = user.profileImage,
            nickname = user.nickname,
            grade = user.grade,
            skillType = user.skillType,
            gender = user.gender
        )
    }
}