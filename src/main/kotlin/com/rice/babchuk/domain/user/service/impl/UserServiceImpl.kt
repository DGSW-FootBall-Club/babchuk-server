package com.rice.babchuk.domain.user.service.impl

import com.rice.babchuk.domain.user.dto.request.UpdateUserRequest
import com.rice.babchuk.domain.user.dto.response.UserResponse
import com.rice.babchuk.domain.user.mapper.UserMapper
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.domain.user.service.UserService
import com.rice.babchuk.global.security.holder.SecurityHolder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val securityHolder: SecurityHolder,
) : UserService {
    override fun getMyInfo(): UserResponse {
        return userMapper.toUserResponse(securityHolder.user)
    }

    override fun getAllUser(): List<UserResponse> {
        val users = userRepository.findAll()
        return users.map { userMapper.toUserResponse(it) }
    }

    @Transactional
    override fun updateUserInfo(request: UpdateUserRequest) {
        val user = securityHolder.user
        user.updateProfile(request)
    }
}