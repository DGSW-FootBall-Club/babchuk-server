package com.rice.babchuk.domain.announcement.controller

import com.rice.babchuk.domain.announcement.dto.request.AnnouncementRequest
import com.rice.babchuk.domain.announcement.service.AnnouncementService
import com.rice.babchuk.global.common.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/announcement")
@Tag(name = "공지사항 API")
class AnnouncementController(
    private val announcementService: AnnouncementService
) {

    @PostMapping
    @Operation(summary = "공지 생성")
    fun createAnnouncement(
        @RequestBody request: AnnouncementRequest
    ) = announcementService.createAnnouncement(request)
        .let { BaseResponse.success(message = "공지 생성 성공") }

    @GetMapping
    @Operation(summary = "공지 조회")
    fun getAnnouncement() =
        BaseResponse.of(
            announcementService.getAnnouncement(),
            message = "공지 조회 성공"
        )

    @DeleteMapping("/{announcementId}")
    @Operation(summary = "공지 삭제")
    fun deleteAnnouncement(
        @PathVariable announcementId: Long
    ) = announcementService.deleteAnnouncement(announcementId)
        .let { BaseResponse.success(message = "공지 삭제 성공") }

    @PatchMapping("/{announcementId}")
    @Operation(summary = "공지 수정")
    fun updateAnnouncement(
        @PathVariable announcementId: Long,
        @RequestBody request: AnnouncementRequest
    ) = announcementService.updateAnnouncement(announcementId, request)
        .let { BaseResponse.success(message = "공지 수정 성공") }
}