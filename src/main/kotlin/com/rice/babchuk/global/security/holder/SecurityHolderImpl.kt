package com.rice.babchuk.global.security.holder

import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
) : SecurityHolder {

    override val userId: Long
        get() = SecurityContextHolder.getContext().authentication.name.toLong()

    override val user: User
        get() = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found: $userId") }

    override val isAuthenticated: Boolean
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return authentication != null &&
                    authentication.isAuthenticated &&
                    authentication.name != "anonymousUser"
        }
}