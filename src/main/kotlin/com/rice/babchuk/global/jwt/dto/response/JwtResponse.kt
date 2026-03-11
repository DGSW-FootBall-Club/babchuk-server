package com.rice.babchuk.global.jwt.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "JWT 토큰 응답")
data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)
