package com.rice.babchuk.domain.auth.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepository(
    private val redisTemplate: StringRedisTemplate
) {

    fun save(userId: String, refreshToken: String, expiration: Long) {
        redisTemplate.opsForValue()
            .set("refresh:$userId", refreshToken, expiration, TimeUnit.MILLISECONDS)
    }

    fun get(userId: String): String? {
        return redisTemplate.opsForValue().get("refresh:$userId")
    }

    fun delete(userId: String) {
        redisTemplate.delete("refresh:$userId")
    }
}