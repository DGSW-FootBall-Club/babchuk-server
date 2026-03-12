package com.rice.babchuk.domain.match.service.Impl

import com.rice.babchuk.domain.match.domain.entity.MatchParticipant
import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.match.domain.enum.TeamType
import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchDetailResponse
import com.rice.babchuk.domain.match.dto.response.MatchResponse
import com.rice.babchuk.domain.match.error.MatchError
import com.rice.babchuk.domain.match.mapper.MatchMapper
import com.rice.babchuk.domain.match.repository.MatchParticipantRepository
import com.rice.babchuk.domain.match.repository.MatchRepository
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
    private val matchParticipantRepository: MatchParticipantRepository,
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

    override fun joinMatch(matchId: Long, userId: Long, request: JoinMatchRequest) {

        val match = matchRepository.findById(matchId)
            .orElseThrow { CustomException(MatchError.MATCH_NOT_FOUND) }

        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        if (match.teamACaptain?.id == userId || match.teamBCaptain?.id == userId) {
            throw CustomException(MatchError.CAPTAIN_CANNOT_JOIN)
        }

        if (matchParticipantRepository.existsByMatchIdAndUserId(matchId, userId)) {
            throw CustomException(MatchError.ALREADY_JOINED)
        }

        if (matchParticipantRepository.countByMatchIdAndTeamType(matchId, request.teamType) >= match.teamSize) {
            throw CustomException(MatchError.TEAM_FULL)
        }

        matchParticipantRepository.save(
            MatchParticipant(match = match, user = user, teamType = request.teamType)
        )

        val teamACount = matchParticipantRepository.countByMatchIdAndTeamType(matchId, TeamType.TEAM_A)
        val teamBCount = matchParticipantRepository.countByMatchIdAndTeamType(matchId, TeamType.TEAM_B)

        if (teamACount >= match.teamSize && teamBCount >= match.teamSize) {
            match.status = MatchStatus.CLOSED
            matchRepository.save(match)
        }
    }

    override fun getMatchDetail(matchId: Long): MatchDetailResponse {
        val match = matchRepository.findByIdWithDetails(matchId)
            ?: throw CustomException(MatchError.MATCH_NOT_FOUND)

        return MatchMapper.toDetailResponse(match)
    }
}