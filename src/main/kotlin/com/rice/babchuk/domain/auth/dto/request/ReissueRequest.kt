package com.rice.babchuk.domain.auth.dto.request

import jakarta.validation.constraints.NotBlank

data class ReissueRequest(
    val refreshToken: String
)

