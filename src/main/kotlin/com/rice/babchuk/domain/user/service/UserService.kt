package com.rice.babchuk.domain.user.service

import com.rice.babchuk.domain.user.dto.request.UpdateUserRequest
import com.rice.babchuk.domain.user.dto.response.UserResponse

interface UserService {
    fun getMyInfo(): UserResponse
    fun getAllUser(): List<UserResponse>
    fun updateUserInfo(request: UpdateUserRequest)
}