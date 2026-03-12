package com.rice.babchuk.domain.announcement.repository

import com.rice.babchuk.domain.announcement.domain.entity.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository : JpaRepository<Announcement, Long> {}