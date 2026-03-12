package com.rice.babchuk.domain.announcement.dto.response

data class AnnouncementResponse(
    val id: Long,
    val title: String,
    val content: String,
    val image: String,
)
