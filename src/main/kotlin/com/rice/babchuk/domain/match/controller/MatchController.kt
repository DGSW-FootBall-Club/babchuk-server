package com.rice.babchuk.domain.match.controller

import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.dto.request.MatchRequest
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/match")
@Tag(name = "매치 API")
class MatchController(
    private val matchService: MatchService
) {
    @PostMapping
    @Operation(summary = "매치 생성")
    fun createMatch(
        @RequestBody @Valid request: MatchRequest
    ) = matchService.createMatch(request)
        .let { BaseResponse.success(message = "매치 생성 성공") }


    @GetMapping
    @Operation(summary = "매치 조회")
    fun getAllMatches() =
        BaseResponse.of(matchService.getMatches(), message = "매치 조회 성공")


    @PostMapping("/{matchId}/join")
    @Operation(summary = "매치 신청")
    fun joinMatch(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
        @RequestBody @Valid request: JoinMatchRequest
    ) = matchService.joinMatch(matchId, userId.toLong(), request)
        .let { BaseResponse.success(message = "매치 신청 성공") }


    @GetMapping("/{matchId}")
    @Operation(summary = "매치 상세 조회")
    fun getMatchDetail(
        @PathVariable matchId: Long
    ) = BaseResponse.of(
        matchService.getMatchDetail(matchId),
        message = "매치 상세 조회 성공"
    )

    @DeleteMapping("/{matchId}/cancel")
    @Operation(summary = "매치 신청 취소")
    fun cancelMatch(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
    ) = matchService.cancelMatch(matchId, userId.toLong())
        .let { BaseResponse.success(message = "매치 신청 취소 성공") }

    @PatchMapping("/{matchId}")
    @Operation(summary = "매치 수정")
    fun updateMatch(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
        @RequestBody @Valid request: MatchRequest
    ) = matchService.updateMatch(matchId, userId.toLong(), request)
        .let { BaseResponse.success(message = "매치 수정 성공") }

    @DeleteMapping("/{matchId}")
    @Operation(summary = "매치 삭제")
    fun deleteMatch(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
    ) = matchService.deleteMatch(matchId, userId.toLong())
        .let { BaseResponse.success(message = "매치 삭제 성공") }

    @GetMapping("/{matchId}/joined")
    @Operation(summary = "매치 신청 여부 조회")
    fun isJoined(
        @PathVariable matchId: Long,
        @AuthenticationPrincipal userId: String,
    ) = BaseResponse.of(
        matchService.isJoined(matchId, userId.toLong()),
        message = "매치 신청 여부 조회 성공"
    )
}