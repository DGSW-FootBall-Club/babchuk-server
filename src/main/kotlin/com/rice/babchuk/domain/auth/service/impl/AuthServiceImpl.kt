package com.rice.babchuk.domain.auth.service.impl

import com.rice.babchuk.domain.auth.client.DauthClient
import com.rice.babchuk.domain.auth.dto.DauthStudent
import com.rice.babchuk.domain.auth.dto.DauthUser
import com.rice.babchuk.domain.auth.dto.request.LoginRequest
import com.rice.babchuk.domain.auth.dto.request.SignUpRequest
import com.rice.babchuk.domain.auth.dto.response.LoginResponse
import com.rice.babchuk.domain.auth.error.AuthError
import com.rice.babchuk.domain.auth.repository.RefreshTokenRepository
import com.rice.babchuk.domain.auth.service.AuthService
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import com.rice.babchuk.global.jwt.provider.JwtProvider
import jakarta.transaction.Transactional
import org.springframework.core.env.Environment
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val dauthClient: DauthClient,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val environment: Environment,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
) : AuthService {

    @Transactional
    override fun loginWithDauth(accessToken: String): LoginResponse {
        val profile = if (accessToken == DEV_TEST_TOKEN && isDevProfile()) {
            DEV_TEST_PROFILE
        } else {
            dauthClient.fetchUser(accessToken)
        }

        if (!profile.roles.contains("STUDENT") || profile.student == null) {
            throw CustomException(AuthError.NOT_STUDENT_ACCOUNT)
        }

        val studentId = buildStudentId(
            grade = profile.student.grade,
            room = profile.student.room,
            number = profile.student.number,
        )

        val user = upsertDauthUser(profile, studentId)
        return issueTokens(user.id)
    }

    @Transactional
    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw CustomException(AuthError.INVALID_CREDENTIALS)

        val hash = user.password ?: throw CustomException(AuthError.INVALID_CREDENTIALS)
        if (!passwordEncoder.matches(request.password, hash)) {
            throw CustomException(AuthError.INVALID_CREDENTIALS)
        }

        return issueTokens(user.id)
    }

    @Transactional
    override fun reissue(refreshToken: String): LoginResponse {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw CustomException(AuthError.INVALID_REFRESH_TOKEN)
        }

        val userId = jwtProvider.getUserIdFromToken(refreshToken)

        val savedRefreshToken = refreshTokenRepository.get(userId)
            ?: throw CustomException(AuthError.INVALID_REFRESH_TOKEN)

        if (savedRefreshToken != refreshToken) {
            throw CustomException(AuthError.INVALID_REFRESH_TOKEN)
        }

        return LoginResponse(
            accessToken = jwtProvider.generateAccessToken(userId),
            refreshToken = refreshToken,
        )
    }

    private fun issueTokens(userId: Long): LoginResponse {
        val accessToken = jwtProvider.generateAccessToken(userId)
        val refreshToken = jwtProvider.generateRefreshToken(userId)

        refreshTokenRepository.save(userId, refreshToken, jwtProvider.refreshTokenExpiration)

        return LoginResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    @Transactional
    override fun signup(request: SignUpRequest) {
        if (userRepository.existsByUsername(request.username)) {
            throw CustomException(AuthError.USERNAME_ALREADY_EXISTS)
        }

        val studentId = buildStudentId(request.grade, request.room, request.number)
        if (userRepository.existsByStudentId(studentId)) {
            throw CustomException(AuthError.STUDENT_ID_ALREADY_EXISTS)
        }

        userRepository.save(
            User(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                studentId = studentId,
                name = request.name,
                profileImage = request.profileImage,
                role = "STUDENT",
                grade = request.grade,
                room = request.room,
                number = request.number,
            )
        )
    }

    private fun isDevProfile(): Boolean =
        environment.activeProfiles.contains("dev")

    private fun upsertDauthUser(profile: DauthUser, studentId: String): User {
        val existing = userRepository.findByPublicId(profile.publicId)
            ?: userRepository.findByStudentId(studentId)

        if (existing != null) {
            existing.applyDauthProfile(profile, studentId)
            return existing
        }

        val student = profile.student!!
        return userRepository.save(
            User(
                publicId = profile.publicId,
                studentId = studentId,
                name = profile.name,
                phone = profile.phone,
                profileImage = profile.profileImage,
                status = profile.status,
                role = profile.roles.firstOrNull() ?: "STUDENT",
                grade = student.grade,
                room = student.room,
                number = student.number,
            )
        )
    }

    private fun buildStudentId(grade: Int, room: Int, number: Int): String =
        "$grade$room${number.toString().padStart(2, '0')}"

    companion object {
        private const val DEV_TEST_TOKEN = "TEST_TOKEN"

        private val DEV_TEST_PROFILE = DauthUser(
            publicId = "test-public-id",
            username = "testuser",
            name = "테스트유저",
            phone = null,
            profileImage = null,
            status = "ACTIVE",
            roles = listOf("STUDENT"),
            student = DauthStudent(grade = 3, room = 3, number = 7),
            createdAt = "2024-01-01",
        )
    }
}
