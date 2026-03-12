package com.rice.babchuk.domain.match.repository

import com.rice.babchuk.domain.match.domain.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository : JpaRepository<Match, Long> {

    @Query("""
        SELECT m FROM Match m
        JOIN FETCH m.teamACaptain
        JOIN FETCH m.teamBCaptain
    """)
    fun findAllWithCaptains(): List<Match>
}