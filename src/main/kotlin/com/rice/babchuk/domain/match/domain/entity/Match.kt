package com.rice.babchuk.domain.match.domain.entity

import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.global.common.entity.BaseTimeEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false)
    val matchAt: LocalDateTime,

    @Column(nullable = false)
    val teamSize: Int,

    @Column(nullable = false)
    val durationMinutes: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_a_captain_id")
    val teamACaptain: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_b_captain_id")
    val teamBCaptain: User? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: MatchStatus = MatchStatus.OPEN
) : BaseTimeEntity()