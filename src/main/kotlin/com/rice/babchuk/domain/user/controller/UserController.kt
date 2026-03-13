package com.rice.babchuk.domain.user.controller

import com.rice.babchuk.domain.user.dto.request.UpdateUserRequest
import com.rice.babchuk.domain.user.service.UserService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "유저 API")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    @Operation(summary = "내정보 조회")
    fun getMyInfo() = BaseResponse.of(
        userService.getMyInfo(), message = "내 정보 조회 성공"
    )

    @GetMapping("/all")
    @Operation(summary = "전체 유저 조회")
    fun getAllUser() = BaseResponse.of(
        userService.getAllUser(), message = "전체 유저 조회 성공"
    )

    @PatchMapping("/me")
    @Operation(summary = "내정보 수정")
    fun updateProfile(
        @RequestBody request: UpdateUserRequest
    ) = userService.updateUserInfo(request)
        .let { BaseResponse.success(message = "프로필 수정 성공") }
}