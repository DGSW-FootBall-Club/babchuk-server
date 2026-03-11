package com.rice.babchuk.domain.match.service

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest

interface MatchService {
    fun createMatch(request: CreateMatchRequest)
}