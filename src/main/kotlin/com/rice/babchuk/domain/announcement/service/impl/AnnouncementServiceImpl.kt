

import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.dto.response.AnnouncementResponse
import com.rice.babchuk.domain.announcement.mapper.AnnouncementMapper
import com.rice.babchuk.domain.announcement.repository.AnnouncementRepository
import com.rice.babchuk.domain.announcement.service.AnnouncementService
import org.springframework.stereotype.Service

@Service
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository,
    private val announcementMapper: AnnouncementMapper
) : AnnouncementService {
    override fun createAnnouncement(request: AnnouncementRequest) {
        announcementRepository.save(announcementMapper.toEntity(request))
    }

    override fun getAnnouncement(): List<AnnouncementResponse> {
       val announcements = announcementRepository.findAll()
        return announcements.map{announcementMapper.toResponse(it)}
    }

    override fun updateAnnouncement(announcementId: Long, request: AnnouncementRequest) {
        val announcement = announcementRepository.findById(announcementId).get()
        announcement.updateAnnouncement(request)
    }

    override fun deleteAnnouncement(id: Long) {
        announcementRepository.deleteById(id)
    }
}