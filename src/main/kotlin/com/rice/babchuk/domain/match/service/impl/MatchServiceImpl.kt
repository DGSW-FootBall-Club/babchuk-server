package com.rice.babchuk.domain.match.service.impl

import com.rice.babchuk.domain.match.domain.entity.MatchParticipant
import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.match.domain.enum.TeamType
import com.rice.babchuk.domain.match.dto.request.JoinMatchRequest
import com.rice.babchuk.domain.match.dto.request.MatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchDetailResponse
import com.rice.babchuk.domain.match.dto.response.MatchResponse
import com.rice.babchuk.domain.match.error.MatchError
import com.rice.babchuk.domain.match.mapper.MatchMapper
import com.rice.babchuk.domain.match.repository.MatchParticipantRepository
import com.rice.babchuk.domain.match.repository.MatchRepository
import com.rice.babchuk.domain.match.service.MatchService
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import com.rice.babchuk.global.security.holder.SecurityHolder
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MatchServiceImpl(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository,
    private val matchParticipantRepository: MatchParticipantRepository,
    private val securityHolder: SecurityHolder
) : MatchService {

    override fun createMatch(request: MatchRequest) {
        if (request.teamACaptainId == request.teamBCaptainId) {
            throw CustomException(MatchError.SAME_CAPTAIN)
        }

        val teamACaptain = userRepository.findById(request.teamACaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        val teamBCaptain = userRepository.findById(request.teamBCaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        matchRepository.save(
            MatchMapper.toEntity(
                request,
                teamACaptain,
                teamBCaptain,
                securityHolder.user
            )
        )
    }

    override fun getMatches(): List<MatchResponse> {
        return matchRepository.findAllWithCaptains()
            .map { MatchMapper.toResponse(it) }
    }

    override fun joinMatch(matchId: Long, request: JoinMatchRequest) {
        val currentUser = securityHolder.user

        val match = matchRepository.findById(matchId)
            .orElseThrow { CustomException(MatchError.MATCH_NOT_FOUND) }

        if (match.status == MatchStatus.CLOSED || match.status == MatchStatus.FINISHED) {
            throw CustomException(MatchError.MATCH_NOT_OPEN)
        }

        if (match.teamACaptain?.id == currentUser.id || match.teamBCaptain?.id == currentUser.id) {
            throw CustomException(MatchError.CAPTAIN_CANNOT_JOIN)
        }

        if (matchParticipantRepository.existsByMatchIdAndUserId(matchId, currentUser.id)) {
            throw CustomException(MatchError.ALREADY_JOINED)
        }

        val perTeam = (match.teamSize / 2) - 1

        if (matchParticipantRepository.countByMatchIdAndTeamType(matchId, request.teamType) >= perTeam) {
            throw CustomException(MatchError.TEAM_FULL)
        }

        matchParticipantRepository.save(
            MatchParticipant(
                match = match,
                user = currentUser,
                teamType = request.teamType
            )
        )

        val teamACount = matchParticipantRepository.countByMatchIdAndTeamType(matchId, TeamType.TEAM_A)
        val teamBCount = matchParticipantRepository.countByMatchIdAndTeamType(matchId, TeamType.TEAM_B)

        if (teamACount >= perTeam && teamBCount >= perTeam) {
            match.status = MatchStatus.CLOSED
        }
    }

    override fun getMatchDetail(matchId: Long): MatchDetailResponse {
        val match = matchRepository.findByIdWithDetails(matchId)
            ?: throw CustomException(MatchError.MATCH_NOT_FOUND)

        return MatchMapper.toDetailResponse(match)
    }

    override fun cancelMatch(matchId: Long) {
        val currentUser = securityHolder.user

        val participant = matchParticipantRepository.findByMatchIdAndUserId(matchId, currentUser.id)
            ?: throw CustomException(MatchError.NOT_JOINED)

        matchParticipantRepository.delete(participant)

        val match = participant.match
        if (match.status == MatchStatus.CLOSED) {
            match.status = MatchStatus.OPEN
        }
    }

    override fun updateMatch(matchId: Long, request: MatchRequest) {
        val currentUser = securityHolder.user

        val match = matchRepository.findById(matchId)
            .orElseThrow { CustomException(MatchError.MATCH_NOT_FOUND) }

        if (match.author != currentUser) {
            throw CustomException(MatchError.NOT_AUTHORIZED)
        }

        if (match.status == MatchStatus.FINISHED || match.status == MatchStatus.CLOSED) {
            throw CustomException(MatchError.CANNOT_MODIFY)
        }

        if (request.teamACaptainId == request.teamBCaptainId) {
            throw CustomException(MatchError.SAME_CAPTAIN)
        }

        val teamACaptain = userRepository.findById(request.teamACaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        val teamBCaptain = userRepository.findById(request.teamBCaptainId)
            .orElseThrow { CustomException(MatchError.CAPTAIN_NOT_FOUND) }

        match.updateMatch(request, teamACaptain, teamBCaptain)
    }

    override fun deleteMatch(matchId: Long) {
        val currentUser = securityHolder.user

        val match = matchRepository.findById(matchId)
            .orElseThrow { CustomException(MatchError.MATCH_NOT_FOUND) }

        if (match.author != currentUser) {
            throw CustomException(MatchError.NOT_AUTHORIZED)
        }

        matchRepository.delete(match)
    }

    override fun isJoined(matchId: Long): Boolean {
        val currentUser = securityHolder.user
        return matchParticipantRepository.existsByMatchIdAndUserId(matchId, currentUser.id)
    }
}