package com.rice.babchuk.domain.match.mapper

import com.rice.babchuk.domain.match.domain.entity.Match
import com.rice.babchuk.domain.match.domain.enum.TeamType
import com.rice.babchuk.domain.match.dto.request.MatchRequest
import com.rice.babchuk.domain.match.dto.response.MatchDetailResponse
import com.rice.babchuk.domain.match.dto.response.MatchResponse
import com.rice.babchuk.domain.match.dto.response.TeamDetailResponse
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.mapper.UserMapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
object MatchMapper {
    fun toEntity(
        request: MatchRequest,
        teamACaptain: User,
        teamBCaptain: User
    ): Match {
        return Match(
            title = request.title,
            matchAt = LocalDateTime.of(request.matchDate, request.matchTime),
            teamSize = request.teamSize,
            durationMinutes = request.durationMinutes,
            teamACaptain = teamACaptain,
            teamBCaptain = teamBCaptain
        )
    }

    fun toResponse(match: Match) = MatchResponse(
        id = match.id,
        title = match.title,
        matchDate = match.matchAt.toLocalDate(),
        matchTime = match.matchAt.toLocalTime(),
        teamSize = match.teamSize,
        durationMinutes = match.durationMinutes,
        teamACaptain = UserMapper.toUserResponse(match.teamACaptain!!),
        teamBCaptain = UserMapper.toUserResponse(match.teamBCaptain!!),
        status = match.status
    )

    fun toDetailResponse(match: Match): MatchDetailResponse {
        val teamAMembers = match.participants
            .filter { it.teamType == TeamType.TEAM_A }
            .map { UserMapper.toUserResponse(it.user) }

        val teamBMembers = match.participants
            .filter { it.teamType == TeamType.TEAM_B }
            .map { UserMapper.toUserResponse(it.user) }

        return MatchDetailResponse(
            id = match.id,
            title = match.title,
            matchDate = match.matchAt.toLocalDate(),
            matchTime = match.matchAt.toLocalTime(),
            teamSize = match.teamSize,
            durationMinutes = match.durationMinutes,
            status = match.status,
            teamA = TeamDetailResponse(
                captain = UserMapper.toUserResponse(match.teamACaptain!!),
                members = teamAMembers,
                currentSize = teamAMembers.size,
            ),
            teamB = TeamDetailResponse(
                captain = UserMapper.toUserResponse(match.teamBCaptain!!),
                members = teamBMembers,
                currentSize = teamBMembers.size,
            ),
        )
    }
}