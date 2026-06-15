package com.rice.babchuk.domain.auth.service

import com.rice.babchuk.domain.auth.dto.request.LoginRequest
import com.rice.babchuk.domain.auth.dto.request.SignUpRequest
import com.rice.babchuk.domain.auth.dto.response.LoginResponse

interface AuthService {
    fun loginWithDauth(accessToken: String): LoginResponse
    fun login(request: LoginRequest): LoginResponse
    fun signup(request: SignUpRequest)
}
