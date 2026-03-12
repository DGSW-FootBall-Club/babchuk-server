package com.rice.babchuk.domain.match.service

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchDetailResponse
import com.rice.babchuk.domain.match.dto.response.MatchResponse

interface MatchService {
    fun createMatch(request: CreateMatchRequest)
    fun getMatches(): List<MatchResponse>
    fun joinMatch(matchId: Long, userId: Long, request: JoinMatchRequest)  // 추가
    fun getMatchDetail(matchId: Long): MatchDetailResponse
}