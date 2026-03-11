package com.rice.babchuk.domain.user.service.Impl

import com.rice.babchuk.domain.user.dto.response.UserResponse
import com.rice.babchuk.domain.user.error.UserError
import com.rice.babchuk.domain.user.mapper.UserMapper
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.domain.user.service.UserService
import com.rice.babchuk.global.error.CustomException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserService {
    override fun getMyInfo(username: String): UserResponse {

        val user = userRepository.findByUsername(username)
            ?: throw CustomException(UserError.USER_NOT_FOUND_BY_ID)

        return userMapper.toUserResponse(user)
    }

    override fun getAllUser(): List<UserResponse> {
        val users = userRepository.findAll()
        return users.map { userMapper.toUserResponse(it) }
    }
}