package com.rice.babchuk.domain.auth.service

import com.rice.babchuk.domain.auth.dto.request.LoginRequest
import com.rice.babchuk.domain.auth.dto.request.SignUpRequest
import com.rice.babchuk.global.jwt.dto.response.JwtResponse

interface AuthService {
    fun login(request: LoginRequest): JwtResponse
    fun signup(request: SignUpRequest)
    fun reissue(refreshToken: String): JwtResponse
}