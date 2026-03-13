package com.rice.babchuk.global.security.details

import com.rice.babchuk.domain.user.domain.entity.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(val user: User) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_USER"))
    override fun getUsername() = user.username
    override fun getPassword() = user.password
}