package com.rice.babchuk.domain.user.domain.entity

import com.rice.babchuk.domain.auth.domain.enum.GenderType
import com.rice.babchuk.domain.auth.domain.enum.SkillType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val profileImage: String,

    @Column(nullable = false, unique = true)
    val userid: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = false)
    val grade: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val skillType: SkillType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val gender: GenderType
)