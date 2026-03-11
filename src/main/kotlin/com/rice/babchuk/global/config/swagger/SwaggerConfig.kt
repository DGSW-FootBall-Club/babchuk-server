package com.rice.babchuk.global.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {

        val info = Info()
            .title("Babchuk Server API")
            .description("Babchuk API Documentation")
            .version("1.0.0")

        val securityScheme = SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("JWT 토큰을 입력하세요. 예: Bearer {token}")

        val components = Components()
            .addSecuritySchemes("bearer-jwt", securityScheme)

        val securityRequirement = SecurityRequirement()
            .addList("bearer-jwt")

        return OpenAPI()
            .info(info)
            .components(components)
            .addSecurityItem(securityRequirement)
    }
}