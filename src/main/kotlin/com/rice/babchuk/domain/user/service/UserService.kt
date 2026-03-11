package com.rice.babchuk.domain.user.service

import com.rice.babchuk.domain.user.dto.response.UserResponse

interface UserService {
    fun getMyInfo(username: String): UserResponse
    fun getAllUser(): List<UserResponse>
}