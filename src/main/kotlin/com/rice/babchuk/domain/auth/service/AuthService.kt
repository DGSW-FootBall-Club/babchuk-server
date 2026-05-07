package com.rice.babchuk.domain.auth.service

import com.rice.babchuk.domain.auth.dto.response.DauthLoginResponse

interface AuthService {
    fun loginWithDauth(accessToken: String): DauthLoginResponse
}
