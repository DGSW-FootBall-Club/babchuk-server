package com.rice.babchuk.domain.match.controller

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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


    @GetMapping
    @Operation(summary = "매치 조회", description = "모든 매치를 조회합니다")
    fun getAllMatches() =
        BaseResponse.of(matchService.getMatches(), message = "매치 조회 성공")


    @PostMapping("/{matchId}/join")
    @Operation(summary = "매치 신청", description = "매치에 신청합니다")
    fun joinMatch(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
        @RequestBody @Valid request: JoinMatchRequest
    ) = matchService.joinMatch(matchId, userId.toLong(), request)
        .let { BaseResponse.success(message = "매치 신청 성공") }


    @GetMapping("/{matchId}")
    @Operation(summary = "매치 상세 조회", description = "매치 상세 정보를 조회합니다")
    fun getMatchDetail(
        @PathVariable matchId: Long
    ) = BaseResponse.of(
        matchService.getMatchDetail(matchId),
        message = "매치 상세 조회 성공"
    )
}