package com.rice.babchuk.domain.announcement.mapper

import com.rice.babchuk.domain.announcement.domain.entity.Announcement
import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.dto.response.AnnouncementResponse
import com.rice.babchuk.domain.user.domain.entity.User
import org.springframework.stereotype.Component


@Component
object AnnouncementMapper {
    fun toEntity(
        request: AnnouncementRequest,
        author: User
    ): Announcement {
        return Announcement(
            title = request.title,
            content = request.content,
            image = request.image,
            author = author
        )
    }

    fun toResponse(announcement: Announcement): AnnouncementResponse {
        return AnnouncementResponse(
            id = announcement.id,
            title = announcement.title,
            content = announcement.content,
            image = announcement.image,
            author = announcement.author.nickname,
            createdAt = announcement.createdAt
        )
    }
}