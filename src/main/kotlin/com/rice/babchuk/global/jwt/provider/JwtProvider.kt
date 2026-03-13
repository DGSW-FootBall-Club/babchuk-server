package com.rice.babchuk.global.jwt.provider

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(

    @Value("\${jwt.secret}")
    secretKey: String,

    @Value("\${jwt.expiration.access}")
    private val accessTokenExpiration: Long,

    @Value("\${jwt.expiration.refresh}")
    private val refreshTokenExpiration: Long
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    private val parser by lazy {
        Jwts.parser().verifyWith(key).build()
    }

    /**
     * Access Token 생성
     */
    fun generateAccessToken(userId: Long) =
        generateToken(userId, accessTokenExpiration)

    /**
     * Refresh Token 생성
     */
    fun generateRefreshToken(userId: Long) =
        generateToken(userId, refreshTokenExpiration)

    private fun generateToken(userId: Long, expiration: Long): String {
        val now = Date()

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(key)
            .compact()
    }

    /**
     * Authorization Header에서 Token 추출
     */
    fun extractToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

    /**
     * Authentication 생성
     */
    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val username = getUserIdFromToken(token)

        return UsernamePasswordAuthenticationToken(
            username,
            null,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }

    /**
     * userName 추출
     */
    fun getUserIdFromToken(token: String): Long =
        parser.parseSignedClaims(token).payload.subject.toLong()

    /**
     * Token 검증
     */
    fun validateToken(token: String): Boolean =
        runCatching { parser.parseSignedClaims(token) }.isSuccess
}