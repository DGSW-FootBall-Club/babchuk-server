package com.rice.babchuk.domain.announcement.service.impl

import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.dto.response.AnnouncementResponse
import com.rice.babchuk.domain.announcement.error.AnnouncementError
import com.rice.babchuk.domain.announcement.mapper.AnnouncementMapper
import com.rice.babchuk.domain.announcement.repository.AnnouncementRepository
import com.rice.babchuk.domain.announcement.service.AnnouncementService
import com.rice.babchuk.global.error.CustomException
import com.rice.babchuk.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service

@Service
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository,
    private val announcementMapper: AnnouncementMapper,
    private val securityHolder: SecurityHolder
) : AnnouncementService {

    override fun createAnnouncement(request: AnnouncementRequest) {
        announcementRepository.save(announcementMapper.toEntity(request, securityHolder.user))
    }

    override fun getAnnouncement(): List<AnnouncementResponse> {
        return announcementRepository.findAll()
            .map { announcementMapper.toResponse(it) }
    }

    override fun updateAnnouncement(announcementId: Long, request: AnnouncementRequest) {
        val announcement = announcementRepository.findById(announcementId)
            .orElseThrow { CustomException(AnnouncementError.NOTICE_NOT_FOUND) }

        if (announcement.author != securityHolder.user) {
            throw CustomException(AnnouncementError.NOTICE_FORBIDDEN)
        }

        announcement.updateAnnouncement(request)
    }

    override fun deleteAnnouncement(id: Long) {
        val announcement = announcementRepository.findById(id)
            .orElseThrow { CustomException(AnnouncementError.NOTICE_NOT_FOUND) }

        if (announcement.author != securityHolder.user) {
            throw CustomException(AnnouncementError.NOTICE_FORBIDDEN)
        }

        announcementRepository.delete(announcement)
    }
}