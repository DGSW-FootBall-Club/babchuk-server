package com.rice.babchuk.domain.user.service

import com.rice.babchuk.domain.user.dto.response.UserResponse
import java.util.UUID

interface UserService {
    fun getMyInfo(userId: UUID): UserResponse
}