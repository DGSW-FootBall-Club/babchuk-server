package com.rice.babchuk.domain.auth.service.Impl

import com.rice.babchuk.domain.auth.dto.request.LoginRequest
import com.rice.babchuk.domain.auth.dto.request.SignUpRequest
import com.rice.babchuk.domain.auth.error.AuthError
import com.rice.babchuk.domain.auth.repository.RefreshTokenRepository
import com.rice.babchuk.domain.auth.service.AuthService
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import com.rice.babchuk.global.jwt.provider.JwtProvider
import com.rice.babchuk.global.jwt.dto.response.JwtResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthService {

    override fun login(request: LoginRequest): JwtResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw CustomException(AuthError.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw CustomException(AuthError.INVALID_PASSWORD)
        }

        val accessToken = jwtProvider.generateAccessToken(user.id)
        val refreshToken = jwtProvider.generateRefreshToken(user.id)

        refreshTokenRepository.save(user.username, refreshToken, 604800000)

        return JwtResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun signup(request: SignUpRequest) {
        if (userRepository.existsByUsername(request.username)) {
            throw CustomException(AuthError.USER_ALREADY_EXISTS)
        }

        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User(
            profileImage = request.profileImage,
            username = request.username,
            password = encodedPassword,
            nickname = request.nickname,
            grade = request.grade,
            skillType = request.skillType,
            gender = request.gender
        )

        userRepository.save(user)
    }

    override fun reissue(refreshToken: String): JwtResponse {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw CustomException(AuthError.INVALID_REFRESH_TOKEN)
        }

        val username = jwtProvider.getUserNameFromToken(refreshToken)

        val savedRefreshToken =
            refreshTokenRepository.get(username) ?: throw CustomException(AuthError.INVALID_REFRESH_TOKEN)

        if (savedRefreshToken != refreshToken) {
            throw CustomException(AuthError.INVALID_REFRESH_TOKEN)
        }

        val user = userRepository.findByUsername(username)
            ?: throw CustomException(AuthError.USER_NOT_FOUND)

        val newAccessToken = jwtProvider.generateAccessToken(user.id)

        return JwtResponse(
            accessToken = newAccessToken,
            refreshToken = refreshToken
        )
    }
}