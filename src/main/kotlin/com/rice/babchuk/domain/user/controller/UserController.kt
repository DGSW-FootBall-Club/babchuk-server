package com.rice.babchuk.domain.user.controller

import com.rice.babchuk.domain.user.service.UserService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
@Tag(name = "유저 API", description = "유저 조회")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    @Operation(summary = "내정보 조회", description = "내정보를 조회합니다")
    fun getMyInfo(@AuthenticationPrincipal userId: String) =
        BaseResponse.of(
            userService.getMyInfo(UUID.fromString(userId)),
            message = "내 정보 조회 성공"
        )
}