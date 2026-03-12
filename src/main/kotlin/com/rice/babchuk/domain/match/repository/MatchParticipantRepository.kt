package com.rice.babchuk.domain.match.repository

import com.rice.babchuk.domain.match.domain.entity.MatchParticipant
import com.rice.babchuk.domain.match.domain.enum.TeamType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchParticipantRepository : JpaRepository<MatchParticipant, Long> {
    fun existsByMatchIdAndUserId(matchId: Long, userId: Long): Boolean
    fun countByMatchIdAndTeamType(matchId: Long, teamType: TeamType): Long
    fun findByMatchIdAndUserId(matchId: Long, userId: Long): MatchParticipant?
}