package com.rice.babchuk.domain.match.service.Impl

import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchResponse
import com.rice.babchuk.domain.match.error.MatchError
import com.rice.babchuk.domain.match.mapper.MatchMapper
import com.rice.babchuk.domain.match.repository.MatchRepository
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
) : MatchService {
    override fun createMatch(request: CreateMatchRequest) {

        if (request.teamACaptainId == request.teamBCaptainId) {
            throw CustomException(MatchError.SAME_CAPTAIN)
        }

        val teamACaptain = userRepository.findById(request.teamACaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        val teamBCaptain = userRepository.findById(request.teamBCaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        matchRepository.save(
            MatchMapper.toEntity(request, teamACaptain, teamBCaptain)
        )
    }

    override fun getMatches(): List<MatchResponse> {
        return matchRepository.findAllWithCaptains()
            .map { MatchMapper.toResponse(it) }
    }
}