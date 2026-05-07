package com.rice.babchuk.domain.user.domain.entity

import com.rice.babchuk.domain.auth.dto.DauthUser
import com.rice.babchuk.domain.user.domain.enum.GenderType
import com.rice.babchuk.domain.user.domain.enum.SkillType
import com.rice.babchuk.domain.user.dto.request.UpdateUserRequest
import com.rice.babchuk.global.common.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    var publicId: String? = null,

    @Column(nullable = false, unique = true, length = 10)
    var studentId: String,

    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "LONGTEXT")
    var profileImage: String? = null,

    var username: String? = null,

    var phone: String? = null,

    @Column(length = 50)
    var status: String? = null,

    @Column(length = 50)
    var role: String? = null,

    @Column(nullable = false)
    var grade: Int,

    @Column(nullable = false)
    var room: Int,

    @Column(name = "student_number", nullable = false)
    var number: Int,

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    var skillType: SkillType? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    var gender: GenderType? = null,

) : BaseTimeEntity() {
    fun updateProfile(request: UpdateUserRequest) {
        request.profileImage?.let { this.profileImage = it }
        request.skillType?.let { this.skillType = it }
        request.gender?.let { this.gender = it }
    }

    fun applyDauthProfile(profile: DauthUser, studentId: String) {
        val student = profile.student!!
        this.publicId = profile.publicId
        this.studentId = studentId
        this.username = profile.username
        this.name = profile.name
        this.phone = profile.phone
        this.profileImage = profile.profileImage
        this.status = profile.status
        this.role = profile.roles.firstOrNull() ?: "STUDENT"
        this.grade = student.grade
        this.room = student.room
        this.number = student.number
    }
}
