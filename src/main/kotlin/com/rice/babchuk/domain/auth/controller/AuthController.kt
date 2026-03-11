package com.rice.babchuk.domain.auth.controller

import com.rice.babchuk.domain.auth.dto.request.LoginRequest
import com.rice.babchuk.domain.auth.dto.request.SignUpRequest
import com.rice.babchuk.domain.auth.dto.request.ReissueRequest
import com.rice.babchuk.domain.auth.service.AuthService
import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "인증 API", description = "회원가입, 로그인, 토큰 관리")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 ID와 비밀번호로 로그인합니다")
    fun login(@Valid @RequestBody request: LoginRequest) =
        BaseResponse.of(authService.login(request), message = "로그인 성공")

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다")
    fun signup(
        @Valid @RequestBody request: SignUpRequest
    ) = authService.signup(request).let { BaseResponse.success(message = "회원가입 성공") }

    @PostMapping("/reissue")
    @Operation(
        summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token을 발급받습니다"
    )
    fun reissue(@Valid @RequestBody request: ReissueRequest) =
        BaseResponse.of(authService.reissue(request.refreshToken), message = "토큰 재발급 성공")
}