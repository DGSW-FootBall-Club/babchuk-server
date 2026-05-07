package com.rice.babchuk.domain.auth.service.impl

import com.rice.babchuk.domain.auth.client.DauthClient
import com.rice.babchuk.domain.auth.dto.DauthStudent
import com.rice.babchuk.domain.auth.dto.DauthUser
import com.rice.babchuk.domain.auth.dto.response.DauthLoginResponse
import com.rice.babchuk.domain.auth.error.AuthError
import com.rice.babchuk.domain.auth.service.AuthService
import com.rice.babchuk.domain.user.domain.entity.User
import com.rice.babchuk.domain.user.repository.UserRepository
import com.rice.babchuk.global.error.CustomException
import com.rice.babchuk.global.jwt.provider.JwtProvider
import jakarta.transaction.Transactional
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val dauthClient: DauthClient,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val environment: Environment,
) : AuthService {

    @Transactional
    override fun loginWithDauth(accessToken: String): DauthLoginResponse {
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

        val user = upsertUser(profile, studentId)

        val token = jwtProvider.generateAccessToken(user.id)
        return DauthLoginResponse(accessToken = token)
    }

    private fun isDevProfile(): Boolean =
        environment.activeProfiles.contains("dev")

    private fun upsertUser(profile: DauthUser, studentId: String): User {
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
                username = profile.username,
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
