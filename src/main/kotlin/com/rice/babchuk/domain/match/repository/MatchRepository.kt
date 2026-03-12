package com.rice.babchuk.domain.match.repository

import com.rice.babchuk.domain.match.domain.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MatchRepository : JpaRepository<Match, Long> {

    @Query("""
        SELECT m FROM Match m
        JOIN FETCH m.teamACaptain
        JOIN FETCH m.teamBCaptain
    """)
    fun findAllWithCaptains(): List<Match>

    @Query("""
        SELECT DISTINCT m FROM Match m
        JOIN FETCH m.teamACaptain
        JOIN FETCH m.teamBCaptain
        LEFT JOIN FETCH m.participants p
        LEFT JOIN FETCH p.user
        WHERE m.id = :matchId
    """)
    fun findByIdWithDetails(matchId: Long): Match?

    @Query("""
        SELECT m FROM Match m
        WHERE m.status != 'FINISHED'
        AND m.matchAt < :now
    """)
    fun findAllNotFinishedAndMatchAtBefore(now: LocalDateTime): List<Match>
}