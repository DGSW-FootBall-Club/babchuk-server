package com.rice.babchuk.domain.match.repository

import com.rice.babchuk.domain.match.domain.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository : JpaRepository<Match, Long>