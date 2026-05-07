package com.rice.babchuk.domain.announcement.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "공지사항 생성 요청")
data class AnnouncementRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val content: String,

    @Schema(description = "이미지 URL 목록 (선택, 0~N장)")
    val images: List<String> = emptyList(),
)
