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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "announcement_images",
        joinColumns = [JoinColumn(name = "announcement_id")],
    )
    @Column(name = "image_url", columnDefinition = "LONGTEXT", nullable = false)
    @OrderColumn(name = "image_order")
    var images: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author: User

) : BaseTimeEntity() {
    fun updateAnnouncement(request: AnnouncementRequest) {
        this.title = request.title
        this.content = request.content
        this.images.clear()
        this.images.addAll(request.images)
    }
}
