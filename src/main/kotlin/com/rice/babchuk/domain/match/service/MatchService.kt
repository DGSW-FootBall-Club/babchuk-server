package com.rice.babchuk.domain.match.service

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchResponse

interface MatchService {
    fun createMatch(request: CreateMatchRequest)
    fun getMatches(): List<MatchResponse>
}