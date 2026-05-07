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
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    private val parser by lazy {
        Jwts.parser().verifyWith(key).build()
    }

    fun generateAccessToken(userId: Long): String {
        val now = Date()

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(Date(now.time + accessTokenExpiration))
            .signWith(key)
            .compact()
    }

    fun extractToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val userId = getUserIdFromToken(token)

        return UsernamePasswordAuthenticationToken(
            userId,
            null,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }

    fun getUserIdFromToken(token: String): Long =
        parser.parseSignedClaims(token).payload.subject.toLong()

    fun validateToken(token: String): Boolean =
        runCatching { parser.parseSignedClaims(token) }.isSuccess
}
