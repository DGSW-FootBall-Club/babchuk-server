package com.rice.babchuk.domain.announcement.service

import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.dto.response.AnnouncementResponse

interface AnnouncementService {
    fun createAnnouncement(request: AnnouncementRequest)
    fun getAnnouncement(): List<AnnouncementResponse>
    fun updateAnnouncement(announcementId: Long, request: AnnouncementRequest)
    fun deleteAnnouncement(id: Long)

}