package com.rice.babchuk.domain.user.domain.entity

import com.rice.babchuk.domain.auth.domain.enum.GenderType
import com.rice.babchuk.domain.auth.domain.enum.SkillType
import com.rice.babchuk.domain.user.dto.request.UpdateUserRequest
import com.rice.babchuk.global.common.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var profileImage: String,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    var nickname: String,

    @Column(nullable = false)
    var grade: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var skillType: SkillType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val gender: GenderType

) : BaseTimeEntity() {
    fun updateProfile(request: UpdateUserRequest) {
        request.profileImage?.let { this.profileImage = it }
        request.nickname?.let { this.nickname = it }
        request.grade?.let { this.grade = it }
        request.skillType?.let { this.skillType = it }
    }
}