package com.rice.babchuk.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DauthStudent(
    val grade: Int,
    val room: Int,
    val number: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DauthUser(
    val publicId: String,
    val username: String?,
    val name: String,
    val phone: String?,
    val profileImage: String?,
    val status: String?,
    val roles: List<String> = emptyList(),
    val student: DauthStudent?,
    val createdAt: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class DauthUserResponse(
    val status: Int,
    val message: String?,
    val data: DauthUser?,
)
