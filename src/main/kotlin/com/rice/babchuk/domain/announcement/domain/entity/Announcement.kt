package com.rice.babchuk.domain.announcement.domain.entity

import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.global.common.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "announcements")
class Announcement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, length = 100)
    var content: String,

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    var image: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author: User

) : BaseTimeEntity() {
    fun updateAnnouncement(
        request: AnnouncementRequest
    ) {
        this.title = request.title
        this.content = request.content
        this.image = request.image
    }
}