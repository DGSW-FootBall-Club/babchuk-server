package com.rice.babchuk.domain.announcement.dto.response
import java.time.LocalDateTime

data class AnnouncementResponse(
    val id: Long,
    val title: String,
    val content: String,
    val image: String,
    val author: String,
    val createdAt: LocalDateTime
)