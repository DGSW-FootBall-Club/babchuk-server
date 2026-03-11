package com.rice.babchuk.domain.auth.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepository(
    private val redisTemplate: StringRedisTemplate
) {

    fun save(userName: String, refreshToken: String, expiration: Long) {
        redisTemplate.opsForValue()
            .set("refresh:$userName", refreshToken, expiration, TimeUnit.MILLISECONDS)
    }

    fun get(userName: String): String? {
        return redisTemplate.opsForValue().get("refresh:$userName")
    }

    fun delete(userName: String) {
        redisTemplate.delete("refresh:$userName")
    }
}