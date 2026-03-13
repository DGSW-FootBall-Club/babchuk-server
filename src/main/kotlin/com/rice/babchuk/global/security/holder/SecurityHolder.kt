package com.rice.babchuk.global.security.holder

import com.rice.babchuk.domain.user.domain.entity.User

interface SecurityHolder {
    val userId: Long
    val user: User
    val isAuthenticated: Boolean
}