package com.rice.babchuk.domain.match.controller

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/match")
@Tag(name = "매치 API", description = "매치 생성 및 조회")
class MatchController(
    private val matchService: MatchService
) {
    @PostMapping
    @Operation(summary = "매치 생성", description = "새로운 매치를 생성합니다")
    fun createMatch(
        @RequestBody @Valid request: CreateMatchRequest
    ) = matchService.createMatch(request)
        .let { BaseResponse.success(message = "매치 생성 성공") }
}