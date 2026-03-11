package com.rice.babchuk.global.jwt.filter

import com.rice.babchuk.global.jwt.provider.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = jwtProvider.extractToken(request)

        if (token != null && jwtProvider.validateToken(token)) {
            SecurityContextHolder.getContext().authentication =
                jwtProvider.getAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }
}