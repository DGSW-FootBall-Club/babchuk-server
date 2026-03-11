package com.rice.babchuk.global.jwt.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.rice.babchuk.global.error.ErrorResponse
import com.rice.babchuk.global.jwt.error.JwtError
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val error = JwtError.INVALID_TOKEN

        response.status = error.status.value()
        response.characterEncoding = Charsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        response.writer.write(
            objectMapper.writeValueAsString(ErrorResponse.of(error))
        )
    }
}