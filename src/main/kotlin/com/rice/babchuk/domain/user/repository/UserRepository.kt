package com.rice.babchuk.domain.user.repository

import com.rice.babchuk.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByPublicId(publicId: String): User?
    fun findByStudentId(studentId: String): User?
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByStudentId(studentId: String): Boolean
}
