package com.rice.babchuk.domain.match.service

import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.dto.request.MatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchDetailResponse
import com.rice.babchuk.domain.match.dto.response.MatchResponse

interface MatchService {
    fun createMatch(request: MatchRequest)
    fun getMatches(): List<MatchResponse>
    fun joinMatch(matchId: Long, request: JoinMatchRequest)
    fun getMatchDetail(matchId: Long): MatchDetailResponse
    fun cancelMatch(matchId: Long)
    fun updateMatch(matchId: Long, request: MatchRequest)
    fun deleteMatch(matchId: Long)
    fun isJoined(matchId: Long): Boolean
}