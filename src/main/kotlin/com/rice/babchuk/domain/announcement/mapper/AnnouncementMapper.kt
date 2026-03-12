package com.rice.babchuk.domain.announcement.mapper

import com.rice.babchuk.domain.announcement.domain.entity.Announcement
import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.dto.response.AnnouncementResponse
import org.springframework.stereotype.Component


@Component
object AnnouncementMapper {
    fun toEntity(
        request: AnnouncementRequest,
    ): Announcement {
        return Announcement(
            title = request.title,
            content = request.content,
            image = request.image
        )
    }

    fun toResponse(
        announcement: Announcement
    ): AnnouncementResponse {
        return AnnouncementResponse(
            id = announcement.id,
            title = announcement.title,
            content = announcement.content,
            image = announcement.image
        )
    }
}