package com.rice.babchuk.domain.auth.controller

import com.rice.babchuk.domain.auth.dto.request.DauthLoginRequest
import com.rice.babchuk.domain.auth.service.AuthService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "인증 API")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/dauth")
    @Operation(summary = "DAuth 로그인")
    fun loginWithDauth(@Valid @RequestBody request: DauthLoginRequest) =
        BaseResponse.of(authService.loginWithDauth(request.accessToken), message = "로그인 성공")
}
