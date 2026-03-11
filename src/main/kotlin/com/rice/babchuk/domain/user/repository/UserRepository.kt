package com.rice.babchuk.domain.user.repository

import com.rice.babchuk.domain.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUserid(userid: String): User?
    fun existsByUserid(userid: String): Boolean
}

