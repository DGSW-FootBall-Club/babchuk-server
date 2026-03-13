package com.rice.babchuk.domain.match.domain.entity

import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.match.dto.request.MatchRequest
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.global.common.entity.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
class Match(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(nullable = false)
    var matchAt: LocalDateTime,

    @Column(nullable = false)
    var teamSize: Int,

    @Column(nullable = false)
    var durationMinutes: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_a_captain_id")
    var teamACaptain: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_b_captain_id")
    var teamBCaptain: User? = null,

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    val participants: MutableList<MatchParticipant> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: MatchStatus = MatchStatus.OPEN,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author: User

    ) : BaseTimeEntity() {

    fun updateMatch(
        request: MatchRequest,
        teamACaptain: User,
        teamBCaptain: User,
    ) {
        this.title = request.title
        this.matchAt = LocalDateTime.of(request.matchDate, request.matchTime)
        this.teamSize = request.teamSize
        this.durationMinutes = request.durationMinutes
        this.teamACaptain = teamACaptain
        this.teamBCaptain = teamBCaptain
    }
}